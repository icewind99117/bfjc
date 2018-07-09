package com.lsf.imf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lsf.imf.entity.bo.ApiTag;
import com.lsf.imf.entity.bo.MsgStore;
import com.lsf.imf.entity.vo.UserInfo;
import com.lsf.imf.mq.Msg;

@Mapper
public interface ApiTagMapper {

	@Select("select id,name,code,params,description,removed from api_tag where removed=0 order by id")
	@ResultType(value=java.util.List.class)
	@ResultMap(value="apiTagMap")
	public List<ApiTag> findAllApiTag();			
	

	@Select("select id,name,code,params,description,removed from api_tag where id=#{id} and removed=0")
	@ResultMap(value="apiTagMap")
	public ApiTag getApiTagById(@Param("id") Integer id);
	
	@Select("select id,name,code,params,description,removed from api_tag where code=#{code} and removed=0")
	@ResultMap(value="apiTagMap")
	public ApiTag getApiTagByCode(@Param("code") String code);
	
	
	@Update("update api_tag set params=#{params} where code=#{code}")
	public Integer updateApiTag(@Param("code") String code,@Param("params") String params);
	
	@Update("update api_tag set params=#{params} where code='timestamp'")
	public Integer updateTimestamp(@Param("params") String timestamp);
	
		
		
}