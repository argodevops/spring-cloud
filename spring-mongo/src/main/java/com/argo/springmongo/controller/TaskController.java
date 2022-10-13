package com.argo.springmongo.controller;

import com.argo.springmongo.*;
import com.argo.springmongo.PriorityType;
import com.argo.springmongo.service.TaskService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/todo/task")
public class TaskController {

  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("")
  public String taskIndex(Model model) {
    model.addAttribute("incompleteTasks", taskService.getOutstandingByPriority());
    return "taskHome";
  }

  @GetMapping("/add")
  public String add(Model model) {
    taskService.injectEmptyTaskIntoModel(model);
    return "taskForm";
  }

  @RequestMapping(value = "/complete", method = RequestMethod.GET)
  public String complete(@RequestParam("id") Optional<String> id) {
    Task task = taskService.findByid(id.get());

    if (!id.isPresent() || task == null) {
      System.out.println("ERROR: Couldn't find the task to complete");
      return "redirect:/todo/task?error=3";
    }

    task.complete();
    taskService.save(task);
    return "redirect:/todo/task?success=3"; // success 3: task completed
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(Model model, @RequestParam("id") Optional<String> id) {
    Task task = taskService.findByid(id.get());

    if (!id.isPresent() || task == null) {
      System.out.println("ERROR: Couldn't find the task to edit");
      return "redirect:/todo/task?error=1"; // Send to list of all tasks if no id or not task with id
    }

    // Otherwise, fill in the form with the relevant task data
    task.injectIntoModel(model);
    return "taskForm";
  }

  @GetMapping("/delete")
  public String delete(@RequestParam("id") Optional<String> id) {
    // TODO: Confirmation dialog for deletion of task with ID

    Task task = taskService.findByid(id.get());

    if (!id.isPresent() || task == null) {
      System.out.println("ERROR: Couldn't find the task to delete");
      return "redirect:/todo/task?error=2";
    }

    taskService.requestDeletion(task);
    return "redirect:/todo/task?success=2"; // success 2: task deleted successfully
  }

  @PostMapping("/handle")
  public String handleForm(// Can't I just request a response of type Task ?
    @RequestParam("id") Optional<String> id,
    @RequestParam("text") Optional<String> text,
    @RequestParam("priority") Optional<String> priority,
    @RequestParam("notes") Optional<String> notes
  ) {
    if (!text.isPresent() || text.get().trim().isEmpty()) {
      System.out.println("No text passed from taskForm");
      return "redirect:/todo/task?error=4";
    }

    Task task = taskService.findByid(id.get());

    if (task == null) {
      // Create
      task = new Task(text.get());
    }
    // TODO: convert priority to enum
    //task.setPriority(null);
    String formPriorityType = priority.get().toUpperCase();
    try {
      PriorityType priorityType = PriorityType.valueOf(formPriorityType);
      System.out.println("Enum valueOf is: " + priorityType);
      task.setPriority(priorityType);
    } catch(Exception e) {
      System.out.println("Could not match priority type; it was: " + formPriorityType);
    }
    task.setNotes(notes.get());
    taskService.save(task);

    return "redirect:/todo/task?success=1"; // success 1: create or update successful ("task saved successfully")
  }

  @GetMapping("/deleteCompleted")
  public String deleteCompleted() {
    taskService.deleteByCompletedTrueCustom();
    return "redirect:/todo/task?success=4"; // success 4: successfully deleted completed tasks using custom query
  }
}
