package com.hanniu.service;

public interface ICloudSorageService {
   
	/*
	 * @ filePath: 上传文件的绝对路径
	 * @ 返回： 返回成功上传文件的URL位置
	 */
	public String upload(String filePath);
	
	
}
