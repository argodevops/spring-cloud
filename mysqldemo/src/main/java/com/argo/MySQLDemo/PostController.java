package com.argo.MySQLDemo.controller;

import java.util.Optional;
//import org.apache.commons.text.StringEscapeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "post/")
public class PostController {

  @GetMapping("")
  public String postIndex() {
    return "You can <a href='/feed'>view all posts</a> or <a href='/add'>create a new one</a>.";
  }

  @GetMapping(path = "feed")
  public String getAllPosts() {
    return "Here are all the latest blog posts";
  }

  /*
  @GetMapping(path="view")
  public String viewPostIndex() {
    return "Please select <a href='view'>a post</a> to view.";
  }
  */

  @GetMapping(path = "view/{id}")
  public String viewPost(
    @RequestParam(name = "id", required = false, defaultValue = "0") String id
    // Tried to do as a path variable instead but currently fails on parseInt and escapeHtml package doesn't seem to exist
    // @PathVariable Optional<String> id
  ) {
    /*
    String output;
    if (! id.isPresent() || Integer.parseInt(id.get()) != null) {
      output = id.get();
    } else {
      output = "0";
    };
    output = StringEscapeUtils.esapeHtml4(output);
    return "You are viewing post " + output;
    */
    return "You are viewing post " + id;
  }

  @PostMapping(path = "add")
  public String createPost() {
    return "Post saved!";
  }
}
