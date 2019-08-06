package com.hanniu.controller;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hanniu.eum.Constants;
import com.hanniu.json.Word2PdfResult;
import com.hanniu.service.PdfConvertService;
import com.hanniu.service.PdfWatermarkService;
import com.hanniu.tools.JacksonTool;
@Controller
public class WaterMarkAPIController {
	protected final static  Logger log = LogManager.getLogger(WaterMarkAPIController.class);
	@Autowired
	private String libreFileInput;
	@Autowired  
	private String libreFileOutput;
	@Autowired
	private PdfConvertService pdfConvertService;
	@Autowired
	private PdfWatermarkService pdfWatermarkService;
    final static String CONTENT_TYPE_PDF="application/pdf";
	private final static String ATTACH_TYPES="doc,docx";
	
	   /** 
	     * 使用Ajax异步上传附件
	     * @param attach 封装word文件对象 
	     * @param request 
	     * @param response 
	     * @throws IOException  
	     * @throws IllegalStateException  
	     */  
	    @RequestMapping(value="/upload/convert",method=RequestMethod.POST)  
	    public void uploadPic(MultipartFile attach, @RequestParam(required=false) String watermarkContent,@RequestParam(required=false) float fontSize,HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {  
	    	log.info(">>>>uploadPic goes<<<<<<<");
	    	Word2PdfResult res=new Word2PdfResult();
	    	res.setResCode("0");
	    	res.setResMsg("success");
            response.setContentType("application/json; charset=utf-8");  
	        try {  
	            //第一步：完成word转pdf 
	            String originalFilename = attach.getOriginalFilename();  
	            System.out.println(originalFilename);  
	            String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());  
	            String extension = FilenameUtils.getExtension(originalFilename);  
	            if(ATTACH_TYPES.indexOf(extension) == -1) {
	            	res.setPath("");
	            	res.setResCode("1009");
	            	res.setResMsg("不支持"+extension+"扩展名的文件转码");
	            	response.getWriter().write(JacksonTool.fromObjectToJson(res)); 
	            	return;
	            }	
	            String destName =  name + "." + extension;  
	            // absolute path  
	            String destPath = libreFileInput +"/"+ destName;  
	            log.info(">>>destName="+destName);
	            
	            File destFile = new File(destPath);  
	            if(!new File(libreFileInput).exists()) {
	            	new File(libreFileInput).mkdirs();
	            }
	            if(!new File(libreFileOutput).exists()) {
	            	new File(libreFileOutput).mkdirs();
	            }
	            if(!destFile.exists()) destFile.createNewFile();
	            log.info(">>>starting upload <<<<");
	            // uploading  
	            attach.transferTo(new File(destPath));  
	            log.info(">>>end upload <<<<");  
	            res.setPath(destPath);
	            log.info(">>转换的目标PDF:"+libreFileOutput+File.separator+ name + ".pdf");
	            //开始转换
	            log.info(">>>开始转换<<<<");
	            if(pdfConvertService.convert(destPath).equals(Constants.SUCCESS)) {
	            	 log.info(">>>完成转换<<<<");
	            	 //完成转换删除源文件
	            	 //destFile.delete();
	            	 String srcPdfPath=libreFileOutput+File.separator+ name + ".pdf";
	            	 //第二步：打水印
	            	 String destPdfPath=pdfWatermarkService.watermarkPDF(srcPdfPath, watermarkContent, fontSize, false);
	            	 res.setPath(destPdfPath);
	            }else {
	            	log.info(">>>转换失败<<<<");
	            	res.setPath("");
	            	res.setResCode("1009");
	            	res.setResMsg("转换失败");
	            	response.getWriter().write(JacksonTool.fromObjectToJson(res)); 
	            	return;
	            }
            	response.getWriter().write(JacksonTool.fromObjectToJson(res)); 
            	return;
	  
	        } catch (Exception e) {  
	        	e.printStackTrace();
	        	res.setPath("");
            	res.setResCode("1009");
            	res.setResMsg("fail");
            	response.getWriter().write(JacksonTool.fromObjectToJson(res)); 
            	return;
	        }  
	    }  
	      
}  
	 
	 
	
	
