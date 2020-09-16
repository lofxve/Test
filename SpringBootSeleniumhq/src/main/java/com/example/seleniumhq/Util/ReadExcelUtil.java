package com.example.seleniumhq.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.seleniumhq.Entity.Entity;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ReadExcelUtil
 * @Description: //TODO excel读取工具
 * @Auther: weizhendong
 * @Date: 11:52 2020/9/16
 **/
@Service
@Data
public class ReadExcelUtil{
    private Entity client;
    private List<Entity> arrayList = new ArrayList<>();;

    /**
     * @Author weizhendong
     * @Description //TODO 创建Entity
     * @Date 21:02 2020/9/16
     * @Param [path]
     * @return void
     **/
    public void createReadExcelUtil(String path){
        File file = new File(path);
        List excelList = readExcel(file);
//        System.out.println("构建client>");
        client = listToEntity((List)excelList.get(1));
//        System.out.println("构建List<Entity>");
        for (int i = 2; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            arrayList.add(listToEntity(list));
        }
    }

    /**
     * @Author weizhendong
     * @Description //TODO list转成Entity
     * @Date 12:02 2020/9/16
     * @Param [list]
     * @return com.example.seleniumhq.Entity.Entity
     **/
    public Entity listToEntity(List list){
        return Entity.builder()
                .id(Integer.parseInt((String) list.get(0)))
                .name((String) list.get(1))
                .url((String) list.get(2))
                .type((String) list.get(3))
                .location((String) list.get(4))
                .operation((String)list.get(5))
                .key((String)list.get(6))
                .sleep(Integer.parseInt(
                        list.get(7).equals("")?"0":(String)list.get(7)
                        )
                )
                .describe((String)list.get(8))
                .build();
    }

    /**
     * @Author weizhendong
     * @Description //TODO 去读Excel 返回List
     * @Date 12:03 2020/9/16
     * @Param [file]
     * @return java.util.List
     **/
    public List readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
//                        if(cellinfo.isEmpty()){
//                            continue;
//                        }
                        innerList.add(cellinfo);
//                        System.out.print(cellinfo);
                    }
                    outerList.add(i, innerList);
//                    System.out.println();
                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}