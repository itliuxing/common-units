package com.common.util.file;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/****
 * *
 * 类名称：		StringWriteFile.java 
 * 类描述：   		字符保存到文件中
 * 创建人：		
 * 创建时间：		2016-10-10下午4:39:52 
 * 修改人：		liuxing
 * 修改时间：		2016-10-10下午4:39:52 
 * 修改备注：   		
 * @version
 */
public class StringWriteFile {
	
	/***
	 * 将信息写入到文件中，指定文件
	 * @param info
	 * @return
	 */
	public static boolean writerToFile( String info) {
		return writerTo( new File("D:/123.txt") , info );
	}
	
	/***
	 * 将信息写入到文件中
	 * @param file
	 * @param info
	 * @return
	 */
	public static boolean writerTo(File file, String info) {
		FileWriter fileWrite;
		try {
			fileWrite = new FileWriter(file , true ); 	// 新建一个FileWriter,并且配置成追加写入
			/*fileWrite.write(info); 				// 将字符串写入到指定的路径下的文件中
			fileWrite.close();*/
		} catch (IOException e) {
			return false;
		}
		PrintWriter printWriter = new PrintWriter( fileWrite ); 
		printWriter.println( info ); 
		printWriter.flush(); 
		try { 
			fileWrite.flush(); 
			printWriter.close(); 
			fileWrite.close(); 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
		return true;
	}
	
	/***
	 * 将信息读出到List中
	 * @param file
	 * @return
	 */
	public static List<String> readToListByFile( File file ){
		List<String> urls = new ArrayList<String>() ;
		String urlTempLine = null ;
		 try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
            /* 读入TXT文件 */  
            InputStreamReader reader = new InputStreamReader( new FileInputStream(file)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            urlTempLine = br.readLine();  
            while (urlTempLine != null) {  
            	urlTempLine = br.readLine(); // 一次读入一行数据
				if(StringUtils.isNotBlank( urlTempLine ) && urlTempLine.length() > 45 ) {
					urls.add(urlTempLine.substring(0, 43));
				}
            }
		 }catch(Exception e) {
			 System.out.println( e.getMessage() );
		 }
		 return urls ;
	}


	/***
	 * 将信息读出到List中
	 * @param inputStream
	 * @return
	 */
	public static List<String> readToList( InputStream inputStream ){
		List<String> urls = new ArrayList<String>() ;
		String urlTempLine = null ;
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
			/* 读入TXT文件 */
			InputStreamReader reader = new InputStreamReader( inputStream ); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			urlTempLine = br.readLine();
			while (urlTempLine != null) {
				urlTempLine = br.readLine(); // 一次读入一行数据
				if(StringUtils.isNotBlank( urlTempLine ) ) {
					urls.add( urlTempLine );
				}
			}
		}catch(Exception e) {
			System.out.println( e.getMessage() );
		}
		return urls ;
	}


	public static void main(String[] args) {

		List<String> tempUrls = StringWriteFile.readToListByFile( new File("C:\\zcsjSipder\\huixun.txt") ) ;
		StringWriteFile.writerTo(new File("D:/123.txt"), "test" ) ;
	}
	
}
