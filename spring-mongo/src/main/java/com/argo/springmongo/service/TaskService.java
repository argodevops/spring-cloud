package com.argo.springmongo.service;

import com.argo.springmongo.*;
import com.argo.springmongo.repository.*;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  @Autowired
  public TaskService(TaskRepository taskRepository){
    this.taskRepository = taskRepository;
  }

  public void clearRepository() {
    taskRepository.deleteAll();
  }

  public Task findByid(String id) {
    return taskRepository.findByid(id);
  }

  public void save(Task task) {
    taskRepository.save(task);
  }

  public void requestDeletion(Task task) {
    task.setDeleted(true);
    taskRepository.deleteById(task.getId());
  }

  public List<Task> getOutstanding() {
    return taskRepository.findByCompleted(false);
  }

  public List<Task> getOutstandingByPriority() {
    return taskRepository.findByCompletedOrderByPriorityDesc(false);
  }

  public void updateModelWithFilteredTasks(Model model) {
    model.addAttribute("allTasks", taskRepository.findAll());

    model.addAttribute("orderPriority", taskRepository.findByOrderByPriority());

    model.addAttribute(
      "topPriority",
      taskRepository.findByPriority(PriorityType.TOP)
    );

    model.addAttribute(
      "incompleteTasks",
      getOutstanding()
    );

    model.addAttribute("hasNotes", taskRepository.findByHasNotesCustom());
  }

  public Model injectEmptyTaskIntoModel(Model model) {
    new Task().injectIntoModel(model);
    return model;
  }

  // Samples

  public void createAndSaveSamples() {
    Task bins = new Task("Take the bins out");
    Task tax = new Task("Apply for wfh tax rebate", PriorityType.TOP);
    Task homework = new Task("Write an essay", PriorityType.TOP);
    Task cleanup = new Task("Tidy the house", PriorityType.LOW);
    Task exercise = new Task("Go for a run", PriorityType.LOWEST);
    Task lunch = new Task("Eat lunch", PriorityType.HIGH);
    tax.setNotes("You can apply online to have your tax code changed");
    tax.complete();
  
    taskRepository.saveAll(Arrays.asList(new Task[]{bins, tax, homework, cleanup, exercise, lunch}));
  }

}
