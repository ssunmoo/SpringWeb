package com.Ezenweb.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delete-api")
public class DeleteController {

    // p.75
    @DeleteMapping("/{variable}")
    public String DeleteVariable( @PathVariable("variable") String variable ){
        return variable;
    }

    @DeleteMapping("/request1")
    public String getRequestParam1( @RequestParam("variable") String variable ){
        return variable;
    }





}
