package com.argo.springmongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.argo.springmongo.PriorityType;
import com.argo.springmongo.repository.*;

@Controller
@RequestMapping("/todo/task")
public class TaskController {

  @Autowired
  public TaskRepository taskRepository;

  @GetMapping("")
  public String taskIndex() {
    return "taskHome";
  }

  @GetMapping("/add")
  public String add(Model model) {
    model.addAttribute("id", "");
    model.addAttribute("text", "");
    model.addAttribute("priority", PriorityType.NORMAL);
    model.addAttribute("notes", "");
    return "taskForm";
  }

  @GetMapping("/edit")
  public String edit() {
    // GET id, check if exists
    // If not, error message, go to list/create new?
    // If exists, print form with attributes filled in
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
