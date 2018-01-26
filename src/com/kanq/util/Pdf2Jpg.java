package com.kanq.util;

import java.awt.Image;  
import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
  
import java.io.File;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;  
import javax.swing.SwingUtilities;

import org.springframework.stereotype.Component;

import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
import com.sun.pdfview.PDFFile;  
import com.sun.pdfview.PDFPage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.nio.ch.FileChannelImpl;  

@Component
public class Pdf2Jpg {  
    public static JSONArray setup(String pdfPath,String imgPath,String path) throws IOException {  
        JSONArray array= new JSONArray();
        RandomAccessFile raf=null;
        FileChannel channel =null;
        try{
        File file = new File(  
                pdfPath);  
         raf = new RandomAccessFile(file, "r");  
         channel = raf.getChannel();  
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel  
                .size());  
        PDFFile pdffile = new PDFFile(buf);
        System.out.println("页数： " + pdffile.getNumPages());  
        for (int i = 1; i <= pdffile.getNumPages(); i++) {  
        	JSONObject object = new JSONObject();
            // draw the first page to an image  
            PDFPage page = pdffile.getPage(i);  
  
            // get the width and height for the doc at the default zoom  
            Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()  
                    .getWidth(), (int) page.getBBox().getHeight());  
            // generate the image  
            Image img = page.getImage(rect.width, rect.height, // width &  
                    rect, // clip rect  
                    null, // null for the ImageObserver  
                    true, // fill background with white  
                    true // block until drawing is done  
                    );  
          
            BufferedImage tag = new BufferedImage(rect.width, rect.height,  
                    BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height,  
                    null);  
            FileOutputStream out = new FileOutputStream(  
                    imgPath  
                            +"_"+ i + ".jpg"); // 输出到文件流  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            encoder.encode(tag); // JPEG编码  
            object.put("filenName", new File(imgPath+"_"+ i + ".jpg").getName());
            object.put("filePath",  path+"_"+ i + ".jpg");
            array.add(object);
            out.close(); 
          
           }  
        // 加上这几行代码,手动unmap  
        Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);  
        m.setAccessible(true);  
        m.invoke(FileChannelImpl.class, buf);  
        } catch (Exception e) {
		}finally {
			if(channel.isOpen()){
				channel.close();
			}
			if(null!=raf){
				raf.close();
			}
		}
       return array;
       
    }  
  
    public static void main(final String[] args) throws IOException {  
    	  SwingUtilities.invokeLater(new  Runnable() {  
              public   void  run() {  
                  try  {  
                	  JSONArray array= Pdf2Jpg.setup("D:\\11111111111111111111.pdf","D:\\11111111111111111111","");  
                  	System.out.println(array);
                  } catch  (IOException ex) {  
                      ex.printStackTrace();  
                  }  
              }  
          });  
    	
               
   
    }  
  
}