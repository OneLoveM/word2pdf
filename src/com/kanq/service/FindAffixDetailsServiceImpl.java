package com.kanq.service;


import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kanq.dao.FindAffixDetailsDao;
import com.kanq.entity.App_assetfilesex;
import com.kanq.util.Pdf2Jpg;
import com.kanq.util.Word2pdf;
import com.kanq.util.httpDown;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Service
public class FindAffixDetailsServiceImpl implements FindAffixDetailsService {
   
	@Autowired
	private FindAffixDetailsDao findAffixDetailsDao;
    
	
	@Autowired
	private Word2pdf word2pdf;
	@Autowired
	private Pdf2Jpg pdf2Jpg;
	@Value("${oaHttp}")
	private String oaHttpUrl;
	@Value("${ftpmulu}")
	private String ftpmulu;
	@Value("${zhmhHttp}")
	private String zhmhHttp;
	
	
	@SuppressWarnings("static-access")
	@Override
	public  JSONObject queryAffixByIdToJpg(String id) throws Exception  {
		final App_assetfilesex  list =  findAffixDetailsDao.queryAffixById(id);
		JSONObject object = new JSONObject();
		if(list!=null){
			File filePdf=new File(ftpmulu+list.getPath()+File.separator+list.getFileName());
			     System.out.println(!filePdf.exists());
			     //if(!filePdf.exists()){
				System.out.println(ftpmulu+list.getPath());
				 File file = new File(ftpmulu+list.getPath()+File.separator);
				if (!file.exists()) {
				    file.mkdirs();
				     }
				boolean flag= httpDown.downLoadFromUrl(oaHttpUrl+list.getPath()+"/"+list.getFileName(), list.getFileName(), ftpmulu+list.getPath());
			    if(flag){
			    	boolean  result=word2pdf.wordToPDF(ftpmulu+list.getPath()+File.separator+list.getFileName(), ftpmulu+list.getPath()+File.separator+list.getFileName().substring(0,list.getFileName().lastIndexOf("."))+".pdf");
			    	 if(result){
			    		 JSONObject jsonObject= new  JSONObject(); 
			    		 jsonObject.put("fileName",list.getFileName().substring(0,list.getFileName().lastIndexOf("."))+".pdf");
			    		 jsonObject.put("path", list.getPath()+"/"+list.getFileName().substring(0,list.getFileName().lastIndexOf("."))+".pdf");
			    		 JSONArray array= pdf2Jpg.setup(ftpmulu+list.getPath()+"/"+list.getFileName().substring(0,list.getFileName().lastIndexOf("."))+".pdf", ftpmulu+list.getPath()+"/"+list.getFileName().substring(0,list.getFileName().lastIndexOf(".")),list.getPath()+"/"+list.getFileName().substring(0,list.getFileName().lastIndexOf(".")));  
			    		 jsonObject.put("images", array);
			    		 object.put("code", "success");
						 object.put("data", jsonObject);
			    		
			    		
			    	 }else{
			    		 JSONObject jsonObject= new  JSONObject(); 
			    		 jsonObject.put("msg", "PDF文件转换失败");
			    		 jsonObject.put("fileName",list.getFileName());
			    		 object.put("code", "success");
						 object.put("data", jsonObject);
			    		 }
			    }else{
			    	object.put("code", "faild");
					object.put("msg", "文件下载失败");
					object.put("data", "");
			          }
//			     }else{
//			    	 JSONObject jsonObject= new  JSONObject(); 
//		    		 jsonObject.put("fileName",list.getFileName().substring(0,list.getFileName().lastIndexOf("."))+".pdf");
//		    		 jsonObject.put("path", list.getPath()+"/"+list.getFileName().substring(0,list.getFileName().lastIndexOf("."))+".pdf");
//			    	 object.put("code", "success");
//					 object.put("data", jsonObject);
//			       }
		      }else{
				object.put("code", "faild");
				object.put("msg", "没有word附件");
				object.put("data", "");
		      }
		
		   return object;
	}
	
	
	
	
	

}
