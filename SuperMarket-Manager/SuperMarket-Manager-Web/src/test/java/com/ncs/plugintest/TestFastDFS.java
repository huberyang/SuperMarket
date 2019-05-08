package com.ncs.plugintest;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.ncs.common.utils.FastDFSClient;

public class TestFastDFS {
	
	@Test
	public void fileupload() throws IOException, MyException {

		// 1、把FastDFS提供的jar包添加到工程中
		// 2、初始化全局配置。加载一个配置文件。
		ClientGlobal.init("D:\\Myself\\Git Workspace\\SuperMarket\\SuperMarket-Manager\\SuperMarket-Manager-Web\\src\\main\\resources\\fileUpload\\client.conf");
		// 3、创建一个TrackerClient对象。
		TrackerClient trackerClient=new TrackerClient();
		// 4、创建一个TrackerServer对象。
		TrackerServer trackerServer=trackerClient.getConnection();
		// 5、声明一个StorageServer对象，null。
		StorageServer storageServer=null;
		// 6、获得StorageClient对象。
		StorageClient storageClient=new StorageClient(trackerServer, storageServer);
		// 7、直接调用StorageClient对象方法上传文件即可。
		String[] upload_file = storageClient.upload_file("D:\\Myself\\Upload Physical Path\\pic\\desk.jpg", "jpg", null);
		for(String arg : upload_file) {
			System.out.println(arg);
		}
	}
	
	
	@Test
	public void uploadFileUseUitl() throws IOException, MyException {
		FastDFSClient fastDFSClient=new FastDFSClient("D:\\Myself\\Git Workspace\\SuperMarket\\SuperMarket-Manager\\SuperMarket-Manager-Web\\src\\main\\resources\\fileUpload\\client.conf");
		String uploadFile = fastDFSClient.uploadFile("C:\\Users\\P1310989\\Desktop\\jboss.txt", "txt");
		
		System.out.print(uploadFile);
	}

}
