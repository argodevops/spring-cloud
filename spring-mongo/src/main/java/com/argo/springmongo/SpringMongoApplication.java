package com.argo.springmongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMongoApplication implements CommandLineRunner {

	@Autowired
	private TaskRepository trep;

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoApplication.class, args);
	}

	public static void p(String t) {
		System.out.println(t);
	}
	public static void o(Object t) {
		System.out.println(t);
	}

	@Override
	public void run(String... args) throws Exception {
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
	}

}
