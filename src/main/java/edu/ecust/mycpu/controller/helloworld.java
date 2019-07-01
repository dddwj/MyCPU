package edu.ecust.mycpu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
public class helloworld {
    @GetMapping("/hello")
    public String showHello(){
        return "hello";
    }
}
