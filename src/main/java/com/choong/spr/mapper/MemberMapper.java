package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.MemberDto;

public interface MemberMapper {

	int insertMember(MemberDto member);

	int countMemberId(String id);

	int countMemberEmail(String email);


	List<MemberDto> listMember();

	MemberDto selectMemberById(String id);

	int deleteMemberById(String id);

	int getcountMemberEmail(String email);

	int getcountMemberNickname(String nickname);

	int updateMember(MemberDto dto);

	int insertAuth(@Param("id")String id, @Param("auth")String auth);

	int deleteAuthById(String id);

	int countMemberNickname(String nickname);

	void updatePasswordById(@Param("id")String id, @Param("pw")String pw);


}
