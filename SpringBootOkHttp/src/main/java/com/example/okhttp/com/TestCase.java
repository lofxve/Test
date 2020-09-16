package com.example.okhttp.com;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TestCase
 * @Description: //TODO 实体类
 * @Auther: weizhendong
 * @Date: 21:33 2020/9/15
 **/
@Data
public class TestCase {
    //用例输入
    private String name;
    private String method;
    private String path;

    private String contentType;
    private JSONObject jsonquery = new JSONObject();
    private Map<String,String> form = new HashMap<String,String>();
    private Map<String,String> query = new HashMap<String,String>();
    private byte[] requestBody;


    //用例输出
    private int statusCode;
    private byte[] responseBody;
    private String responseJson;
    private long startTime;
    private long endTime;
    private long responseTime;
    private String location;
}
