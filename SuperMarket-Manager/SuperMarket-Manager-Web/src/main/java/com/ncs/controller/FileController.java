package com.ncs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ncs.common.pojo.PicResult;
import com.ncs.common.utils.JsonUtils;
import com.ncs.service.FileService;

/**
 * 
 * @Title:  FileController.java   
 * @Package com.ncs.controller   
 * @Description:    TODO(文件上传，文件（image，file，media...）)   
 * @author: Hubery Yang   
 * @date:   Apr 17, 2019 2:04:33 PM   
 * @version V1.0 
 * @Copyright: 2019 Inc. All rights reserved. 
 *
 */
@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * fileUpload 
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String fileUpload(@RequestParam MultipartFile uploadFile) throws Exception {
		PicResult result = fileService.fileUpload(uploadFile);
		String json = JsonUtils.objectToJson(result);		
		return json;
	}
	
	
	

}
