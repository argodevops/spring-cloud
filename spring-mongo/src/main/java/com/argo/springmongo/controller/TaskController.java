package com.argo.springmongo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.argo.springmongo.PriorityType;
import com.argo.springmongo.repository.*;
import com.argo.springmongo.service.TaskService;
import com.argo.springmongo.*;

@Controller
@RequestMapping("/todo/task")
public class TaskController {

  @Autowired
  public TaskRepository taskRepository;
  public TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("")
  public String taskIndex() {
    return "taskHome";
  }

  @GetMapping("/add")
  public String add(Model model) {
    taskService.updateModelWithTask(model, taskService.emptyTask);
    return "taskForm";
  }

  @RequestMapping(value="/edit", method = RequestMethod.GET)
  public String edit(Model model, @RequestParam("id") Optional<String> id) {
    Task task = taskService.findByid(id.get());

    if (!id.isPresent() || task == null) {
      return "redirect:/todo"; // Send to list of all tasks if no id or not task with id
    }

    // Otherwise, fill in the form with the relevant task data
    taskService.updateModelWithTask(model, task);
    return "taskForm";
  }

  @GetMapping("/delete")
  public String delete() {
    // List of tasks (or not if ID is in GET)
    // Confirmation dialog for deletion of task with ID
    // Redirect to list of tasks
    return "delete";
  }

  @PostMapping("/handle")
  public String handleForm() {
    // Get in all the values
    // Check for ID
    // If no ID, create new task
    // If ID, check repository for it
    // If exists, update task
    // If ID doesn't match repository, give error
    // Redirect to list of tasks
    return "handleForm";
  }
}
