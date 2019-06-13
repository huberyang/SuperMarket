package com.ncs.service;

import org.springframework.web.multipart.MultipartFile;

import com.ncs.common.utils.pojo.PicResult;

public interface FileService {
	
	public PicResult fileUpload(MultipartFile uploadFile) throws Exception;

}
