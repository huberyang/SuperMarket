package com.ncs.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ncs.common.pojo.PicResult;
import com.ncs.common.utils.FastDFSClient;
import com.ncs.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	
	/**
	 * 这里使用Spring注解，该参数来源于Spring扫描了所有的properties文件，获取了其中的key/value内容，所以此处可以直接采用注解读取指定数据
	 */
	@Value("${file_server_base_url}")
	private String file_server_base_url;

	@Override
	public PicResult fileUpload(MultipartFile uploadFile){
		
		PicResult result=new PicResult();
		//先判断文件是否为空
		if(uploadFile==null) {
			result.setError(1);
			result.setMessage("文件为空");
			return result;
		}
		try {
		//上传到文件服务器                                                                                
	    //因为这里有两种情况，源码会从文件路径，或者从类路径（所以我们只需要书写类路径下的url，该文件便可以被读取）
		FastDFSClient client=new FastDFSClient("fileUpload/client.conf");
        //获取文件扩展名，上传文件
		String url = client.uploadFile(uploadFile.getBytes(), uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1));
		//返回结果给客户端
		result.setError(0);
		//拼接完成的图片服务器地址
		/*Properties properties = new Properties();
		InputStream ins = this.getClass().getClassLoader().getResourceAsStream("properties/fileserver.properties");
		properties.load(ins);*/
		result.setUrl(file_server_base_url+url);
		return result;
		}catch(Exception e){
			result.setError(1);
			result.setMessage("文件上传失败");
		}
		
		return result;
	}

}
