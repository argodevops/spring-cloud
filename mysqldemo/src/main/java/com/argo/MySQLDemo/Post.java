package com.argo.MySQLDemo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String title;

  private String body;

  private Integer owner_id;

  private Boolean published = false;

  private String[] drafts;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    // this.drafts append(this.body) etc
    this.body = body;
  }

  public void setOwner(Integer owner_id) {
    this.owner_id = owner_id;
  }

  public Integer getOwner() {
    // In future could return a User object matching the given ID
    return this.owner_id;
  }

  public void publish() {
    this.published = true;
  }

  public void hide() {
    this.published = false;
  }
}