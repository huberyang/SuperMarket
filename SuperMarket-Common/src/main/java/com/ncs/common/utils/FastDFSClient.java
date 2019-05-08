package com.ncs.common.utils;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * 
 * @Title:  FastDFSClient.java   
 * @Package com.ncs.common.utils   
 * @Description:    TODO(文件服务器插件客户端)   
 * @author: Stephen Yang   
 * @date:   Apr 17, 2019 11:33:15 PM   
 * @version V1.0 
 * @Copyright: 2019 Inc. All rights reserved. 
 *
 */
public class FastDFSClient {
	
	private TrackerClient trackerClient;
	private TrackerServer trackerServer;
	private StorageServer storageServer;
	private StorageClient1 storageClient1;
	
	public FastDFSClient(String conf) throws IOException, MyException {
		
		/*if(conf.contains("classpath:")) {
			conf=conf.replace("classpath:", this.getClass().getResource("/").getPath());
			System.out.println("conf:"+conf);
		}*/
		
		// 1、把FastDFS提供的jar包添加到工程中
		// 2、初始化全局配置。加载一个配置文件。
		ClientGlobal.init(conf);
		// 3、创建一个TrackerClient对象。
		 trackerClient=new TrackerClient();
		// 4、创建一个TrackerServer对象。
		 trackerServer=trackerClient.getConnection();
		// 5、声明一个StorageServer对象，null。
		 storageServer=null;
		// 6、获得StorageClient对象。
		 storageClient1=new StorageClient1(trackerServer, storageServer);
		
	}
	
	
	/**
	 * file upload method
	 * @param fileName 文件路径
	 * @param fileExtName 文件扩展名
	 * @param metas 文件扩展信息
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public String uploadFile(String fileName,String fileExtName,NameValuePair[] metas) throws IOException, MyException {
		String fileUrl = storageClient1.upload_file1(fileName, fileExtName, metas);
		return fileUrl;
	}
	
	public String uploadFile(String fileName,String fileExtName) throws IOException, MyException {
		String fileUrl = storageClient1.upload_file1(fileName, fileExtName, null);
		return fileUrl;
	}
	
	public String uploadFile(String fileName) throws IOException, MyException {
		String fileUrl = storageClient1.upload_file1(fileName, null, null);
		return fileUrl;
	}
	
	
	/**
	 * fileContent upload method
	 * @param fileContent 文件字节内容
	 * @param fileExtName 文件扩展名
	 * @param metas 文件扩展信息
	 * @return
	 * @throws MyException 
	 * @throws IOException 
	 */
	public String uploadFile(byte[] fileContent,String fileExtName,NameValuePair[] metas) throws IOException, MyException {
		String fileUrl = storageClient1.upload_file1(fileContent, fileExtName, metas);
		return fileUrl;
	}
	
	public String uploadFile(byte[] fileContent,String fileExtName) throws IOException, MyException {
		String fileUrl = storageClient1.upload_file1(fileContent, fileExtName, null);
		return fileUrl;
	}
	
	public String uploadFile(byte[] fileContent) throws IOException, MyException {
		String fileUrl = storageClient1.upload_file1(fileContent, null, null);
		return fileUrl;
	}

}
