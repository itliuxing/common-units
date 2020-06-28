package com.common.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @Class 	FileHelper.java
 * @Author 	作者姓名:刘兴 
 * @Version	1.0
 * @Date	创建时间：2017-9-7 下午3:46:15
 * @Copyright Copyright by 智多星
 * @Direction 类说明
 */

public class FileHelper {

	public static final Logger logger = LoggerFactory.getLogger( FileHelper.class ) ;
	public static final String FILE_PATH = "/ftpDir/wisecost/价格信息/2014版" ;
	
	/***
	 * 
	 * @param sourceFile		源文件夹
	 * @param targetFile		目标文件夹
	 * @throws IOException
	 */
	public static void copyFile( String sourceFile, String targetFile) throws IOException{
		List<File> list = Arrays.asList(new File( sourceFile ).listFiles());
        File file2 = new File( targetFile );
        file2.mkdirs();
        /*
         	请使用:JDK 1.8
        list.forEach((x) -> {
            File fs = x;
            try {
                Files.copy(Paths.get(fs.getAbsolutePath()), Paths.get(file2.getAbsolutePath() + "\\" + fs.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
        //请使用:JDK 1.7 nio特性操作
        for (int i = 0; i < list.size(); i++) {
            File fs = list.get(i);            
            Files.copy(Paths.get(fs.getAbsolutePath()), Paths.get(file2.getAbsolutePath() + "\\" + fs.getName()), StandardCopyOption.REPLACE_EXISTING);
            if( fs.isDirectory() ){
            	copyFile( new StringBuffer( sourceFile ).append("\\").append( fs.getName() ).toString(), 
            			new StringBuffer( targetFile ).append("\\").append( fs.getName() ).toString() ) ;
            }
        }
	}

    /***
     * 寻找文件夹内版本最大的那个文件名
     * @param baseDir
     * @return
     */
    public static int findMaxVersion( File baseDir){
    	String[] children = baseDir.list();
    	List<Integer> dirFile = new ArrayList<Integer>();
        //递归删除目录中的子目录下
        for (int i=0; i<children.length; i++) {
        	File file = new File( baseDir, children[i] ) ;
        	if ( !file.isDirectory() ) {
        		try {
					dirFile.add( Integer.parseInt( getFileNameNoEx(file.getName() ) ) ) ;
				} catch (NumberFormatException e) {}
            }
        }
        if( dirFile.size() < 1 ){
        	return 0 ;
        }else{
        	Collections.sort( dirFile ) ;
        	return dirFile.get( dirFile.size() -1 );
        }
    }

    /***
     * 寻找文件夹内版本最大的那个文件名
     * @param baseDir
     * @return
     */
    public static File findMaxVersionFile( File baseDir){
    	String[] children = baseDir.list();
    	List<Integer> dirFile = new ArrayList<Integer>();
        //递归删除目录中的子目录下
        for (int i=0; i<children.length; i++) {
        	File file = new File( baseDir, children[i] ) ;
        	if ( !file.isDirectory() ) {
        		try {
					dirFile.add( Integer.parseInt( getFileNameNoEx(file.getName() ) ) ) ;
				} catch (NumberFormatException e) {}
            }
        }
        if( dirFile.size() < 1 ){
        	return null ;
        }else{
        	Collections.sort( dirFile ) ;
        	return new File( baseDir, dirFile.get( dirFile.size() -1 ) + ".html" ) ;
        }
    }

	/***
	 * 寻找文件夹内版本最大的那个文件名
	 * @param baseDir
	 * @return
	 */
	public static List<String> findAllFile( File baseDir ,Map<String ,List<String>> dirFile ){
		String[] children = baseDir.list();
		List<String> fileNames = new ArrayList<>() ;
		//递归删除目录中的子目录下
		for (int i=0; i<children.length; i++) {
			File file = new File( baseDir, children[i] ) ;
			logger.info( "找到的文件名称--->>>" + file.getName() );
			String[] namePix = file.getName().split("\\u002E");
			if( namePix.length > 1 && "htm".equals( namePix[1] ) ) {
				continue;
			}
			fileNames.add( file.getName() ) ;
			if ( file.isDirectory() ) {
				try {
					dirFile.put( file.getName() , findAllFile( file.getAbsoluteFile() ,null ) ) ;
				} catch ( Exception e) {}
			}
		}
		return fileNames ;
	}

	public static void main(String[] args) {
		Map<String ,List<String>> dirFile = new HashMap<>();
		findAllFile( new File( FileHelper.FILE_PATH ) , dirFile ) ;
		System.out.println( "" );
	}

    /***
     * Java文件操作 获取文件扩展名
     * @param filename
     * @return
     */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/***
	 * Java文件操作 获取不带扩展名的文件名
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

}
