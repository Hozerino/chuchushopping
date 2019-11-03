package ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/loja/{label}")
    public String loja(@PathVariable("label") String label) {
        return "/loja-single.html";
    }

}
