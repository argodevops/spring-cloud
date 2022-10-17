package com.argo.springmongo.service;

import com.argo.springmongo.*;
import com.argo.springmongo.repository.*;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  @Autowired
  public TaskService(TaskRepository taskRepository){
    this.taskRepository = taskRepository;
  }

  @PreDestroy
  public void clearRepository() {
    taskRepository.deleteAll();
  }

  public void save(Task task) {
    taskRepository.save(task);
  }

  public void save(List<Task> tasks) {
    taskRepository.saveAll(tasks);
  }

  public Task getByid(String id) {
    return taskRepository.findByid(id);
  }

  public List<Task> getAll() {
    return taskRepository.findAll();
  }

  public Boolean validate(Task task) {
    return (task != null && !task.getText().isEmpty());
  }

  public Boolean validate(List<Task> tasks) {
    for (Task task : tasks) {
      if (!validate(task)) {
        return false;
      }
    }
    return true;
  }

  public Task fix(Task task) {
    if (validate(task)) {
      return new Task(task.getText());
    }
    return null;
  }

  public List<Task> fix(List<Task> tasks) {
    List<Task> output = Arrays.asList();
    for (Task task : tasks) {
      if (validate(task)) {
        output.add(new Task(task.getText()));
      }
    }
    return output;
  }

  public void complete(Task task) {
    task.complete();
    taskRepository.save(task);
  }

  public void complete(List<Task> tasks) {
    for (Task task : tasks) {
      complete(task);
    }
  }
  
  public void requestDeletion(Task task) {
    taskRepository.deleteById(task.getId());
  }

  public void requestDeletion(String id) {
    taskRepository.deleteById(id);
  }

  public void requestDeletionMany(List<Task> tasks) {
    taskRepository.deleteAll(tasks);
  }

  public void requestDeletionManyByid(List<String> ids) {
    taskRepository.deleteAllById(ids);
  }

  public void deleteByCompletedTrueCustom() {
    taskRepository.deleteByCompletedTrueCustom();
  }

  public List<Task> getOutstanding() {
    return taskRepository.findByCompleted(false);
  }

  public List<Task> getOutstandingByPriority() {
    return taskRepository.findByCompletedOrderByPriorityDesc(false);
  }

  // Samples

  @PostConstruct
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
