package com.itwill.springboot4.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot4.dto.MemberSignupRequestDto;
import com.itwill.springboot4.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberSvc;
	
	@GetMapping("/login")
	public void login() { //-> 리턴타입이 void: mapping 주소와 같은 html을 반환. (member 폴더 생성 - login html 생성)
		log.info("GET - login()");
	}
	
	@GetMapping("/signup")
	public void signup() {
		log.info("GET - signup()");
	}
	
	@PostMapping("/signup")
    public String signup(@ModelAttribute MemberSignupRequestDto dto) {
        log.info("POST - signup(dto={})", dto);
        
        memberSvc.createMember(dto);
        
        return "redirect:/member/login";
    }
	
}
