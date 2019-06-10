package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.JSONResult;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@RequestMapping("/queryuser")
	public JSONResult queryuser(String user_id,double user_longitude,double user_latitude,String location) {
		/*String sql = "select count(*) from users where user_id="+"3";
		 // 判断是否存在记录数
		   int count = jdbcTemplate.queryForObject(sql, Integer.class);
		  if(count==0) {
			    sql = "insert into users value(?,?)";			 
			    jdbcTemplate.update(sql,user_id,user_longitude,user_latitude,location);			    
		  }
		  else {
			  sql = "update users set user_location=? where user_id=?";
			  jdbcTemplate.update(sql,user_longitude,user_latitude,location,user_id);			  
		      }*/
		  String store_sql =  "select count(*) from store where store_id="+user_id;
		  if(jdbcTemplate.queryForObject(store_sql, Integer.class)!=0) {
			  return JSONResult.ok("true");
		  }
		  else {
			return JSONResult.ok("false");
		}
		
	       
	}
	

}
