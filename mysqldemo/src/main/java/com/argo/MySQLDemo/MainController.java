package com.argo.MySQLDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class MainController {

  @GetMapping(path = "/")
  public String index(Model model) {
    model.addAttribute("title", "MySQL Demo Index");
    model.addAttribute("topText", "Where dreams come true");
    //model.addAttribute("menus", {});

    return "index";
    //return "<p>You have reached the home page!<p>Do you want to <a href='post/feed'>view posts</a>?</p>";
  }
}
