package com.itwill.springboot1.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot1.dto.Author;
import com.itwill.springboot1.dto.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	/*
	이 코드에서 "home"이라는 값은 HTML 또는 JSP 파일의 이름을 나타냅니다. 
	Spring Framework에서는 기본적으로 ViewResolver를 통해 뷰 이름을 해석하고 해당하는 뷰를 찾아 렌더링합니다.
	기본적으로 Thymeleaf나 JSP와 같은 템플릿 엔진을 사용할 때, 뷰 이름은 템플릿 파일의 경로 및 이름을 나타내며,
	일반적으로 ".html"이나 ".jsp"와 같은 확장자를 포함하지 않습니다. 
	따라서 "home"이라는 뷰 이름은 "home.html" 또는 "home.jsp"와 같은 템플릿 파일을 나타내게 됩니다.
	이렇게 함으로써 컨트롤러는 "home"이라는 뷰 이름을 반환하고, 
	ViewResolver는 해당하는 뷰 템플릿 파일을 찾아 렌더링하여 사용자에게 보여주게 됩니다.
	*/
	@GetMapping("/") // context root로 들어오는 GET 방식의 요청을 처리하는 메서드.
	public String home(Model model) { // 뷰에 특정 값을 전달하기 위해 Model 사용.
		log.info("home()");
		
		LocalDateTime now = LocalDateTime.now(); // 현재 시간
		model.addAttribute("currentTime", now); // ("템플릿에서 사용할 이름", 실제 값을 가진 객체)
		
		Author author = Author.builder().FirstName("약용").lastName("정").build();
		Book book = Book.builder().id(100).title("목민심서").author(author).build();
		model.addAttribute("book", book);
		
		return "home"; //-> 뷰의 이름: home.html
	}
	
	// 질의 문자열(query string)의 요청 파라미터를 처리하는 컨트롤러 메서드:
	@GetMapping("/book")
	// @RequestParam -> 쿼리스트링의 요청 파라미터를 컨트롤러 메서드의 매개변수로 받아오기 위해 사용.
	public void bookDetails(@RequestParam(name = "id") int id, Model model) { 
		// 로그 레벨(log level): trace < debug < info < warning < error < fatal
		// 스프링 부트에서 기본 로그 출력 레벨은 info 이상.
		log.info("bookDetails(id={})", id);
		
		Book book = Book.builder().id(id).title("책_" + id).build();
		model.addAttribute("book", book);
		
		// return 값이 없는 경우 뷰의 이름은 요청 주소와 같음 -> book.html
	}
	
	// 경로 변수(path variable)을 처리하는 컨트롤러 메서드:
	// 경로 변수: URL 주소가 변수처럼 변한다는 뜻.
	@GetMapping("/book/{id}")
	public String bookDetails2(@PathVariable(name = "id") int id, Model model) {
		log.info("bookDetails2(id={})", id);
		
		Book book = Book.builder().id(id).title("BOOK_" + id).build();
		model.addAttribute("book", book);
		
		return "book"; //-> 뷰의 이름: book.html
	}
	
//	// books 더미 데이터 만들기. (외부에서 따로 생성하는 방법)
//	private List<Book> generateBooks() {
//		List<Book> generatedBooks = new ArrayList<>();
//		
//		// 10개의 책 리스트를 생성.
//		for (int i = 1; i <= 10; i++) {
//			Author author = Author.builder().FirstName("FirstName" + i).lastName("lastName" + i).build();
//			
//			Book book = Book.builder().id(i).title("Book_" + i).author(author).build();
//			
//			generatedBooks.add(book);
//		}
//		
//		return generatedBooks;
//	}
	
	@GetMapping("/books/list")
	public String booksList(Model model) {
		log.info("booksList()");
		
		// 더미 데이터를 저장하기 위한 리스트 객체 생성(GetMapping 메서드 내부에서 생성)
		ArrayList<Book> generatedBooks = new ArrayList<>();
		for (int i = 1; i <=10; i++) {
			Book books = Book.builder().id(i).title("TITLE_" + i)
						.author(Author.builder().FirstName("First_"+ i).lastName("Last_" + i).build()).build();
			generatedBooks.add(books);
		}
		
		// 더미 데이터 10개 생성. (외부에서 더미 데이터를 생성했을 경우 사용)
//		List<Book> generatedBooks = generateBooks();
		
		// 생성된 더미 데이터(list)의 0번째 인덱스(첫번째 값)의 author를 null로 set.
		generatedBooks.get(0).setAuthor(null);
		// 더미 데이터를 모델에 실어서 뷰에게 전달.
		model.addAttribute("booksList", generatedBooks);
		
		// return "list"; //-> 뷰: templates/list.html
		return "books/list"; //-> 뷰: templates/books.list.html
		// 리턴 타입이 void 인 경우 뷰: templates/books/list.html
	}
	
}