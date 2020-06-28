package com.common.util.excel;

import com.zhx.helper.exception.CreateWorkBookException;
import jxl.*;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReader {

    private static final int DATA_ROW_INDEX_DEFAULT = 0;

    private Workbook excel;

    private ExcelReader(InputStream inputStream){
        try {
            WorkbookSettings settings = new WorkbookSettings();
            settings.setEncoding("iso-8859-1");
            this.excel = Workbook.getWorkbook(inputStream,settings);
        } catch (Exception e) {
            throw new CreateWorkBookException(e.getMessage());
        }
    }


    /**
     * @return
     */
    public List<List<String>> read(){
        return read(DATA_ROW_INDEX_DEFAULT);
    }

    /**
     *
     * @param dataRowIndex
     * @return
     */
    public List<List<String>> read(int dataRowIndex){
        if(dataRowIndex < 0){
            throw new IllegalArgumentException("dataRowIndex should be great than or equal to zero, now is " + dataRowIndex);
        }
        Sheet[] sheets = excel.getSheets();
        List<List<String>> result = new ArrayList<List<String>>();
        Map<String,Object> map = new HashMap<String,Object>();
        for (Sheet sheet : sheets) {
            int rows = sheet.getRows();
            int columns = sheet.getColumns();
            for(int i = dataRowIndex;i < rows;i++){
                List<String> list = new ArrayList<String>();
                for(int j = 0;j < columns;j++){
                    if(i==0){
                        map.put("title_"+j,sheet.getCell(j, i).getContents());
                        continue;
                    }
                    String content = "";

                    if ("报价时间".equals(map.get("title_"+j))) {
                        Cell cell = sheet.getCell(j, i);
                        try{
                            DateCell dc = (DateCell) cell;
                            Date data = dc.getDate();
                            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                            content = dataFormat.format(data);
                        }catch (Exception e){
                            content = sheet.getCell(j, i).getContents()+"&&报价时间格式错误，格式为1990-1-1";
                        }
                    } else {
                        content = sheet.getCell(j, i).getContents();
                    }

                    list.add(content);
                }
                result.add(list);
            }
        }

        return result;
    }

    /**
     * @param inputStream
     * @return
     */
    public static ExcelReader me(InputStream inputStream){
        return new ExcelReader(inputStream);
    }
}
