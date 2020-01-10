package com.mybatis.sinro.mybatisboot.common.excel.utils;

import com.mybatis.sinro.mybatisboot.common.utils.ExcelUtils;
import com.mybatis.sinro.mybatisboot.common.utils.FileUtils;
import com.mybatis.sinro.mybatisboot.common.utils.UUIDUtils;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @author: tan.hongwei
 * @Date: 2019/10/16 16:28
 * @Description: 提取公共生成EXCEL
 */
public class ExcelCreateFileUtils {

    /**
     * @param excel 整个表数据，解析查询数据获得
     * @param title 表头数组
     * @return
     */
    public static void createFile(List<List<Object>> excel, String[] title, HttpServletResponse response) {
        String excelFile = "";
        try {
            excelFile = ResourceUtils.getFile("")
                    .getAbsolutePath()
                    .concat(File.separator)
                    .concat("export")
                    .concat(File.separator)
                    .concat(UUIDUtils.create() + ExcelUtils.OFFICE_EXCEL_2007_POSTFIX);

            ExcelUtils.createExcel(excelFile);
            ExcelUtils.appendRow(excelFile, excel, title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileUtils.download(response, excelFile, ResourceUtils.FILE_URL_PREFIX);
        File file = new File(excelFile);
        if (file.exists()) {
            FileUtils.deleteDir(file);
        }
    }

}
