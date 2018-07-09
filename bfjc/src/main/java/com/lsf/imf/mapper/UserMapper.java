package com.lsf.imf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.lsf.imf.entity.vo.UserInfo;

@Mapper
public interface UserMapper {

	@Select("select id,login_name,user_name,password from user_info where login_name=#{loginName} ")
	@ResultMap(value="userInfoMap")
	public List<UserInfo> findUserInfoByLoginName(@Param("loginName") String loginName);
	

	
		
		
}