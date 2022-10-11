package com.argo.springmongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/todo")
public class MainController {

    @Autowired
    public TaskRepository trep;

    public String out;
    public static String nl = "\r\n";

    public void p(String t) {
        out = out + nl + t;
    }
    public void o(Object t) {
        p(t.toString());
    }

    @GetMapping("/")
    public String index(Model model) {

		trep.deleteAll();

		Task bins = new Task("Take the bins out");
		Task tax = new Task("Apply for wfh tax rebate", PriorityType.TOP);
		Task homework = new Task("Write an essay", PriorityType.TOP);
		Task cleanup = new Task("Tidy the house", PriorityType.LOW);
		Task exercise = new Task("Go for a run", PriorityType.LOWEST);
		Task lunch = new Task("Eat lunch", PriorityType.HIGH);
		tax.complete();

		trep.save(bins);
		trep.save(tax);
		trep.save(homework);
		trep.save(cleanup);
		trep.save(exercise);
		trep.save(lunch);

		p("ALL TASKS:");
		for (Task task : trep.findAll()) {
			o(task);
		}

        //model.addAttribute("allTasks", trep.findAll());

		p("ALL TASKS ORDERED BY PRIORITY:");
		for (Task task : trep.findByOrderByPriority()) {
			o(task);
		}

		p("TOP PRIORITY TASKS:");
		for (Task task : trep.findByPriority(PriorityType.TOP)) {
			o(task);
		}

		p("INCOMPLETE TASKS:");
		for (Task task : trep.findByCompleted(false)) {
			o(task);
		}

        model.addAttribute("content", out);
        return "index";
    }
}
