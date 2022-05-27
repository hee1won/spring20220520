package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public boolean addMember(MemberDto member) {
		
		// 평문암호를 암호화(encoding)
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		// 암호화된 암호를 다시 세팅 
		member.setPassword(encodedPassword);
		
		// insert member
		int cnt1 = mapper.insertMember(member);
		
		// insert auth
		int cnt2 = mapper.insertAuth(member.getId(), "ROLE_USER");
		
		return cnt1 ==1 && cnt2 ==1;
	}

	public boolean hasMemberId(String id) {
		return mapper.countMemberId(id) > 0;
	}

	public boolean hasMemberemail(String email) {
		return mapper.countMemberEmail(email) > 0;
	}

	public boolean hasMembernickname(String nickname) {
		return mapper.countMemberNickname(nickname) > 0;
	}

	public List<MemberDto> listMember() {
		return mapper.listMember();
	}

	public MemberDto getMemberById(String id) {
		return mapper.selectMemberById(id);
	}

	@Transactional
	public boolean removeMember(MemberDto dto) {
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		String rawPw = dto.getPassword();
		String encodedPw = member.getPassword();
		
		if(passwordEncoder.matches(rawPw, encodedPw)) {
			int cnt1 = mapper.deleteAuthById(dto.getId());
			int cnt2 = mapper.deleteMemberById(dto.getId());
			return cnt2 == 1;
		}
		return false;
	}

	public boolean gethasMemberemail(String email) {
		return mapper.getcountMemberEmail(email) > 0;
	}

	public boolean gethasMembernickname(String nickname) {
		return mapper.getcountMemberNickname(nickname) > 0;
	}

	public boolean modifyMember(MemberDto dto, String oldPassword) {
		// db에서 member 읽어서 
		MemberDto oldMember = mapper.selectMemberById(dto.getId());
		// 기존 password가 일치할때만 계속 진행
		
		String encodedPw = oldMember.getPassword();
		
		if(passwordEncoder.matches(oldPassword, encodedPw)) {
			
			// 암호 인코딩 
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			
			return mapper.updateMember(dto) == 1;
		}
		return false;
	}


}
