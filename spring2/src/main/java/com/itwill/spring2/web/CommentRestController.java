package com.itwill.spring2.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.spring2.domain.Comment;
import com.itwill.spring2.dto.comment.CommentListItemDto;
import com.itwill.spring2.dto.comment.CommentRegisterDto;
import com.itwill.spring2.dto.comment.CommentUpdateDto;
import com.itwill.spring2.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//-> final 키워드가 붙은 필드들을 아규먼트로 받는 생성자를 자동으로 생성하여 의존성 주입 수행.
@RestController
//-> 이 클래스가 RESTful API를 처리하는 컨트롤러임을 나타냄.
//-> @Controller와 @ResponseBody를 합친 기능을 제공.
//-> 메서드의 반환값이 HTTP 응답 본문으로 전송되도록 동작함.
@RequestMapping("/api/comment")
//-> 해당 컨트롤러의 모든 핸들러 메서드에 대해 /api/comment 경로를 매핑.
public class CommentRestController {
    
	private final CommentService commentService; // 생성자에 의한 의존성 주입. CommentService의 메서드를 호출하기 위함.
	
    @PostMapping
    // HTTP POST 요청에 대해 해당 메서드를 매핑.
    // 클라이언트가 /api/comment 경로로 POST 요청을 보낼 때, registerComment 메서드 실행.
    public ResponseEntity<Integer> registerComment(@RequestBody CommentRegisterDto dto) {
    	// CommentRegisterDto 객체를 요청의 바디에서 읽어와서 파라미터로 받음.
    	// ResponseEntity<T>: 서버가 클라이언트로 보내는 데이터와 응답코드를 설정할 수 있는 객체.
    	// 컨트롤러 메서드의 파라미터 선언에서 사용하는 에너테이션:
    	// @RequestParam: 질의 문자열(query string)에 포함된 요청 파라미터를 읽을 때.
    	// @ModelAttribute: Post 방식의 양식 데이터를 읽을 때.
    	// @RequestBody: Ajax 요청의 요청 패킷 바디에 포함된 데이터를 읽어서 자바 객체로 변환.
    	// jackson-databind 라이브러리: JSON(JavaScriptObjectNotation) 문자열을 자바 객체로 변환(역직렬화, de-serialization).
    	// jackson-databind 동작방식: 클래스의 기본 생성자 호출 -> setter 메서드를 호출.
        log.debug("registerComment(dto={})", dto);
        
        // 서비스 계층의 메서드를 호출해서 댓글 등록 서비스를 수행.
        // dto 객체를 아규먼트(인자)로 전달하여 댓글 등록에 필요한 데이터(postid, writer, ctext)를 서비스 계층으로 전달.
        int result = commentService.create(dto);
        
        // ResponseEntity<T>: 서버가 클라이언트로 보내는 데이터와 응답코드를 설정할 수 있는 객체.
        return ResponseEntity.ok(result); //-> ok: 응답코드 200번(success/성공)과 데이터 result를 클라이언트로 전송.
    }
    
    @GetMapping("/all/{postId}")
    public ResponseEntity<List<CommentListItemDto>> getAllComments(@PathVariable long postId) { // 댓글이 여러 개가 리턴되므로 List<> 타입으로 작성.
    	// @PathVariable: 요청 주소의 일부가 변수처럼 변할 수 있는 값일 때,
    	// 요청 주소를 분석해서 컨트롤러 메서드의 파라미터로 전달.
    	log.debug("getAllComments(postId={}", postId);
    	
    	// 서비스 계층의 메서드를 호출해서 댓글 전체 목록을 가져옴.
    	List<CommentListItemDto> list = commentService.read(postId);
    	
    	return ResponseEntity.ok(list);
    	//-> 컨트롤러 메서드에서 ResponseEntity<T>를 리턴하면
    	// 자바 객체를 JSON 문자열로 변환해서 클라이언트에게 전송.
    	// jackson-databind 라이브러리가 자바 객체를 JSON 문자열로 변환을 담당.
    	// 직렬화(serialization): 자바 객체 -> JSON
    	// jackson-databind: 직렬화(객체 -> 문자열) & 역직렬화(문자열 -> 객체) 라이브러리.
    	// jackson-databind: Maven을 사용하는 경우, pom.xml 파일에 의존성을 추가하여 사용.
    }
    
    @DeleteMapping("/{id}")
    //-> {id}는 경로 변수로, 삭제할 리소스의 고유 식별자를 나타냄.
    //-> 예를 들어, /users/{id} 경로에 DELETE 요청이 오면, 
    //-> @DeleteMapping("/{id}") 어노테이션이 지정된 메서드가 호출되어 해당 ID의 사용자를 삭제할 수 있음.
    public ResponseEntity<Integer> deleteComment(@PathVariable long id) {
    	log.debug("deleteComment(id={})", id);
    	
    	// 서비스 계층의 메서드를 호출해서 댓글 삭제 서비스를 수행.
    	int result = commentService.delete(id);
    	
    	// 결과를 리턴(클라이언트로 응답을 보냄)
    	return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CommentListItemDto> getCommentById(@PathVariable long id) {
    	log.debug("getCommentById(id={})", id);
    	
    	// 서비스 계층의 메서드를 호출해서 응답을 보낼 DTO 객체를 읽어옴.
    	CommentListItemDto dto = commentService.readById(id);
    	
    	// Ajax 요청에 대한 응답을 리턴.
    	return ResponseEntity.ok(dto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateCommentById(@PathVariable long id, 
    		@RequestBody CommentUpdateDto dto) {
    	log.debug("updateCommentById(id={}, dto={})", id, dto);
    	
    	// DTO의 id(댓글 아이디)를 경로 변수(path variable) 값으로 설정.
    	dto.setId(id);
    	
    	// 서비스 계층의 메서드를 호출해서 댓글 업데이트 서비스를 수행.
    	int result = commentService.update(dto);
    	
    	// Ajax 요청에 대한 응답을 리턴.
    	return ResponseEntity.ok(result);
    }
    
    /*
     * 경로 변수:
     * 경로 변수(Path Variable)는 스프링 프레임워크에서 사용되는 URL 경로의 일부를 변수로 처리하는 방법.
     * 경로 변수는 중괄호( {} )로 둘러싸인 값으로 표현함.
     * 경로 변수를 사용하면 동적인 경로를 처리할 수 있음. 예를 들어 /users/{id}에서 {id}는 경로 변수임.
     * 경로 변수는 @PathVariable 애너테이션을 사용하여 메서드의 매개변수로 받아올 수 있음.
     * 이렇게 받아온 경로 변수는 메서드 내에서 사용하여 요청 처리 및 DB 조회 등의 작업을 수행함.
     * 예를 들어, /users/123 경로에 대한 요청이 오면, @GetMapping("/users/{id}") 어노테이션이 지정된 메서드에서 
     * id 라는 매개변수를 @PathVariable 어노테이션과 함께 사용하여 123이라는 값을 받아올 수 있음.
     */

}