package com.kanq.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kanq.entity.App_assetfilesex;
import com.kanq.service.FindAffixDetailsService;
import com.kanq.util.FtpUtils;
import com.kanq.util.Word2pdf;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/Converter")
public class Word2PdfService {
	
	
	
	
	@Autowired
	private FindAffixDetailsService findAffixDetailsService;
	
	
	@RequestMapping("word2Pdf")
	@ResponseBody
	public void   word2pdf(@RequestParam(defaultValue="",value="assetId") String id,HttpServletResponse response) throws Exception{
	      JSONObject o=  findAffixDetailsService.queryAffixByIdToJpg(id);
	      response.setCharacterEncoding("UTF-8");
	      response.setContentType("text/json;charset=UTF-8");
	      response.setHeader("Cache-control", "no-cache");
	      PrintWriter out = response.getWriter();
	      out.print(o.toString());
	}
	

	
}
