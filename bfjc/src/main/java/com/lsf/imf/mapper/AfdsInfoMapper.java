package com.lsf.imf.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lsf.imf.entity.bo.AfdsInfo;
import com.lsf.imf.entity.bo.ApiTag;
import com.lsf.imf.entity.bo.MsgStore;
import com.lsf.imf.entity.vo.UserInfo;
import com.lsf.imf.mq.Msg;

@Mapper
public interface AfdsInfoMapper {

	@Select("select * from afds_info where removed=0 and flight_identity=#{flight_identity} and flight_direction=#{flight_direction} and scheduled_date=#{scheduled_date} and flight_repeat_count=#{flight_repeat_count} ")
	@ResultMap(value="afdsInfoMap")
	@ResultType(value=java.lang.Integer.class)
	public AfdsInfo getAfdsInfo(Map<String,Object> map);		

	public Integer insertAfdsInfo(Map<String,Object> map);
		
	public Integer updateAfdsInfo(Map<String,Object> map);
	
	
	@Update("update afds_info set removed=1 where id=#{id}")
	public Integer removeAfdsInfo(@Param("id") int id);
	
	@Select("select * from afds_info where removed=0 and scheduled_date=#{scheduledDate}")
	@ResultType(value=java.util.HashMap.class)
	public List<HashMap<String,String>> findAllAfdsInfoByScheduledDate(@Param("scheduledDate") String scheduledDate);		
		
}