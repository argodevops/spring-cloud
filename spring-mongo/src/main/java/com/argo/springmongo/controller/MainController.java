package com.argo.springmongo.controller;

import com.argo.springmongo.*;
import com.argo.springmongo.service.*;
import com.argo.springmongo.repository.*;

import java.util.List;
import java.util.Optional;

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
@RequestMapping({"/todo", "/todo/"})
public class MainController {

  TaskService taskService;

  @Autowired
  public MainController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("/")
  public String index(Model model, @RequestParam("error") Optional<String> error, @RequestParam("success") Optional<String> success) {
    // TODO: handle messaging
    taskService.updateModelWithFilteredTasks(model);
    return "index";
  }
}
