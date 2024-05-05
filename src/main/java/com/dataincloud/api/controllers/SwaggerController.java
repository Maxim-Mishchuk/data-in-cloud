package com.dataincloud.api.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class SwaggerController {
    @GetMapping
    RedirectView redirect() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
