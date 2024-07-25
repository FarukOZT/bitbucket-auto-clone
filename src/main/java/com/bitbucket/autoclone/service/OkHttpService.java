package com.bitbucket.autoclone.service;

import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public interface OkHttpService {
    Response requestBB(Request request) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException;

}
