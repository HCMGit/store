package com.example.Controller;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.JSONResult;
import com.example.pojo.LocationUtils;
import com.example.pojo.SQLCheck;

@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//////查询商店信息
	@RequestMapping("query_store_detail")
    public JSONResult query_store_detail(String store_id,String store_name) {
		String sql="select * from "+store_id+"_"+store_name;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		 list = jdbcTemplate.queryForList(sql);
	       /* for (Map<String, Object> map : list) {
	            Set<Entry<String, Object>> entries = map.entrySet( );
	                if(entries != null) {
	                    Iterator<Entry<String, Object>> iterator = entries.iterator( );
	                    while(iterator.hasNext( )) {
	                    Entry<String, Object> entry =(Entry<String, Object>) iterator.next( );
	                    Object key = entry.getKey( );
	                    Object value = entry.getValue();
	                    
	                }
	            }
	        }*/
	    	return JSONResult.ok(list);
		
	}
	////添加新的商店信息
	@RequestMapping("/addstore")
	public JSONResult AddStore(String store_id,String store_name,String store_picture_address,String store_location,String store_longitude,String store_latitude) {
		String sql = "select count(*) from store where store_id="+store_id;
		 // 判断是否存在记录数
		   int count = jdbcTemplate.queryForObject(sql, Integer.class);
		   
		   if(count!=0)
		   {
			   return JSONResult.errorException("商家已存在");
		   }
		   else {
			   if(!SQLCheck.isValid(store_name)) {
				   return JSONResult.errorException("商店名字含有特殊字符和特殊字如delete/select/update");
			   }
			   
			  // double longitude = Double.parseDouble(store_longitude);
			  //double latitude = Double.parseDouble(store_latitude);
			   sql = "insert into store value(?,?,?,?,?,?)";			 
			    jdbcTemplate.update(sql,store_id,store_name,store_picture_address,store_location,store_longitude,store_latitude);
			    StringBuffer sb = new StringBuffer("");  
		        sb.append("CREATE TABLE `" +store_id+"_"+store_name+ "` (");  
		        sb.append(" `classify` varchar(255) NOT NULL,");
		        sb.append(" `item_name` varchar(255) NOT NULL,"); 
		        sb.append(" `item_price` double NOT NULL,"); 
		        sb.append(" `item_picture_address` varchar(255) NOT NULL"); 
		        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		        jdbcTemplate.update(sb.toString());
			    return JSONResult.ok("添加商家成功");
		}
	}
	////寻找附近的商店
	@RequestMapping("/nearby_store")
	public JSONResult nearby_store(double distance,String store_longitude,String store_latitude) {
		String sql="select * from store";
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		 list = jdbcTemplate.queryForList(sql);
	        /*for (Map<String, Object> map : list) {
	            Set<Entry<String, Object>> entries = map.entrySet( );
	                if(entries != null) {
	                    Iterator<Entry<String, Object>> iterator = entries.iterator( );
	                    while(iterator.hasNext( )) {
	                    Entry<String, Object> entry =(Entry<String, Object>) iterator.next( );
	                    Object key = entry.getKey( );
	                    Object value = entry.getValue();
	                    
	                }
	            }
	        }*/
		  double longitude = Double.parseDouble(store_longitude);
		  double latitude = Double.parseDouble(store_latitude);
		  List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		 for(int i=0;i<list.size();i++) {
			 if(LocationUtils.getDistance(latitude,longitude, (double)list.get(i).get("store_latitude"),(double)list.get(i).get("store_longitude"))<distance)
			 {
				 
				 list1.add(list.get(i));
			 }
		 }
	    	return JSONResult.ok(list1);    	
	}
	
}
