package com.bitbucket.autoclone.controller;

import com.bitbucket.autoclone.service.BBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@RestController
public class Controller {

    @Autowired
    private BBService service;

    @GetMapping("/get")
    public void getRepo() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        service.gitClone();
    }
}
