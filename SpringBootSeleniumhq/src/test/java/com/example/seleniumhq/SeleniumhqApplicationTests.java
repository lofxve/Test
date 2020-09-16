package com.example.seleniumhq;

import com.example.Base.Client;
import com.example.Base.SeleniumhqCase;
import com.example.Entity.Entity;
import com.example.Util.ReadExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class SeleniumhqApplicationTests {
    @Test
    void clientRunHuoYun(){
        Client client = new Client();
        String path = "D:\\java\\Spring项目\\自动化测试\\自动化测试\\SpringBootSeleniumhq\\src\\main\\resources\\货运系统测试.xls";
        client.run(path);
    }
    @Test
    void clientRunBaiDu(){
        Client client = new Client();
        String path = "D:\\java\\Spring项目\\自动化测试\\自动化测试\\SpringBootSeleniumhq\\src\\main\\resources\\百度一下.xls";
        client.run(path);
    }

    /**
     * @Author weizhendong
     * @Description //TODO 读取excel并进行自动化测试
     * @Date 14:13 2020/9/16
     * @Param []
     * @return void
     **/
    @Test
    void exclerun() throws InterruptedException {
        String path = "D:\\java\\Spring项目\\自动化测试\\自动化测试\\SpringBootSeleniumhq\\src\\main\\resources\\百度一下.xls";
        ReadExcelUtil entityUtil = new ReadExcelUtil(path);
        SeleniumhqCase se = new SeleniumhqCase();
        log.info("打开浏览器");
        se.client(entityUtil.getClient());
        entityUtil.getArrayList().forEach(entity -> {
            try {
                se.step(entity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @Author weizhendong
     * @Description //TODO excel读取
     * @Date 14:12 2020/9/16
     * @Param []
     * @return void
     **/
    @Test
    void readexcel(){
        String path = "D:\\java\\Spring项目\\自动化测试\\自动化测试\\SpringBootSeleniumhq\\src\\main\\resources\\百度一下.xls";
        ReadExcelUtil obj = new ReadExcelUtil(path);

        List excelList = obj.getArrayList();

        System.out.println("构建client>");
        Entity client = obj.getClient();
        System.out.println("构建List<Entity>");
        List<Entity> arr = new ArrayList<>();
        for (int i = 2; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            arr.add(obj.listToEntity(list));
        }
        System.out.println("打印client");
        System.out.println(client.toString());
        System.out.println("list中的数据打印出来");
        arr.forEach(entity -> {
            System.out.println(entity.toString());
        });
    }

    /**
     * @Author weizhendong
     * @Description //TODO 自动化基础
     * @Date 14:11 2020/9/16
     * @Param []
     * @return void
     **/
    @Test
    void contextLoads() throws InterruptedException {
    System.setProperty("webdriver.chrome.driver", "c://chromedriver.exe");
    WebDriver webDriver = new ChromeDriver();
    webDriver.get("https://www.baidu.com/");
    webDriver.findElement(By.xpath("//*[@id=\"kw\"]")).sendKeys("admin");
    webDriver.findElement(By.xpath("//*[@id=\"su\"]")).click();
    Thread.sleep(5000);
    }
    
    /**
     * @Author weizhendong
     * @Description //TODO 百度搜索
     * @Date 14:11 2020/9/16
     * @Param []
     * @return void
     **/
    @Test
    void baidu() throws InterruptedException {
        SeleniumhqCase se = new SeleniumhqCase();
        log.info("打开浏览器");
        se.client(
                Entity.builder()
                        .name("登录")
                        .url("https://www.baidu.com/")
                        .build()
        );
        se.sleep(
                Entity.builder()
                        .name("睡眠")
                        .sleep(10000)
                        .build()
        );
        se.step(
                Entity.builder()
                        .name("用户名")
                        .type("xpath")
                        .location("//*[@id=\"kw\"]")
                        .operation("sendkey")
                        .key("HY003")
                        .build()
        );
        se.step(
                Entity.builder()
                        .name("点击")
                        .type("xpath")
                        .location("//*[@id=\"su\"]")
                        .operation("click")
                        .build()
        );
        Thread.sleep(1000);
    }
    
    /**
     * @Author weizhendong
     * @Description //TODO 登录货运系统
     * @Date 14:11 2020/9/16
     * @Param []
     * @return void
     **/
    @Test
    void login() throws InterruptedException {
        SeleniumhqCase se = new SeleniumhqCase();
        log.info("打开浏览器");
        se.client(
                Entity.builder()
                        .name("登录")
                        .url("http://192.168.8.126:9501/#/login?redirect=%2Fdashboard")
                        .build()
        );
        se.step(
                Entity.builder()
                        .name("用户名")
                        .type("xpath")
                        .location("//*[@id=\"app\"]/div[1]/div/form/div[1]/div/div/input")
                        .operation("sendkey")
                        .key("HY003")
                        .build()
        );
        se.step(
                Entity.builder()
                        .name("密码")
                        .type("xpath")
                        .location("//*[@id=\"app\"]/div[1]/div/form/div[2]/div/div/input")
                        .operation("sendkey")
                        .key("12345678")
                        .build()
        );
        se.step(
                Entity.builder()
                        .name("点击")
                        .type("tagName")
                        .location("button")
                        .operation("click")
                        .build()
        );
        se.sleep(
                Entity.builder()
                        .name("睡眠")
                        .sleep(10000)
                        .build()
        );
        se.close();
    }

}
