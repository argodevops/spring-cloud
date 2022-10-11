package com.argo.springmongo.controller;

import com.argo.springmongo.*;
import com.argo.springmongo.service.*;
import com.argo.springmongo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/todo")
public class MainController {

  @Autowired
  public TaskRepository taskRepository;

  TaskService taskService;

  @Autowired
  public MainController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("/")
  public String index(Model model) {
    taskService.clearRepository();
    taskService.createSamples();
    taskService.saveSamples();
    taskService.updateModelWithFilteredTasks(model);

    return "index";
  }
}
