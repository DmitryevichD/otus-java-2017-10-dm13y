package by.dm13y.study.msgsys.webclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CacheInfoCtrl {
    @GetMapping("/")
    public String infoPage(){
        return "index";
    }
}
