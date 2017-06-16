package com.tenshun.agss.rest;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Gateway {

    @GetMapping("/")
    public String test(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
