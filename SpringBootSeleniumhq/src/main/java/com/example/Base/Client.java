package com.example.Base;

import com.example.Util.ReadExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Client {
    public void run(String path){
        log.info("开始读取excel....");
        ReadExcelUtil entityUtil = new ReadExcelUtil(path);
        log.info("完成读取excel");

        SeleniumhqCase se = new SeleniumhqCase();
        log.info("完成打开浏览器");
        se.client(entityUtil.getClient());
        log.info("完成构建selenium客户端");
        entityUtil.getArrayList().forEach(entity -> {
            try {
                se.step(entity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
