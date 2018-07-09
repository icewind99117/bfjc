package com.lsf.imf.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lsf.imf.entity.bo.MsgStore;

@Mapper
public interface MsgStoreMapper {

	@Insert("insert into msg_store(msg_type,data_type,msg_content,sender,msg_time,receive_time,parse_time,"
			+ "afds_id,flight_identity,flight_direction,flight_repeat_count,scheduled_date,seq_num,status,removed) "
			+ "values(#{msgType},#{dataType},#{msgContent},#{sender},#{msgTime},#{receiveTime},#{parseTime},"
			+ "#{afdsId},#{flightIdentity},#{flightDirection},#{flightRepeatCount},#{scheduledDate},#{seqNum},#{status},#{removed})")	
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public Integer saveMsgStore(MsgStore msgStore);
	
	@Update("update msg_store set parse_time=now(),status=#{status},afds_id=#{afdsId} where id=#{id} and removed=0")
	@ResultMap(value="msgStoreMap")
	public Integer updateMsgStoreStatusAndAfdsId(MsgStore msgStore);
//	
	
//	@Select("select * from msg_store where removed=0 order by id")
//	@ResultType(value=java.util.List.class)
//	@ResultMap(value="msgStoreMap")
//	public List<MsgStore> findAllMsgStore();
	
	@Select("select ifnull(max(id),0) from msg_store where removed=0 and receive_time<#{receiveTime}")
	@ResultType(value=java.lang.Integer.class)
//	@ResultMap(value="msgStoreMap")
	public Integer getMaxIdOfMsgStoreBefore(@Param("receiveTime") Timestamp receiveTime);	
	
	@Delete("delete from msg_store where id<=#{id}")	
	public Integer deleteMsgStoreBefore(@Param("id") Integer id);	

	@Select("select * from msg_store where id=#{id} and removed=0")
	@ResultMap(value="msgStoreMap")
	public MsgStore getMsgStoreById(@Param("id") Integer id);
	
	
	public Integer countMsgStore(Map<String,String> paramMap);
	
	public List<MsgStore> pageQueryMsgStore(Map<String,String> paramMap);
		
		
}