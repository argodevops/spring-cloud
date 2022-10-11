package com.argo.springmongo.service;

import com.argo.springmongo.*;
import com.argo.springmongo.repository.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TaskService {

  @Autowired
  TaskRepository taskRepository;

  List<Task> taskSamples = new ArrayList<>();

  public void clearRepository() {
    taskRepository.deleteAll();
  }

  public void createSamples() {
    Task bins = new Task("Take the bins out");
    Task tax = new Task("Apply for wfh tax rebate", PriorityType.TOP);
    Task homework = new Task("Write an essay", PriorityType.TOP);
    Task cleanup = new Task("Tidy the house", PriorityType.LOW);
    Task exercise = new Task("Go for a run", PriorityType.LOWEST);
    Task lunch = new Task("Eat lunch", PriorityType.HIGH);
    tax.notes = "You can apply online to have your tax code changed";
    tax.complete();

    taskSamples.add(bins);
    taskSamples.add(tax);
    taskSamples.add(homework);
    taskSamples.add(cleanup);
    taskSamples.add(exercise);
    taskSamples.add(lunch);
  }

  public void saveSamples() {
    for (Task taskSample : taskSamples) {
      taskRepository.save(taskSample);
    }
  }

  public void updateModelWithTasks(Model model) {
    model.addAttribute("allTasks", taskRepository.findAll());

    model.addAttribute("orderPriority", taskRepository.findByOrderByPriority());

    model.addAttribute(
      "topPriority",
      taskRepository.findByPriority(PriorityType.TOP)
    );

    model.addAttribute(
      "incompleteTasks",
      taskRepository.findByCompleted(false)
    );
  }
}
