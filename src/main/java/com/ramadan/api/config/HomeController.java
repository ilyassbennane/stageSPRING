package com.ramadan.api.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Home redirection to OpenAPI api documentation
 * @author ZAROUATI Ayoub
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "redirect:swagger-ui/index.html";
    }

}