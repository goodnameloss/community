package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.example.entity.Accesstoken;
import com.example.entity.GitHubUser;
import com.example.provider.GitHubprovider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthController {
    @Autowired
    private GitHubprovider githubprovider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state){
        Accesstoken accesstoken = new Accesstoken();
        accesstoken.setCode(code);
        accesstoken.setState(state);
        accesstoken.setRedirect_uri("http://localhost:8080/callback");
        accesstoken.setClient_id("f98e36b1731973d97992");
        accesstoken.setClient_secret("794d51a1aba9dd3af49f30a1666363735a734538");
        String accessToken = githubprovider.getAccessToken(accesstoken);
        GitHubUser ghu=githubprovider.getUser(accessToken);

        return "index";
    }

}
