package com.itwill.spring2.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.spring2.domain.User;
import com.itwill.spring2.dto.user.UserRegisterDto;
import com.itwill.spring2.dto.user.UserSignInDto;
import com.itwill.spring2.service.PostService;
import com.itwill.spring2.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 디스패쳐 서블릿에서 호출하는 컨트롤러 메서드들을 가지고 있는 클래스.
@Slf4j
@RequiredArgsConstructor 
//-> final 키워드가 붙은 필드들을 아규먼트로 받는 생성자를 자동으로 생성하여 의존성 주입 수행.
@Controller
@RequestMapping("/user")
//-> 스프링 프레임워크에서 컨트롤러(Controller)의 요청 매핑(Request Mapping)을 
//-> 설정할 때 사용하는 어노테이션
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/signup")
	public void signup() {
		log.debug("GET - signup()");
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute UserRegisterDto dto) {
		log.debug("USER - create(dto={})", dto);
		
		// 회원 가입 서비스 수행.
		int result = userService.create(dto);
		
		return "redirect:/user/signin"; // 로그인(signin) 페이지로 이동(redirect)
		//-> redirect는 GET 방식으로 이동함.
		
	}
	@GetMapping("/signin")
    public void signin() {
        log.debug("GET - signin()");
    }
	
//	@PostMapping("/signin")
//	public String signin(@RequestParam(name = "userid") String userid, 
//			@RequestParam(name = "password") String password, HttpSession session) {
//		log.debug("POST - signin(userid={}, password={}, session={})", userid, password, session);
//		
//		// 로그인 서비스 수행.
//		User result = userService.checkUseridAndPassword(userid, password);
//		
//		log.debug("!!!!!result={}!!!!!", result);
//		if (result != null) { // 로그인 성공
//			// 로그인 성공 시 세션에 사용자 정보 저장
//			session.setAttribute("signedInUser", result.getId());
//			// 타겟 페이지로 이동.
//			return "redirect:/";
//		} else {
//			// 로그인 페이지로 이동.
//			return "redirect:/user/signin?result=f";
//		}
//	}
	
	// 선생님 코드(로그인)
	@PostMapping("/signin")
	public String signin(@ModelAttribute UserSignInDto dto, HttpSession session, 
			@RequestParam(name = "target", defaultValue = "") String target) 
			throws UnsupportedEncodingException {
		log.debug("POST - signin(dto={}, session={}, target={})", dto, session, target);
		
		// 서비스 메서드를 호출해서 아이디와 비밀번호가 일치하는 사용자가 있는지 확인.
		User user = userService.read(dto);
		if (user != null) { // 아이디와 비밀번호가 모두 일치하는 사용자가 있는 경우. 로그인 성공.
			// 세션에 로그인 사용자 정보를 저장.
			session.setAttribute("signedInUser", user.getUserid());
			// 타겟 페이지로 이동.
//			return "redirect:" + target;
			return (target.equals("")) ? "redirect:/" : "redirect:" + target;
			
		} else { // 아이디와 비밀번호가 일치하는 사용자가 없는 경우. 로그인 실패.
			// 로그인 페이지로 이동.
			return "redirect:/user/signin?result=f&target=" 
				+ URLEncoder.encode(target, "UTF-8");
		}
	}
	
	@GetMapping("/signout")
	public String signout(HttpSession session) {
		log.debug("signout(session={})", session);
		// 세션에 저장된 "signedInUser" 정보를 삭제.
		session.removeAttribute("signedInUser"); //-> 로그인 시 사용한 이름(signedInUser)을 그대로 사용.
		// 세션을 만료시킴.
		session.invalidate(); //-> 이것만 해도 충분하긴 함. 세션이 사라지므로...
		
		// 로그아웃 이후 로그인 페이지로 이동.
		return "redirect:/user/signin";
	}
	
	@GetMapping("/checkid")
	@ResponseBody
	// Ajax...
	public ResponseEntity<String> checkId(@RequestParam(name = "userid") String userid) {
		log.debug("checkId(userid={}", userid);
		
		boolean result = userService.checkUserid(userid);
		if (result) {
			return ResponseEntity.ok("Y");
		} else {
			return ResponseEntity.ok("N");
		}
	}
	
	
}
