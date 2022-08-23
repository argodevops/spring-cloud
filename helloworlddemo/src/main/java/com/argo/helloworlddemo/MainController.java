package com.argo.helloworlddemo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String index(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return "Hello " + name + "!";
    }
}