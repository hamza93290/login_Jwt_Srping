package com.example.demo.Dto;

import org.springframework.stereotype.Component;

import com.example.demo.entity.UserInfo;

@Component
public class UserInfoMapper {

	public UserInfoDto toDto(UserInfo userInfo) {

		return new UserInfoDto(userInfo.getId(), userInfo.getName(), userInfo.getEmail(), 

				userInfo.getRoles());

	}

 

	public UserInfo toUser(UserInfoDto userInfoDto) {

		return new UserInfo(userInfoDto.getId(), userInfoDto.getName(), userInfoDto.getEmail(),

			     userInfoDto.getRoles());

	}
}
