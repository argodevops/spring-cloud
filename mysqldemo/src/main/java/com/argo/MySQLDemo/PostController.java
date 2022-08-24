package com.argo.MySQLDemo;

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
import org.springframework.ui.Model;

@Controller
@RequestMapping(path = "post/")
public class PostController {

  @Autowired
  private PostRepository postRepository;

  @GetMapping("")
  @ResponseBody
  public String postIndex() {
    return "You can <a href='/view/feed'>view all posts</a> or <a href='/view/create'>create a new one</a>.";
  }

  @GetMapping(path = "feed")
  public @ResponseBody Iterable<Post> getAllPosts() {
    return postRepository.findAll();
  }

  /*
  @GetMapping(path="view")
  public String viewPostIndex() {
    return "Please select <a href='view'>a post</a> to view.";
  }
  */

  @GetMapping(path = "view/{id}")
  @ResponseBody
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

  // User interface to create a post
  @GetMapping(path = "create")
  public String postForm(Model model) {
    return "createPostForm";
  }

  // Adds a post from a POST request
  @PostMapping(path = "/add")
  public @ResponseBody String addPost(
    @RequestParam String title,
    @RequestParam String body,
    @RequestParam Integer owner_id
  ) {
    // We want to escape HTML for these values too
    Post p = new Post();
    p.setTitle(title);
    p.setBody(body);
    // parseInt on owner_id ? (wasn't necessary)
    p.setOwner(owner_id);
    postRepository.save(p);
    return "Post <i>" + title + "</i> has been saved! Would you like to <a href='/view/feed'>view all posts</a>?";
  }
}
