package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.configuration.security.JWTRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("loginData", new JWTRequest());
        return "index";
    }
}
