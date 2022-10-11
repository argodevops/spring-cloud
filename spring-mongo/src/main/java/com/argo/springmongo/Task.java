package com.argo.springmongo;

import java.util.Date;
import lombok.Data;

import org.springframework.data.annotation.Id;

@Data
public class Task {

  @Id
  public String id;

  // TODO: make properties private and update elsewhere to use lombok getters
  public String text;
  public String notes;
  public Boolean completed = false;
  public PriorityType priority = PriorityType.NORMAL;

  // Advanced options
  public String location;
  public Date date;

  public Task() {}

  public Task(String text) {
    this.text = text;
  }

  public Task(String text, PriorityType priority) {
    this.text = text;
    this.priority = priority;
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
  }
}
