package com.argo.springmongo;

import java.util.Date;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.ui.Model;

@Data
public class Task {

  @Id
  private String id;

  private String text;
  private String notes;
  private Boolean completed = false;
  private PriorityType priority = PriorityType.NORMAL;
  private Date createdDate;

  // Advanced options
  private String location;
  private Date dueDate;
  private Date completedDate;

  public Task() {}

  public Task(String text) {
    this.text = text;
    this.createdDate = new Date();
  }

  public Task(String text, PriorityType priority) {
    this.text = text;
    this.priority = priority;
    this.createdDate = new Date();
  }

  @Override
  public String toString() {
    return String.format(
      "Task '%s' [id=%s]",
      text,
      id
    );
  }

  public void complete() {
    this.completed = true;
    this.completedDate = new Date();
  }

  public Model injectIntoModel(Model model) {
    model.addAttribute("id", this.id);
    model.addAttribute("text", this.text);
    model.addAttribute("priority", this.priority);
    model.addAttribute("notes", this.notes);
    return model;
  }

}
