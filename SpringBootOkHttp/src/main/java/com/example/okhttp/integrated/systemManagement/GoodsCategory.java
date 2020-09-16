package com.example.okhttp.integrated.systemManagement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.okhttp.com.HttpClientTool;
import com.example.okhttp.com.TestCase;
import com.example.okhttp.integrated.User;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName: GoodsCategory
 * @Description: //TODO 系统管理，货邮类别
 * @Auther: weizhendong
 * @Date: 16:14 2020/9/16
 **/
@Slf4j
@Service
public class GoodsCategory {
    /**
     * @Author weizhendong
     * @Description //TODO 增加货邮类别
     * @Date 18:21 2020/9/16
     * @Param [httpClientTool, mOkHttpClient, user]
     * @return void
     **/
    public void add(HttpClientTool httpClientTool, OkHttpClient mOkHttpClient, User user) throws IOException {
        //用例
        TestCase testCase = new TestCase();
        testCase.setName("系统管理===>货邮类别===>添加");
        //路径
        testCase.setPath(user.getBaseurl()+"/api/config/cargo/species/type/add");
        //请求
        testCase.setMethod("POST");
        //格式
        testCase.setContentType("application/json; charset=utf-8");
        //查询
        String jsonstr = "{\n" +
                "    \"cargoList\":[\n" +
                "        {\n" +
                "            \"cargoName\":\"23\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"cargoName\":\"36\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"typeName\":\"魏振东\",\n" +
                "    \"type\":1,\n" +
                "    \"typeCode\":\"007\",\n" +
                "    \"id\":null,\n" +
                "    \"name\":\"\"\n" +
                "}";
        JSONObject jsonObject = JSON.parseObject(jsonstr);

        testCase.setJsonquery(jsonObject);
        //运行
        log.info(testCase.toString());
        httpClientTool.runTestCase(testCase,mOkHttpClient,user);
        String json = httpClientTool.stringToJson(testCase.getResponseJson());
        System.out.println(json);
    }
    public String list(HttpClientTool httpClientTool, OkHttpClient mOkHttpClient, User user,String typeName) throws IOException {
        //用例
        TestCase testCase = new TestCase();
        testCase.setName("系统管理===>货邮类别===>获取所有");
        //路径
        testCase.setPath(user.getBaseurl()+"/api/config/cargo/species/type/list");
        //请求
        testCase.setMethod("POST");
        //格式
        testCase.setContentType("application/json; charset=utf-8");
        //查询
        String jsonstr = "{\"page\":1,\"count\":10,\"params\":{\"cargoName\":\"\",\"typeName\":\""+typeName+"\"}}";
        JSONObject jsonObject = JSON.parseObject(jsonstr);

        testCase.setJsonquery(jsonObject);
        //运行
        log.info(testCase.toString());
        httpClientTool.runTestCase(testCase,mOkHttpClient,user);
        String json = httpClientTool.stringToJson(testCase.getResponseJson());
        JSONObject jsonobj = JSON.parseObject(json);

        String data = jsonobj.getString("data");
        JSONObject dataobj = JSON.parseObject(data);

        String results = dataobj.getString("results");
        JSONArray resultsarr = JSON.parseArray(results);

        return resultsarr.getJSONObject(resultsarr.size()-1).getString("id");
    }
    public void del(HttpClientTool httpClientTool, OkHttpClient mOkHttpClient, User user,String id) throws IOException {
        //用例
        TestCase testCase = new TestCase();
        testCase.setName("系统管理===>货邮类别===>删除");
        //路径
        testCase.setPath(user.getBaseurl()+"/api/config/cargo/species/type/del/"+id);
        //请求
        testCase.setMethod("GET");
        //运行
        log.info(testCase.toString());
        httpClientTool.runTestCase(testCase,mOkHttpClient,user);
        String json = httpClientTool.stringToJson(testCase.getResponseJson());
        System.out.println(json);
    }
}
