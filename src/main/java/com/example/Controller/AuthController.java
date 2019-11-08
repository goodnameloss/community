package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.example.entity.Accesstoken;
import com.example.entity.GitHubUser;
import com.example.provider.GitHubprovider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AuthController {
    @Autowired
    private GitHubprovider githubprovider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code, @RequestParam(name="state") String state,HttpServletRequest request){
        Accesstoken accesstoken = new Accesstoken();
        accesstoken.setCode(code);
        accesstoken.setState(state);
        accesstoken.setRedirect_uri(redirectUri);
        accesstoken.setClient_id(clientId);
        accesstoken.setClient_secret(clientSecret);
        String accessToken = githubprovider.getAccessToken(accesstoken);
        GitHubUser ghu=githubprovider.getUser(accessToken);
        if(ghu!=null){
            request.getSession().setAttribute("user",ghu);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }

}
