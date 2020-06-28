package com.common.util.excel;

/*import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
*/

public class ExcelHandler {

    public final static String FILE_TYPE = "xls" ;

    //文件格式处理
  /*  public static void fileTypeValidate( FileItem fileItem ) throws Exception{
        String[] namePix = fileItem.getName().split("\\u002E");
        if( ! namePix[namePix.length-1].equals( FILE_TYPE ) ){
            throw new Exception( "您导入的文件格式错误，请导入.xls后缀的文件." ) ;
        }
    }*/

    /****
     * excel文件读取数据
     * @param file
     * @return
     */
    /*public static List<List<String>> readExcel(MultipartFile file) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            List<List<String>> results = ExcelReader.me(in).read();
            return results;
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }*/


}
