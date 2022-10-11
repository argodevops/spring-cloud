package com.argo.springmongo;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Task {

  @Id
  public String id;

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
