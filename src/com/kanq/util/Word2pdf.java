package com.kanq.util;
import java.io.File;  

import org.apache.poi.POIXMLDocument;  
import org.apache.poi.xwpf.usermodel.XWPFDocument;  
import org.dom4j.Document;  
import org.dom4j.DocumentException;  
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import com.jacob.activeX.ActiveXComponent;  
import com.jacob.com.ComThread;  
import com.jacob.com.Dispatch;  
import com.jacob.com.Variant; 

@Component
public class Word2pdf {    
    static final int wdFormatPDF = 17;// PDF 格式      
    public boolean wordToPDF(String sfileName,String toFileName) throws Exception{      
              
        System.out.println("启动Word...");        
        long start = System.currentTimeMillis();        
        ActiveXComponent app = null;    
        Dispatch doc = null;    
        boolean flag=false;
        try {        
            app = new ActiveXComponent("Word.Application");   
            // 设置word不可见  
            app.setProperty("Visible", new Variant(false));    
            // 打开word文件  
            Dispatch docs = app.getProperty("Documents").toDispatch();     
//          doc = Dispatch.call(docs,  "Open" , sourceFile).toDispatch();     
            doc = Dispatch.invoke(docs,"Open",Dispatch.Method,new Object[] {                      
               sfileName, new Variant(false),new Variant(true) }, new int[1]).toDispatch();  
            System.out.println("打开文档..." + sfileName);    
            System.out.println("转换文档到PDF..." + toFileName);        
            File tofile = new File(toFileName);      
           // System.err.println(getDocPageSize(new File(sfileName)));  
            if (tofile.exists()) {        
                tofile.delete();   
            }          
//          Dispatch.call(doc, "SaveAs",  destFile,  17);     
         // 作为html格式保存到临时文件：：参数 new Variant(8)其中8表示word转html;7表示word转txt;44表示Excel转html;17表示word转成pdf。。  
            Dispatch.put(doc, "ShowRevisions", false);
           Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {                  
                toFileName, new Variant(17) }, new int[1]);      
            long end = System.currentTimeMillis();        
            System.out.println("转换完成..用时：" + (end - start) + "ms.");    
            flag=true;
        } catch (Exception e) {    
            e.printStackTrace();    
            
            System.out.println("========Error:文档转换失败：" + e.getMessage());        
        }catch(Throwable t){  
            t.printStackTrace();  
        } finally {    
            // 关闭word  
            Dispatch.call(doc,"Close",false);    
            System.out.println("关闭文档");    
            if (app != null)        
                app.invoke("Quit", new Variant[] {});        
            }    
          //如果没有这句话,winword.exe进程将不会关闭    
           ComThread.Release();    
           return flag;  
           }    
    @SuppressWarnings("unused")
	private static Document read(File xmlFile) throws DocumentException {  
        SAXReader saxReader = new SAXReader();  
        return saxReader.read(xmlFile);  
    }  
//    public String getDocPageSize(File file){  
//        String pages = null;  
//        try{  
//            Document doc = read(file);  
//            List<Node> nodes = doc.selectNodes("//o:Pages");  
//            if(nodes != null && nodes.size() > 0){  
//                pages = nodes.get(0).getText();  
//                System.out.println("/////////////////");  
//                System.out.println("该word文档的页数为："+Integer.parseInt(pages));  
//                System.out.println("/////////////////");  
//            }else{  
//                System.out.println("*********");  
//                System.out.println("页面转换错误");  
//                System.out.println("*********");  
//            }  
//        }catch(Exception ex){  
//            ex.printStackTrace();   
//        }  
//        return pages;  
//    }  
    public  int getDocPageSize(String filePath)  throws Exception {  
        XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(filePath));  
        int pages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();//总页数  
        int wordCount = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getCharacters();// 忽略空格的总字符数 另外还有getCharactersWithSpaces()方法获取带空格的总字数。          
        System.out.println ("pages=" + pages + " wordCount=" + wordCount);  
        return pages;  
    }  
       
    public static void main(String[] args) throws Exception {    
        Word2pdf d = new Word2pdf();    
       //System.err.println(d.getDocPageSize("d:\\局域网组建.doc"));  
        d.wordToPDF("F:\\谢昊java.doc", "F:\\1111.pdf");    
    }    
    
} 