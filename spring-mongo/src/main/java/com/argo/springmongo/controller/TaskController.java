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
  public ResponseEntity<HttpStatus> delete() {
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

  @DeleteMapping("/completed")
  public ResponseEntity<HttpStatus> deleteCompleted() {
    Boolean authorised = true;
    if (authorised) {
      taskService.deleteByCompletedTrueCustom();
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
