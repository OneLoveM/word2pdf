package com.kanq.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.kanq.entity.App_assetfilesex;

/**
 * @date 2017/1/20 
 * @author xh
 * 
 *
 */
@Repository
public class FindAffixDetailsDaoImpl implements FindAffixDetailsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	/**
	 * 查询办公协同附件为word的文件
	 */
	public App_assetfilesex queryAffixById(String id) {
//		String sql = "SELECT assetId, fileType, fileName, path  FROM app_assetfilesex   WHERE   filetype in (2,9)  and  assetid = ?"; 
//		List<App_assetfilesex> list = null;
//		List<App_assetfilesex> list1 = null;
//		//RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
//		try {  
//	        RowMapper<App_assetfilesex> rowMapper = new BeanPropertyRowMapper<App_assetfilesex>(App_assetfilesex.class);  
//	         list = jdbcTemplate.query(sql, rowMapper,id); 
//	         if(list.size()>0){
//	        	 for(App_assetfilesex app:list){
//	        		  if("9".equals(app.getFileType())){
//	        			  list1.add(app);
//	        		  }
//	        	 }
//	         }
//			System.out.println(list);
//		} catch (EmptyResultDataAccessException e) {  
//		    return null;  
//		}  
//		return list; 
		
		
		String sql = "SELECT assetId, fileType, fileName, path  FROM app_assetfilesex   WHERE   filetype='9'  and  assetid = ?"; 
		String sql1 = "SELECT assetId, fileType, fileName, path  FROM app_assetfilesex   WHERE   filetype='2'  and  assetid = ?"; 
		App_assetfilesex app=null;
		try{
			RowMapper<App_assetfilesex> rm = ParameterizedBeanPropertyRowMapper.newInstance(App_assetfilesex.class);
			app=(App_assetfilesex)jdbcTemplate.queryForObject(sql, rm,id);
			return app;
		 }catch (EmptyResultDataAccessException e) {  
			 try{
			    RowMapper<App_assetfilesex> rm1 = ParameterizedBeanPropertyRowMapper.newInstance(App_assetfilesex.class);
				app=(App_assetfilesex)jdbcTemplate.queryForObject(sql1, rm1,id);
				return app;
				}catch(EmptyResultDataAccessException e1){
					return null;
				}
		  }  
	}
	
	
	

}
