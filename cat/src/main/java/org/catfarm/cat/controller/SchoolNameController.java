package org.catfarm.cat.controller;// SchoolNameController.java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolNameController {

    @GetMapping(value = "/name")
    public String getName() {
        return "NOVI";
    }
}