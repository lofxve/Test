package com.example.okhttp.integrated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.okhttp.com.HttpClientTool;
import com.example.okhttp.com.TestCase;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: LoginTestCase
 * @Description: //TODO 货运系统登录
 * @Auther: weizhendong
 * @Date: 18:20 2020/9/16
 **/
@Slf4j
@Service
public class LoginTestCase {
    public String testLoginInstaller(HttpClientTool httpClientTool,OkHttpClient mOkHttpClient,User user) throws IOException {
        TestCase testCase = new TestCase();
        testCase.setName("登录");
        testCase.setPath(user.getBaseurl()+"/api/user/login");
        testCase.setMethod("POST");
        testCase.setContentType("application/x-www-form-urlencoded; charset=UTF-8");

        //查询
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("userName", user.getUserName());
        map.put("password", user.getPassword());
        testCase.setForm(map);

        //运行
        log.info(testCase.toString());
        httpClientTool.runTestCase(testCase,mOkHttpClient,user);

        //输出json字符串
        String json = httpClientTool.stringToJson(testCase.getResponseJson());
        JSONObject jsonobj = JSON.parseObject(json);
        String data = jsonobj.getString("data");
        JSONObject dataobj = JSON.parseObject(data);
        return dataobj.getString("fsid");
    }
}
