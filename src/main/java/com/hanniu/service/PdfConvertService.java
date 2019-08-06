package com.hanniu.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hanniu.controller.WaterMarkAPIController;
import com.hanniu.eum.Constants;

@Service("pdfConvertService")
public class PdfConvertService {
	protected final static  Logger log = LogManager.getLogger(WaterMarkAPIController.class);
	
	@Autowired
	private String libreFileInput;
	@Autowired  
	private String libreFileOutput;
	@Autowired 
	private String libreOfficeToolInstall;
	
	@Autowired 
	private String libreOfficeScript;
	   
	@Autowired
	private Integer libreofficeTransTimeout;
	/*
	 *  @仅限于将近WORD转成PDF
	 *  @srcPath: 需要转换的word文件
	 *  @返回转换后文件的完整路径
	 */
	public String convert(final String srcFilePath) throws Exception {
		
		final String cmmand=libreOfficeToolInstall+"/soffice --headless --convert-to  pdf "+srcFilePath+" --outdir "+libreFileOutput;
		log.info(">>> convert cmmand:"+cmmand);
		//CommandLine cmdLine = new CommandLine(libreOfficeToolInstall+"/soffice --headless --convert-to  pdf /tmp/答辩知识点汇总V0.3.docx --outdir /tmp/");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  
		  
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();  

        CommandLine commandline = CommandLine.parse(cmmand);  

        DefaultExecutor exec = new DefaultExecutor();  

        exec.setExitValues(null);  

        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);  

        exec.setStreamHandler(streamHandler);  

        exec.execute(commandline); 
        if(outputStream != null ) {
        	 log.info(">>> Success message="+new String(outputStream.toByteArray(), "utf-8"));
             return Constants.SUCCESS;
        }
        if(errorStream != null ) {
       	   log.error(">>> Error message="+new String(errorStream.toByteArray(), "utf-8"));
       	 return Constants.FAIL;
        }
        //no stdout
        return Constants.SUCCESS;
       
        
	}

}
