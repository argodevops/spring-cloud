package com.argo.springmongo.controller;

import com.argo.springmongo.*;
import com.argo.springmongo.service.TaskService;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.connector.Response;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * GET /api/tasks -> [tasks]
 * GET /api/tasks/{uuid} -> task
 * POST /api/tasks {}
 * POST /api/tasks/{uuid}/complete
 * PATCH /api/tasks/{uuid}
 * PUT /api/tasks/{uuid}
 * DELETE /api/tasks
 * DELETE /api/tasks/{uuid}
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;
  private static Logger logger = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  // TODO: get logger working
  public void p(String t) {
    System.out.println(t);
  }

  @GetMapping(value = "")
  public ResponseEntity<List<Task>> get() {
    return ResponseEntity.ok(taskService.getAll());
  }

  @GetMapping(value = "/{uuid}")
  public ResponseEntity<Task> get(@PathVariable String uuid) {
    Task task = taskService.getByid(uuid);
    logger.debug("Searched for task" + uuid + " and the result is:");
    logger.debug(String.valueOf(task));

    p("Searched for task " + uuid + " and the result is:");
    p(String.valueOf(task));
    p("It's only normal print working and not logger");

    if (task != null) {
      return ResponseEntity.ok(task);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping(value = "")
  public ResponseEntity<List<Task>> add(@RequestBody List<Task> tasks) {
    if (taskService.validate(tasks)) {
      taskService.save(tasks);
      return ResponseEntity.ok(tasks);
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping(value = "/{uuid}/complete")
  public ResponseEntity<Task> complete(@PathVariable String uuid) {
    Task task = taskService.getByid(uuid);
    if (task != null) {
      taskService.complete(task);
      return ResponseEntity.ok(task);
    }
    return ResponseEntity.notFound().build();
  }

  // TODO: not sure if this is the intended use of PATCH ?
  @PatchMapping(value = "/{uuid}")
  public ResponseEntity<Task> patch(@PathVariable String uuid, String text) {
    Task task = taskService.getByid(uuid);
    if (task != null && !text.isEmpty()) {
      task.setText(text);
      taskService.save(task);
      return ResponseEntity.ok(task);
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping(value = "/{uuid}")
  public ResponseEntity<Task> update(
    @PathVariable String uuid,
    @RequestBody Task newTask
  ) {
    Task task = taskService.getByid(uuid);
    if (task != null) {
      taskService.save(newTask);
      return ResponseEntity.ok(newTask);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping(value = "")
  public ResponseEntity delete() {
    Boolean authorised = true;
    // TODO: add authorisation
    if (authorised) {
      taskService.clearRepository();
      return ResponseEntity.accepted().build();
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @DeleteMapping(value = "/{uuid}")
  public ResponseEntity<Task> delete(@PathVariable String uuid) {
    Boolean authorised = true;
    Task task = taskService.getByid(uuid);
    if (task == null) {
      return ResponseEntity.notFound().build();
    }
    // TODO: add authorisation
    if (authorised) {
      taskService.requestDeletion(task);
      return ResponseEntity.ok(task);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  // Former methods

  @RequestMapping(value = "/complete", method = RequestMethod.GET)
  public String complete(@RequestParam("id") Optional<String> id) {
    Task task = taskService.getByid(id.get());

    if (!id.isPresent() || task == null) {
      System.out.println("ERROR: Couldn't find the task to complete");
      return "redirect:/todo/task?error=3";
    }

    task.complete();
    taskService.save(task);
    return "redirect:/todo/task?success=3"; // success 3: task completed
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(@RequestParam("id") Optional<String> id) {
    Task task = taskService.getByid(id.get());

    if (!id.isPresent() || task == null) {
      System.out.println("ERROR: Couldn't find the task to edit");
      return "redirect:/todo/task?error=1"; // Send to list of all tasks if no id or not task with id
    }

    return "taskForm";
  }

  @GetMapping("/delete")
  public String delete(@RequestParam("id") Optional<String> id) {
    Task task = taskService.getByid(id.get());

    if (!id.isPresent() || task == null) {
      System.out.println("ERROR: Couldn't find the task to delete");
      return "redirect:/todo/task?error=2";
    }

    taskService.requestDeletion(task);
    return "redirect:/todo/task?success=2"; // success 2: task deleted successfully
  }

  @PostMapping("/handle")
  public String handleForm( // Can't I just request a response of type Task ?
    @RequestParam("id") Optional<String> id,
    @RequestParam("text") Optional<String> text,
    @RequestParam("priority") Optional<String> priority,
    @RequestParam("notes") Optional<String> notes
  ) {
    if (!text.isPresent() || text.get().trim().isEmpty()) {
      System.out.println("No text passed from taskForm");
      return "redirect:/todo/task?error=4";
    }

    Task task = taskService.getByid(id.get());

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
    } catch (Exception e) {
      System.out.println(
        "Could not match priority type; it was: " + formPriorityType
      );
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
