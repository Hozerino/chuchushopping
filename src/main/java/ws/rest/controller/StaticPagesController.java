package ws.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPagesController {

    @GetMapping("/")
    public String homepage() {
        return "/index.html";
    }

    @GetMapping("/lojas")
    public String lojas() {
        return "/lojas.html";
    }
}
