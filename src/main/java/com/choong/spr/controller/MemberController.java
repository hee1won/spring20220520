package com.choong.spr.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("signup")
	public void signupForm() {
		
	}
	
	@PostMapping("signup")
	public String signupProcess(MemberDto member, RedirectAttributes rttr) {
		boolean success = service.addMember(member);
		
		if (success) {
			rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
			return "redirect:/board/list";
		} else {
			rttr.addFlashAttribute("message", "회원가입을 실패했습니다.");
			rttr.addFlashAttribute("member", member);
			return "redirect:/member/signup";
		}
	}
	
	@GetMapping(path = "check", params = "id")
	@ResponseBody
	public String idCheck(String id) {
		
		boolean exist = service.hasMemberId(id);
		
		if(exist) {
			return "notok";
		} else {
			return "ok";
		}
	}
	
	@GetMapping(path = "check", params = "email")
	@ResponseBody
	public String emailCheck(String email) {
		
		boolean exist = service.hasMemberemail(email);
		
		if(exist) {
			return "notok";
		} else {
			return "ok";
		}
	}
	
	@GetMapping(path = "check", params = "nickname")
	@ResponseBody
	public String nicknameCheck(String nickname, RedirectAttributes rttr) {
		boolean exist = service.hasMembernickname(nickname);
		if(exist) {
			rttr.addFlashAttribute("message", "사용 가능한 닉네임 입니다.");
			return "notok";
		} else {
			rttr.addFlashAttribute("message", "이미 사용중인 닉네임 입니다.");
			return "ok";
		}
	}
	
	@GetMapping("Memberlist")
	public void list(Model model) {
		List<MemberDto> list = service.listMember();
		model.addAttribute("memberList", list);
	}
	
	// jsp (id, password, email, nickname, inserted) table로 
	// ORDER BY inserted DESC
	
	@GetMapping("get")
	public String getMember(String id, Model model, Principal principal, HttpServletRequest request) {
		
		if(hasAuthOrAdmin(id, principal, request)) {
			MemberDto member = service.getMemberById(id);
			model.addAttribute("member", member);
			return null;
		}
		return "redirect:/member/login";
	}
	
	private boolean hasAuthOrAdmin(String id, Principal principal, HttpServletRequest req) {
		return req.isUserInRole("ROLE_ADMIN") || (principal != null && principal.getName().equals(id));
	}
	
	@PostMapping("remove")
	public String removeMember(MemberDto dto, RedirectAttributes rttr, Principal principal, HttpServletRequest req) {
		if(hasAuthOrAdmin(dto.getId(), principal, req)) {
			
			boolean success = service.removeMember(dto);
			
			if(success) {
				rttr.addFlashAttribute("message", "회원 탈퇴 되었습니다.");
				return "redirect:/board/list";
			} else{
				rttr.addAttribute("id", dto.getId());
				return "redirect:/member/get";
			}
		} else {
			return "redirect:/member/login";
		}
	}
	
	@GetMapping(path = "get", params = "email")
	@ResponseBody
	public String getemailCheck(String email, RedirectAttributes rttr) {
		
		boolean exist = service.gethasMemberemail(email);
		
		if(exist) {
			return "notok";
		} else {
			return "ok";
		}
	}
	
	@GetMapping(path = "get", params = "nickname")
	@ResponseBody
	public String getnicknameCheck(String nickname) {
		boolean exist = service.gethasMembernickname(nickname);
		if(exist) {
			return "notok";
		} else {
			return "ok";
		}
	}
	
	@PostMapping("modify")
	public String modifyMember(MemberDto dto, String oldPassword, RedirectAttributes rttr, Principal principal, HttpServletRequest req) {
		if(hasAuthOrAdmin(dto.getId(), principal, req)) {
			
			boolean success = service.modifyMember(dto, oldPassword);
			
//			System.out.println(dto);
//			System.out.println(oldPassword);
			
			if(success) {
				rttr.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
				return "redirect:/board/list";
			} else {
				rttr.addFlashAttribute("message", "다시 시도해 주세요.");
			}
			rttr.addFlashAttribute("member", dto);	// model
			rttr.addAttribute("id", dto.getId());	// query string
			return "redirect:/member/get";
		} else {
			return "redirect:/member/login";
		}
	}
	
	@GetMapping("login")
	public void loginPage() {
		
	}
	
	@GetMapping("initpw")
	public void initPage() {
		
	}
	
	@PostMapping("initpw")
	public String initpw(String id) {
		service.initPassword(id);
		
		return "redirect:/board/list";
	}
	
	
}
