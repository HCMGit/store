package com.example.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.JSONResult;

@RequestMapping("/goods")
@RestController
public class GoodsController {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	////通过关键字查询商品
	@RequestMapping("/search_goods")
    public JSONResult search_goods(List<Map<String, Object>> storenames,String key) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		String sql;
		int count;
		for (int i = 0; i < storenames.size(); i++) {
			sql="select count(*) from "+storenames.get(i).get("store_id")+"_"+storenames.get(i).get("store_name")+"where item_name like '%"+key+"%'";
			 count = jdbcTemplate.queryForObject(sql, Integer.class);
			 if(count!=0) {
				 list.add(storenames.get(i));
			 }
		}
		return JSONResult.ok(list);
	}
	////添加商品信息
	@RequestMapping("/add_goods_detail")
	public JSONResult add_goods_detail(String store_id_name,String classify,String item_name,double item_price,String item_picture_address) {
		  String sql = "insert into "+store_id_name+" value(?,?,?,?)";			 
		  int check=jdbcTemplate.update(sql,classify,item_name,item_price,item_picture_address);
          if(check<0) {
        	  return JSONResult.errorMsg("添加商品信息失败");
          }
		return JSONResult.ok("添加商品信息成功");
	}
}
