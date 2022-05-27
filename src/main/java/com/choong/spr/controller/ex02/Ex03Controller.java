package com.choong.spr.controller.ex02;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.choong.spr.domain.ex02.Book;

@RequestMapping("ex03")
//@Controller
//@ResponseBody
@RestController	// @controller, @responsebody를 한번에 주는 어노테이션
public class Ex03Controller {
	
	@RequestMapping("sub01")
	public String method01() {
		return "string data";
	}
	
	@RequestMapping("sub02")
	public Book method02() {
		Book b = new Book();
		b.setTitle("soccer");
		b.setWriter("son");
		
		return b;
	}
	
	@RequestMapping("sub03")
	public String method03() {
		System.out.println("ex03/sub03 일함!!!");
		
		return "hello ajax";
	}
	@RequestMapping("sub04")
	public String method04() {
		System.out.println("ex04/sub04 일함!!!");
		
		return "hi";
	}
	
	@PostMapping("sub06")
	public String method06() {
		System.out.println("ex06/sub06 일함@!@!@");
		
		return "jj";
	}
	
	@DeleteMapping("sub07")
	public String method07() {
		System.out.println("ex06/sub07 일");
		
		return "jj";
	}
	
	@PutMapping("sub08")
	public String method08() {
		System.out.println("ex06/sub06 일함~~~");
		
		return "jj";
	}
	
	@GetMapping("sub09")
	public String method09(String title, String writer) {
		System.out.println("##받은 데이터 ");
		System.out.println("title : " + title);
		System.out.println("writer : " + writer);
		
		return null;
	}
	
	@PostMapping("sub10")
	public String method(String name, String address) {
		System.out.println("데이터---");
		System.out.println("name : " + name);
		System.out.println("address : " + address);
		
		return "data";
	}
	
	@PostMapping("sub11")
	public void method11(Book book) {
		System.out.println(book);
		
	}
	
	@PostMapping("sub12")
	public String method12() {
		System.out.println("12번째 메소드 일함!!");
		
		return "hi";
	}
	
	@GetMapping("sub13")
	public Integer method13() {
		return (int) (Math.random() * 45 + 1);
	}
	
	@GetMapping("sub14")
	public Book method14() {
		Book b = new Book();
		b.setTitle("스프링");
		b.setWriter("홍길동");
		return b;
	}
	
	@GetMapping("sub17")
	public ResponseEntity<String> method17() {
		
		return ResponseEntity.status(500).body("internal server error");
		
	}
	
	@GetMapping("sub18")
	public ResponseEntity<String> method18(){
		boolean success = Math.random() > 0.5;
		
		if(success) {
			return ResponseEntity.ok().body("data tou want");
		}else {
			return ResponseEntity.status(500).body("something wrong");
		}
	}
}
