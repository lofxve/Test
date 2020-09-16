package com.example.Base;

import com.example.Entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SeleniumhqCase
 * @Description: //TODO Seleniumhq初始化
 * @Auther: weizhendong
 * @Date: 16:36 2020/9/15
 **/
@Slf4j
@Service
public class SeleniumhqCase{
    WebDriver driver;
    /**
     * @Author weizhendong
     * @Description //TODO 初始化驱动器
     * @Date 16:37 2020/9/15
     * @Param [entity]
     * @return
     **/
    public SeleniumhqCase(){
        System.setProperty("webdriver.chrome.driver", "c://chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    /**
     * @Author weizhendong
     * @Description //TODO 使用客户端打开一个新的页面
     * @Date 16:37 2020/9/15
     * @Param [entity]
     * @return void
     **/
    public void client(Entity entity){
        driver.get(entity.getUrl());
    }
    /**
     * @Author weizhendong
     * @Description //TODO 测试的每一步执行
     * @Date 16:38 2020/9/15
     * @Param [entity]
     * @return void
     **/
    public void step(Entity entity) throws InterruptedException {
        // id             WebElement element = driver.findElement(By.id("coolestWidgetEvah"));
        // className          List<WebElement> cheeses = driver.findElements(By.className("cheese"));
        // tagName        WebElement frame = driver.findElement(By.tagName("iframe"));
        // name           WebElement cheese = driver.findElement(By.name("cheese"));
        // linkText       WebElement cheese = driver.findElement(By.linkText("cheese"));
        //partialLinkText WebElement cheese = driver.findElement(By.partialLinkText("cheese"));
        // xpath          WebElement cheese = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div/div/input"));
        // css            WebElement cheese = driver.findElement(By.cssSelector("#food span.dairy.aged"));
        WebElement cheese = null;

        if (!entity.getType().equals(" ")){
            switch (entity.getType()){
                case "xpath":
                    cheese = driver.findElement(By.xpath(entity.getLocation()));break;
                case "id":
                    cheese = driver.findElement(By.id(entity.getLocation()));break;
                case "className":
                    List<WebElement> cheeses = driver.findElements(By.className(entity.getLocation()));break;
                case "tagName":
                    cheese = driver.findElement(By.tagName(entity.getLocation()));break;
                case "name":
                    cheese = driver.findElement(By.name(entity.getLocation()));break;
                case "linkText":
                    cheese = driver.findElement(By.linkText(entity.getLocation()));break;
                case "partialLinkText":
                    cheese = driver.findElement(By.partialLinkText(entity.getLocation()));break;
                case "css":
                    cheese = driver.findElement(By.cssSelector(entity.getLocation()));break;
            }
            if (!entity.getOperation().equals(" ")){
                switch (entity.getOperation()){
                    case "sendkey":
                        sendkey(cheese,entity);break;
                    case "click":
                        cheese.click();break;
                }

            }else{
                log.info("{} ==>实体Operation为空",entity.getName());
            }
        }else{
            log.info("{} ==>实体type为空",entity.getName());
        }
        if (entity.getSleep()!=0){
            sleep(entity);
        }
        log.info("id = {},name = {} step==>执行结束",entity.getId(),entity.getName());
    }

    /**
     * @Author weizhendong
     * @Description //TODO 输入文本框
     * @Date 16:38 2020/9/15
     * @Param [cheese, entity]
     * @return void
     **/
    public void sendkey(WebElement cheese,Entity entity){
        cheese.sendKeys(entity.getKey());
        log.info("=====>{} sendkey==>执行结束 set value====>{}",entity.getName(),entity.getKey());
    }
    /**
     * @Author weizhendong
     * @Description //TODO 关闭浏览器
     * @Date 16:38 2020/9/15
     * @Param []
     * @return void
     **/
    public void close(){
        driver.close();
    }

    /**
     * @Author weizhendong
     * @Description //TODO 睡眠
     * @Date 16:39 2020/9/15
     * @Param [entity]
     * @return void
     **/
    public void sleep(Entity entity) throws InterruptedException {
        Thread.sleep(entity.getSleep());
    }
}
