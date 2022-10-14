package com.argo.springmongo.controller;

import java.util.Optional;

import com.argo.springmongo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping({"/todo", "/todo/"})
public class MainController {

  TaskService taskService;

  @Autowired
  public MainController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("/")
  public String index(Model model, @RequestParam("error") Optional<String> error, @RequestParam("success") Optional<String> success) {
    taskService.updateModelWithFilteredTasks(model);
    return "index";
  }
}
