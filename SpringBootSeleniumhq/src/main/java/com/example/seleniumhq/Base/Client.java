package com.example.seleniumhq.Base;

import com.example.seleniumhq.Util.ReadExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: Client
 * @Description: //TODO Seleniumhq客户端
 * @Auther: weizhendong
 * @Date: 21:03 2020/9/16
 **/
@Service
@Slf4j
public class Client {
    @Autowired
    private ReadExcelUtil readExcelUtil;
    @Autowired
    private SeleniumhqCase seleniumhqCase;

    public void run(String path){
        log.info("开始读取excel....");
        readExcelUtil.createReadExcelUtil(path);
        log.info("完成读取excel");

        seleniumhqCase.client(readExcelUtil.getClient());
        log.info("完成构建selenium客户端");

        //执行每一个步骤
        readExcelUtil.getArrayList().forEach(entity -> {
            try {
                seleniumhqCase.step(entity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
