package com.hanniu.watermark;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Component;

//@Component
public class WatermarkPdf {

    public static void main(String... args) throws Exception {
         /*System.out.println("---sart convert ---");
         new WatermarkPdf().watermarkPDF("/Users/zhangxiao/eclipse-workspace/file-preview-server/金融产品配置化项目立项建议书.pdf",
        		"Agilean Copyright, 2019-2030",
        		120f,true);
         System.out.println("---end convert ---");
         */
    }
    

    /*
     * PDFBox Technics to get the convert job done
     * 
     * @ srcPdfPath: 源PDF绝对路径
     * @ watermarkContent: 水印内容
     * @ fontSize: 水印字体大小
     * 
     * @ deleteSrcFile: 是否删除源PDF文件
     * 
     * @ 返回打水印后的PDF文件
     * 
     */
    public  String watermarkPDF (String srcPdfPath,
    		String watermarkContent,
    		float fontSize,
    		boolean deleteSrcFile) throws Exception {
       
        PDDocument doc;
        File srcPdfFile=new File(srcPdfPath);
        File destPDF = new File(srcPdfFile.getParent() + System.getProperty("file.separator") +"Watermark_"+System.currentTimeMillis()+srcPdfFile.getName());
        System.out.println(">>destPDF="+destPDF);
        doc = PDDocument.load(srcPdfFile);
        doc.setAllSecurityToBeRemoved(true);
        for(PDPage page:doc.getPages()){
          try(PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true))
          {
            PDRectangle rec=page.getArtBox();
            PDFont font = PDType1Font.HELVETICA_OBLIQUE;
            PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
            r0.setNonStrokingAlphaConstant(0.2f);
            r0.setAlphaSourceFlag(true);
            cs.setGraphicsStateParameters(r0);
            cs.setNonStrokingColor(200,0,0);//Red
            cs.beginText();
            cs.setFont(font, fontSize);
            cs.setTextMatrix(Matrix.getRotateInstance(20,rec.getWidth()/4-200,rec.getHeight()/5));
            cs.showText(watermarkContent);
            cs.setTextMatrix(Matrix.getRotateInstance(20,rec.getWidth()/2,rec.getHeight()/5));
            cs.showText(watermarkContent);
            cs.endText();
          }catch(Exception e) {
            	e.printStackTrace();
          }
            
        }
        //删除转换的源PDF文件
        if(deleteSrcFile) srcPdfFile.delete();
        doc.save(destPDF);
        return destPDF.getAbsolutePath();
    }
 


    
    
}
