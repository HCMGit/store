package com.example.Controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.JSONResult;
import com.example.pojo.StringToArray;

@RequestMapping("/goods")
@RestController
public class GoodsController {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	////通过关键字查询商品
	@RequestMapping("/search_goods")
    public JSONResult search_goods(String storenames,String key) {
		List<Map<String, Object>> list=StringToArray.StringToArray(storenames);
		List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		String sql;
		System.out.println(list.get(1).get("store_id"));
		int count;
		for (int i = 0; i < list.size(); i++) {
			sql="select count(*) from "+list.get(i).get("store_id")+"_"+list.get(i).get("store_name")+" where item_name LIKE '%"+key+"%'";
		 count = jdbcTemplate.queryForObject(sql, Integer.class);
			 if(count!=0) {
				 list1.add(list.get(i));
			 }
		}
		return JSONResult.ok(list1);
	}
	//添加分类
	@RequestMapping("/add_classify")
	public JSONResult add_classify(String store_id, String store_name,String classify,String item_name,double item_price,String item_picture_address) {
		String sql="select count(*) from "+store_id+"_"+store_name+" where classify='"+classify+"'";
		if (jdbcTemplate.queryForObject(sql,Integer.class) != 0) {
			return JSONResult.ok("该分类以存在");
		}
		sql = "insert into "+store_id+"_"+store_name+" value(?,?,?,?,?)";	
		 long id=System.currentTimeMillis();
		  int check=jdbcTemplate.update(sql,classify,item_name,item_price,item_picture_address,id);
          if(check<0) {
        	  return JSONResult.errorMsg("添加商品信息失败");
          }
		return JSONResult.ok("添加商品信息成功");
	}
	//////添加商品信息
	@RequestMapping("/add_item")
	public JSONResult add_goods_detail(String store_id, String store_name,String classify,String item_name,double item_price,String item_picture_address) {
		  String sql = "insert into "+store_id+"_"+store_name+" value(?,?,?,?)";
		  long id=System.currentTimeMillis();
		  int check=jdbcTemplate.update(sql,classify,item_name,item_price,item_picture_address,id);
          if(check<0) {
        	  return JSONResult.errorMsg("添加商品信息失败");
          }
		return JSONResult.ok("添加商品信息成功");
	}
	///查询商品信息
	@RequestMapping("query_goods_detail")
    public JSONResult query_store_detail(String store_id,String store_name) {
		String sql="select * from "+store_id+"_"+store_name;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		 list = jdbcTemplate.queryForList(sql);
		 if(list!=null) {
	    	return JSONResult.ok(list);}
		 else {
			return JSONResult.ok("请先注册商店");
		}
	}
    //删除大分类
	@RequestMapping("delete_classify")
	public JSONResult delete_classify(String store_id,String store_name,String classify) {
		String sql="delete from "+store_id+"_"+store_name+" where classify="+classify;
		jdbcTemplate.update(sql);
		return JSONResult.ok("删除成功");
	}
	//删除小分类
	@RequestMapping("delete_item")
	public JSONResult delete_item(String store_id,String store_name,String item)
	{
		String sql="delete from "+store_id+"_"+store_name+" where item='"+item+"'";
		jdbcTemplate.update(sql);
		return JSONResult.ok("删除成功");
	}
	//修改大分类信息
	@RequestMapping("update_classify")
	public JSONResult update_classify(String store_id,String store_name,String classify,String old_classify)
	{
		String sql="update "+store_id+"_"+store_name+" set classify= '"+classify+"' where classify= '"+old_classify+"'";
		jdbcTemplate.update(sql);
		return JSONResult.ok("更新成功");
	}
	//修改小分类信息
	@RequestMapping("update_item")
	public JSONResult update_item(String store_id,String store_name,String classify,String item_name,double item_price,String item_picture_address,int id) {
		
		String sql="update "+store_id+"_"+store_name+" set classify= '"+classify+"',item_name='"+item_name+"',item_price='"+item_name+"',item_picture_address='"+item_picture_address+"' where id="+id ;         
		jdbcTemplate.update(sql);
		return JSONResult.ok("更新成功");
	}
	
}
