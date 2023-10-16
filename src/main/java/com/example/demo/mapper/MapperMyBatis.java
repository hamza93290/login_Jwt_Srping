package com.example.demo.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.UserInfo;

@Mapper
public interface MapperMyBatis  {

	@Select("SELECT * FROM user_info WHERE name = #{name}")
	Optional<UserInfo> findByName(@Param("name") String name);
}
