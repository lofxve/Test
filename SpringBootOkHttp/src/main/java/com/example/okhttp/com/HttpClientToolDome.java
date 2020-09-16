package com.example.okhttp.com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * @ClassName: HttpClientTool
 * @Description: //TODO okHttp 工具类
 * @Auther: weizhendong
 * @Date: 21:31 2020/9/15
 **/
@Slf4j
@Service
public class HttpClientToolDome {
    /**
     * @Author weizhendong
     * @Description //TODO 拼接url
     * @Date 21:32 2020/9/15
     * @Param [testCase]
     * @return java.lang.String
     **/
    public static String contactUrl(TestCase testCase){
        log.info("url拼接");
        //拼接
        HttpUrl.Builder url = new HttpUrl.Builder();
        url.scheme(System.getProperty("http"));
        url.host(System.getProperty("host"));
        url.addPathSegment(testCase.getPath());
        Iterator<Map.Entry<String, String>> iterator = testCase.getQuery().entrySet().iterator();
        while (iterator.hasNext()) {
            //add form parameters key value
            Map.Entry<String, String> next = iterator.next();
            //添加body 键值对 userId：10000
            url.addQueryParameter(next.getKey(), next.getValue());
        }
        url.build();
        return String.valueOf(url);
    }
    public String stringToJson(String str){
        JSONObject object = JSONObject.parseObject(str);
        String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        return pretty;
    }

    /**
     * @Author weizhendong
     * @Description //TODO 运行一个testCase
     * @Date 21:32 2020/9/15
     * @Param [testcase, mOkHttpClient]
     * @return void
     **/
    public static void runTestCase(TestCase testcase, OkHttpClient mOkHttpClient) throws IOException {
        log.info("Test Case Name %s Run 开始", testcase.getName());
        HttpUrl loginUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("47.104.96.72")
                .port(8082)
                .build();

        //获取需要提交的CookieStr
        StringBuilder cookieStr = new StringBuilder();
        //从缓存中获取Cookie
        List<Cookie> cookies = mOkHttpClient.cookieJar().loadForRequest(loginUrl);
        //将Cookie数据弄成一行
        for(Cookie cookie : cookies){
            cookieStr.append(cookie.name()).append("=").append(cookie.value()+";");
        }
        cookieStr.append("userName").append("=").append("wzd"+";");
        cookieStr.append("password").append("=").append("123456789"+";");
        cookieStr.append("token").append("=").append("aaab_a8c3197f4c2944da9db2d8269aae39815ea9cac1ddc34f3786b5b5d6df266d7a"+";");
        System.out.println(cookieStr.toString());

        Request request = null;
        RequestBody body = null;
        //选择请求方式
        switch (testcase.getMethod())
        {
            case "GET":{
                log.info("GET");
                //构造Request对象
                request = new Request.Builder()
                        .url(testcase.getPath())
                        .build();
            }break;
            case "POST":{
                log.info("POST");
                //选择contextType form or json
                switch (testcase.getContentType())
                {
                    case "application/x-www-form-urlencoded; charset=UTF-8": {
                        log.info("POST FORM");
                        //构建请求form
                        FormBody.Builder formbody = new FormBody.Builder();

                        Iterator<Map.Entry<String, String>> iterator = testcase.getForm().entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            formbody.add(next.getKey(), next.getValue());
                        }
                        //获得够建请求requestbody
                        body = formbody.build();
                    }break;
                    case "application/json; charset=utf-8": {
                        log.info("POST JSON");
                        //构建请求json
                        Object obj = JSONArray.toJSON(testcase.getJsonquery());
//                        String json = obj.toString();
//                        log.info(json);
//                        //MediaType  设置Content-Type 标头中包含的媒体类型值
//                        body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
//                                , json);

                        //获得够建请求requestbody
                        body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                                , obj.toString());
                    }break;
                }
                String cookie;
                if (cookieStr.toString().equals("")){
                    cookie = "";
                }else{
                    cookie = cookieStr.toString();
                }
                //构造Request对象
                request = new Request.Builder()
                        .url(testcase.getPath())
                        .post(body)
                        .header("Cookie", cookie)
                        .addHeader("Accept","application/json, text/javascript, */*; q=0.01")
                        .addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
                        .addHeader("Accept-Encoding","gzip, deflate")
                        .addHeader("lang","zh")
                        .addHeader("X-Requested-With","XMLHttpRequest")
                        .addHeader("Connection","keep-alive")
                        .addHeader("Pragma","no-cache")
                        .addHeader("Cache-Control","no-cache")
                        .addHeader("fsid","aaab_a8c3197f4c2944da9db2d8269aae39815ea9cac1ddc34f3786b5b5d6df266d7a")
                        .build();
                System.out.println(request.header("Cookie"));
            }break;
        }


        //构建请求客户端
        Call call = mOkHttpClient.newCall(request);
        //获取响应体
        Response response = call.execute();

        try {
            //获取返回数据的头部
            Headers headers = response.headers();
            //获取头部的Cookie,注意：可以通过Cooke.parseAll()来获取
            cookies = Cookie.parseAll(loginUrl, headers);
            //防止header没有Cookie的情况
            if (cookies != null){
                //存储到Cookie管理器中
                mOkHttpClient.cookieJar().saveFromResponse(loginUrl, cookies);//这样就将Cookie存储到缓存中了
            }

            Headers responseHeaders = response.headers();
            log.info("请求体");
            log.info(String.format("request : %s",response.request()));

            log.info(String.format("responseHeaders Cookie : %s",responseHeaders.toString()));

            log.info("响应头");
            for (int i = 0; i < responseHeaders.size(); i++) {
                log.info(String.format("Headers %s : %s",responseHeaders.name(i),responseHeaders.value(i)));
            }
            log.info("响应体");
            log.info(String.format("code : %s",response.code()));
//            log.info(String.format("handshake : %s",response.handshake()));
            log.info(String.format("responsebody : %s",response.body().toString()));
            //只能输出一次
            testcase.setResponseJson(response.body().string());
//            log.info(String.format("networkResponse : %s",response.networkResponse()));
//            log.info(String.format("cacheResponse : %s",response.cacheResponse()));
//            log.info(String.format("priorResponse : %s",response.priorResponse()));
//            log.info(String.format("sentRequestAtMillis : %s",response.sentRequestAtMillis()));
//            log.info(String.format("receivedResponseAtMillis : %s",response.receivedResponseAtMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
