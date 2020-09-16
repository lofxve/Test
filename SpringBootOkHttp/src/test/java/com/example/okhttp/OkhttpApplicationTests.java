package com.example.okhttp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.okhttp.com.HttpClientTool;
import com.example.okhttp.com.HttpClientToolDome;
import com.example.okhttp.com.TestCase;
import com.example.okhttp.integrated.LoginTestCase;
import com.example.okhttp.integrated.User;
import com.example.okhttp.integrated.systemManagement.GoodsCategory;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class OkhttpApplicationTests {
    /**
     * @Author weizhendong
     * @Description //TODO 创建一个OkHttp请求客户端，并记录cookie值
     * @Date 21:35 2020/9/15
     * @Param
     * @return
     **/
    CookieJar cookieJar = new CookieJar() {
        //Cookie缓存区
        private final Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();
        @Override
        public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
            // TODO Auto-generated method stub
            //移除相同的url的Cookie
            String host = arg0.host();
            List<Cookie> cookiesList = cookiesMap.get(host);
            if (cookiesList != null){
                cookiesMap.remove(host);
            }
            //再重新天添加
            cookiesMap.put(host, arg1);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl arg0) {
            // TODO Auto-generated method stub
            List<Cookie> cookiesList = cookiesMap.get(arg0.host());
            //注：这里不能返回null，否则会报NULLException的错误。
            //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
            return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
        }
    };
    OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .cookieJar(cookieJar)
            .build();

    HttpClientTool httpClientTool = new HttpClientTool();

    @Autowired
    private LoginTestCase loginTestCase;
    @Autowired
    private GoodsCategory goodsCategory;


    /**
     * @Author weizhendong
     * @Description //TODO 货邮类别
     * @Date 19:15 2020/9/16
     * @Param []
     * @return void
     **/
    @Test
    void testGoodsCategory() throws IOException {
        User userold = User.builder().userName("wzd").password("123456789").token("").baseurl("http://47.104.96.72:8082").build();
        String token = loginTestCase.testLoginInstaller(httpClientTool, mOkHttpClient,userold);
        User usernew = User.builder().userName("wzd").password("123456789").token(token).baseurl("http://47.104.96.72:8082").build();
        //增
        goodsCategory.add(httpClientTool,mOkHttpClient,usernew);
        //查
        String id = goodsCategory.list(httpClientTool,mOkHttpClient,usernew,"");
        System.out.println("del = "+id);
        //删
        goodsCategory.del(httpClientTool,mOkHttpClient,usernew,id);
        //查
        String nameid = goodsCategory.list(httpClientTool,mOkHttpClient,usernew,"饮品");
        System.out.println(nameid);
    }

    @Test
    void login() throws IOException {
        User userold = User.builder().userName("wzd").password("123456789").token("").build();
        String token = loginTestCase.testLoginInstaller(httpClientTool, mOkHttpClient,userold);
        System.out.println(token);
    }

    //1、get组合url查询 完成
    @Test
    void test1() throws IOException {
        log.info("1、get组合url查询 完成");
        //基础信息
        System.setProperty("http","http");
        System.setProperty("host","www.baidu.com");


        //用例
        TestCase testCase = new TestCase();
        //查询
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("wd", "okhttp");
        map.put("ie", "UTF-8");
        testCase.setQuery(map);
        //路径
        testCase.setPath("s");
        //请求
        testCase.setMethod("GET");


        HttpClientToolDome httpClientTool = new HttpClientToolDome();
        //url拼接
        testCase.setPath(httpClientTool.contactUrl(testCase));
        log.info(testCase.toString());


        //运行
        httpClientTool.runTestCase(testCase,mOkHttpClient);
    }

    //2、post json 请求
    @Test
    void test() throws IOException {
        log.info("2、post json 请求");
        HttpClientToolDome httpClientTool = new HttpClientToolDome();
        //用例
        TestCase testCase = new TestCase();
        testCase.setName("post json测试");
        //路径
        testCase.setPath("http://172.20.58.54:10000/iuap_server/auth/login");
        //请求
        testCase.setMethod("POST");
        //格式
        testCase.setContentType("application/json; charset=utf-8");
        //查询
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accessDataCollection",1);
        jsonObject.put("password","123");
        jsonObject.put("rememberMe",0);
        jsonObject.put("username","admin");
        testCase.setJsonquery(jsonObject);
        //运行
        log.info(testCase.toString());
        httpClientTool.runTestCase(testCase,mOkHttpClient);
    }

    //3、get
    @Test
    void test3() throws IOException {
        log.info("3、get");
        //用例
        TestCase testCase = new TestCase();
        //路径
        testCase.setPath("https://www.baidu.com/");
        //请求
        testCase.setMethod("GET");
        HttpClientToolDome httpClientTool = new HttpClientToolDome();
        log.info(testCase.toString());
        //运行
        httpClientTool.runTestCase(testCase,mOkHttpClient);
    }

    //4、post form 请求
    @Test
    void test4() throws IOException {
        log.info("4、post form");
        HttpClientToolDome httpClientTool = new HttpClientToolDome();
        //用例
        TestCase testCase = new TestCase();
        testCase.setName("post form");
        //路径
        testCase.setPath("http://172.20.58.54:10000/iuap_server/auth/login");
        //请求
        testCase.setMethod("POST");
        //格式
        testCase.setContentType("application/x-www-form-urlencoded");

        //查询
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("wd", "okhttp");
        map.put("ie", "UTF-8");
        testCase.setForm(map);

        //运行
        log.info(testCase.toString());
        httpClientTool.runTestCase(testCase,mOkHttpClient);
    }

    //url拼接get请求
    @Test
    void testGetSync() {
        log.info("testGetSync");
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("www.baidu.com")
                .addPathSegment("s")
                .addQueryParameter("wd", "okhttp")
                .addQueryParameter("ie", "UTF-8")
                .build();
        System.out.println(url);

        //OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //构造Request对象
        Request request = new Request.Builder()
                .url(url)
                .build();

        //将Request封装为Call
        Call call = okHttpClient.newCall(request);
        try {
            //输出response
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //post json
    @Test
    void testPostSync() throws IOException {
        log.info("testPostSync");
        String url = "http://172.20.58.54:10000/iuap_server/auth/login";
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accessDataCollection",1);
        jsonObject.put("password","123");
        jsonObject.put("rememberMe",0);
        jsonObject.put("username","admin");
        Object obj = JSONArray.toJSON(jsonObject);
        String json = obj.toString();
        log.info(json);
        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept","application/json, text/javascript, */*; q=0.01")
                .addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("lang","zh")
                .addHeader("X-Requested-With","XMLHttpRequest")
                .addHeader("Connection","keep-alive")
                .addHeader("Pragma","no-cache")
                .addHeader("Cache-Control","no-cache")
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
    }

    //post form
    @Test
    void testPostSync1() throws IOException {
        log.info("testPostSync");
        String url = "http://172.20.58.54:10000/iuap_server/auth/login";
        OkHttpClient okHttpClient = new OkHttpClient();


        HashMap<String,String> map = new HashMap<String,String>();
        map.put("accessDataCollection","1");
        map.put("password","123");
        map.put("rememberMe","0");
        map.put("username","admin");

        FormBody.Builder body = new FormBody.Builder();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            //add form parameters key value
            Map.Entry<String, String> next = iterator.next();
            //添加body 键值对 userId：10000
            body.add(next.getKey(), next.getValue());
        }
        log.info(body.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body.build())
                .addHeader("Accept","application/json, text/javascript, */*; q=0.01")
                .addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("lang","zh")
                .addHeader("X-Requested-With","XMLHttpRequest")
                .addHeader("Connection","keep-alive")
                .addHeader("Pragma","no-cache")
                .addHeader("Cache-Control","no-cache")
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
    }

}
