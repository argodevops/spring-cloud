package com.argo.springmongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.argo.springmongo.service.TaskService;

@Controller
@RequestMapping({"/todo/test", "/todo/test/"})
public class TestController {

    private final TaskService taskService;

    @Autowired
    public TestController(TaskService taskService) {
      this.taskService = taskService;
    }

    @GetMapping("")
    public String testIndex() {
        return "redirect:/todo";
    }

    @GetMapping("/samples")
    public String samples() {
      taskService.createAndSaveSamples();
      return "redirect:/todo/?test=1";
    }

    @GetMapping("/clear")
    public String clear() {
      taskService.clearRepository();
      return "redirect:/todo/?test=2";
    }
}
