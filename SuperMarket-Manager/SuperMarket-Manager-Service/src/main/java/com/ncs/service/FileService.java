package com.ncs.service;

import org.springframework.web.multipart.MultipartFile;

import com.ncs.common.pojo.PicResult;

public interface FileService {
	
	public PicResult fileUpload(MultipartFile uploadFile) throws Exception;

}
