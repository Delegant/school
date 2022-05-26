package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value(("${server.port}"))
    private String serverPort;

    @GetMapping("/get_info")
    public String getInfo(){
        return serverPort;
    }
}
