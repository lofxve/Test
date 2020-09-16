package com.example.seleniumhq.Entity;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: Entity
 * @Description: //TODO step 实体类
 * @Auther: weizhendong
 * @Date: 16:36 2020/9/15
 **/
@Data
@Builder
public class Entity {
    private long id;
    private String name;
    private String url;

    //定位方式
    // id             WebElement element = driver.findElement(By.id("coolestWidgetEvah"));
    // className          List<WebElement> cheeses = driver.findElements(By.className("cheese"));
    // tagName        WebElement frame = driver.findElement(By.tagName("iframe"));
    // name           WebElement cheese = driver.findElement(By.name("cheese"));
    // linkText       WebElement cheese = driver.findElement(By.linkText("cheese"));
    //partialLinkText WebElement cheese = driver.findElement(By.partialLinkText("cheese"));
    // xpath          WebElement cheese = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div/div/input"));
    // css            WebElement cheese = driver.findElement(By.cssSelector("#food span.dairy.aged"));
    private String type;
    //位置
    private String location;

    //操作
    //sendkey
    //click
    private String operation;
    //操作值
    private String key;

    private long sleep;

    private String describe;
}
