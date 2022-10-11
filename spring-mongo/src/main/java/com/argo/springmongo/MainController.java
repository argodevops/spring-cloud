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

    @GetMapping("/")
    public String index(Model model) {

		trep.deleteAll();

		Task bins = new Task("Take the bins out");
		Task tax = new Task("Apply for wfh tax rebate", PriorityType.TOP);
		Task homework = new Task("Write an essay", PriorityType.TOP);
		Task cleanup = new Task("Tidy the house", PriorityType.LOW);
		Task exercise = new Task("Go for a run", PriorityType.LOWEST);
		Task lunch = new Task("Eat lunch", PriorityType.HIGH);
        tax.notes = "You can apply online to have your tax code changed";
		tax.complete();

		trep.save(bins);
		trep.save(tax);
		trep.save(homework);
		trep.save(cleanup);
		trep.save(exercise);
		trep.save(lunch);

        model.addAttribute("allTasks", trep.findAll());

        model.addAttribute("orderPriority", trep.findByOrderByPriority());

        model.addAttribute("topPriority", trep.findByPriority(PriorityType.TOP));

        model.addAttribute("incompleteTasks", trep.findByCompleted(false));

        return "index";
    }
}
