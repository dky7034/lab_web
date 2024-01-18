/**
 * comments.js
 * /post/details.html에 포함.
 * 댓글 보기/감추기
 * 댓글 등록
 * 댓글 목록 불러오기
 * 댓글 수정/삭제
 */

// HTML 문서가 로드되면 실행할 함수 등록.
document.addEventListener('DOMContentLoaded', () => {
	// 페이징에 사용할 변수...
	let curPage = 0; // 현재 보고 있는 댓글 페이지.
	let totalPages = 0; // 댓글 전체 목록의 페이지 수.
	
	// bootstrap 모듈의 Collapse 객체를 생성
	const bsCollapse = new bootstrap.Collapse('div#collapseComments', {toggle: false});
	
	// Collapse 객체 토글 버튼을 찾고 클릭 이벤트 리스너를 등록
	const btnToggleCollapse = document.querySelector('button#btnToggleCollapse');
	btnToggleCollapse.addEventListener('click', () => {
		bsCollapse.toggle(); //-> 감추기->보기, 보기->감추기 기능 수행하는 코드...
		
		if (btnToggleCollapse.innerHTML === '댓글 보기') {
			btnToggleCollapse.innerHTML = '댓글 감추기';
			getAllComments(0); // 댓글 목록 갱신. (0: 첫 번째 페이지만 보겠다...)
		} else {
			btnToggleCollapse.innerHTML = '댓글 보기';
		}
	});
	
	// 댓글 등록 버튼의 이벤트 리스너:
	const btnRegisterCmt = document.querySelector('button#btnRegisterCmt');
	btnRegisterCmt.addEventListener('click', registerComment);
	
	// 댓글 더보기 버튼의 이벤트 리스너:
	const btnMoreCmt = document.querySelector('button#btnMoreCmt');
	btnMoreCmt.addEventListener('click', () => {
		getAllComments(curPage + 1); // 댓글 더보기 버튼을 누르면 다음 페이지 보여주기...
	});
	
	/* =============================================================================== */
	// ----- 함수 정의(선언)들 -----
	/*
     * btnRegisterCmt 버튼의 클릭 이벤트 리스너.
     * 댓글 등록 Ajax 요청을 보내고, 응답을 받으면 댓글 목록을 갱신하는 "비동기" 함수.
     */
	async function registerComment() { // await가 있는 함수는 async 사용...
		// 댓글이 달리는 포스트의 아이디
		const postId = document.querySelector('input#id').value;
		// 댓글 내용
		const text = document.querySelector('textarea#cmtText').value;
		// 댓글 작성자
		const writer = document.querySelector('input#cmtWriter').value;
		
		if (text === '') {
			alert("댓글 내용을 입력하세요!");
			return; // 함수 종료.
		}
		
		// Ajax 요청에 포함시켜서 보낼 데이터 -> 크롬 페이로드에 표시됨...
		const data = {postId, text, writer};
		
		try {
			// Ajax POST 요청을 보냄.
			const response = await axios.post('../api/comment', data); //-> axios 요청을 보내고 기다리겠다(await)...
			console.log(response);
			document.querySelector('textarea#cmtText').value = '';
			alert('댓글 등록 성공!');
			
			// 댓글 목록 갱신.
			getAllComments(0); // (0: 첫 번째 페이지만 보겠다...)
			
		} catch (error) {
			console.log(error)
		}
		
	} // end function registerComment()
	
	/*
	* 포스트 상세보기 페이지에서, 포스트에 달려 있는 모든 댓글 목록을 요청, 응답을 처리.
	* 댓글 목록 Collapse 객체를 펼칠 때, 댓글 등록이 성공했을 때 댓글 목록을 갱신하기 위해서 호출.
	*/
	async function getAllComments(page) { //-> 보고 싶은 페이지를 아규먼트로 전달...
		if (page === undefined) { // 아규먼트가 없으면...
			//-> undefined: 초기화되지 않은 변수.
			page = 0; // 0페이지를 보겠다는 것.
		}
		
		const postId = document.querySelector('input#id').value;
		
		// Ajax 요청을 보낼 주소. path variable에 쿼리스트링 추가...
		// path variable - 댓글이 달린 포스트 아이디.
		// request parameter - 댓글 페이지.
		const uri = `../api/comment/all/${postId}?p=${page}`; 
		try {
			const response = await axios.get(uri);
			console.log(response);
			curPage = response.data.number; // Page 객체에서 현재 페이지 숫자를 저장.
			totalPages = response.data.totalPages; // Page 객체에서 전체 페이지 개수를 저장.
			
			const divBtnMoreCmt = document.querySelector('div#divBtnMoreCmt'); // [더보기] 버튼이 있는 div.
			if (curPage + 1 < totalPages) {  // 현재 페이지 번호가 전체 페이지 개수보다 작을 때...
			//-> curPage는 배열의 인덱스처럼 0부터 시작하고,
			//-> totalPages는 페이지의 총 개수를 나타내므로 curPag + 1을 해야 함.
				// 다음 페이지가 있을 때...
				divBtnMoreCmt.classList.remove('d-none'); // [더보기] 버튼을 보여줌.
			} else { // 다음 페이지가 없을 때...
				divBtnMoreCmt.classList.add('d-none'); // [더보기] 버튼을 숨김.
			}
			
			makeCommentElements(response.data.content); // 댓글 목록 html 코드를 작성.
		} catch (error) {
			console.log(error);
		}
		
	} // end function getAllComments()
	
	/*
	* 댓글들의 배열을 아규먼트 data로 전달받아서, html 코드를 div에 추가.
	*/
	function makeCommentElements(data) {
		const cmtDiv = document.querySelector('div#cmtDiv'); // 댓글 목록을 추가할 div.
		let htmlStr = ''; // div에 삽입할 html 코드.
		for (let comment of data) { // 배열의 원소들을 순서대로 반복.
			const modifiedTime = new Date(comment.modifiedTime).toLocaleDateString(); // 수정 시간 표현 방식 변경.
			htmlStr += `
			<div class="card card-body my-1">
                <div>
                    <span class="fw-bold">${comment.writer}</span>
                    <span class="text-secondary">${modifiedTime}</span>
                </div>
                <div>
                    <textarea data-id="${comment.id}" 
                        class="cmtText form-control">${comment.text}</textarea>
                </div>
                <div class="my-1">
                    <button data-id="${comment.id}" 
                        class="btnDeleteCmt btn btn-outline-danger">삭제</button>
                    <button data-id="${comment.id}" 
                        class="btnUpdateCmt btn btn-outline-primary">업데이트</button>
                </div>
            </div>
            `;
		}
		
		if (curPage === 0) {
			cmtDiv.innerHTML = htmlStr; // div를 비우고 html 코드를 div에 삽입.
		} else {
			cmtDiv.innerHTML += htmlStr; // 기존 div 내용 뒤에 html 코드를 추가.
		}
		
		// 모든 btnDeleteCmt, btnUpdateCmt를 찾아서 클릭 이벤트 리스너를 등록.
		// 1. 모든 삭제 버튼을 찾아서 클릭 이벤트 리스너를 등록:
		const btnDeletes = document.querySelectorAll('button.btnDeleteCmt');
		btnDeletes.forEach((btn) => {
			// ***** 리스너가 중복으로 등록되는 문제 해결... *****
			btn.removeEventListener('click', deleteComment); // 이미 등록된 리스너가 있으면 제거.
			btn.addEventListener('click', deleteComment); // 리스너를 새로 등록.
		});
		
		//for (let btn of btnDeletes) {
		//	btn.addEventListener('click', deleteComment);
		//}
		
		// 2. 모든 수정 버튼을 찾아서 클릭 이벤트 리스너를 등록:
		const btnUpdates = document.querySelectorAll('button.btnUpdateCmt');
		btnUpdates.forEach((btn) => {
			// ***** 리스너가 중복으로 등록되는 문제 해결... *****
			btn.removeEventListener('click', updateComment); // 이미 등록된 리스너가 있으면 제거.
			btn.addEventListener('click', updateComment); // 리스너를 새로 등록.
		});
		
		//for (let btn of btnUpdates) {
		//	btn.addEventListener('click', updateComment);
		//}
		
		// 모든 btnDeleteCmt, btnUpdateCmt를 찾아서 클릭 이벤트 리스너를 등록.
		// 1. 모든 삭제 버튼을 찾아서 클릭 이벤트 리스너를 등록:
        // const btnDeletes = document.querySelectorAll('button.btnDeleteCmt');
        // btnDeletes.forEach((btn) => btn.addEventListener('click', deleteComment));
        // 2. 모든 수정 버튼을 찾아서 클릭 이벤트 리스너를 등록:
        // const btnUpdates = document.querySelectorAll('button.btnUpdateCmt');
        // btnUpdates.forEach((btn) => btn.addEventListener('click', updateComment));
		
	} // end function makeCommentElements()
	
	/*
	* 댓글 삭제 버튼의 클릭 이벤트 리스너.
    * 댓글 삭제 Ajax DELETE 요청을 보내고 응답을 처리하는 비동기 함수.
	* argument e: 이벤트 객체.
	*/
	async function deleteComment(e) {
		console.log("e.target =", e.target); // e.target: 이벤트가 발생한 타겟. 여기서는 삭제 버튼.
		
		const result = confirm('정말 삭제할까요?');
		if (!result) { // 사용자가 취소를 클릭했을 때...
			return; // 콜백 함수 종료.
		}
		
		// 삭제할 댓글 아이디를 찾음.
		const id = e.target.getAttribute('data-id'); // 삭제할 댓글 아이디(PK)
		console.log("삭제할 댓글 아이디:", id);
		
		// Ajax 요청을 보냄.
		const uri = `../api/comment/${id}`;
		await axios.delete(uri) // DELETE 방식의 Ajax 요청을 보냄.
			.then((response) => { // 성공 콜백. 응답(response)을 처리하는 콜백 등록.
				console.log(response);
				
				if (response.status === 200) {
					alert('댓글 삭제 성공!');
					getAllComments(0); // 댓글 목록 갱신. (0: 첫 번째 페이지만 보겠다...)
				}
			})
			.catch((error) => {
				console.log(error);
			})
			
	} // end function deleteComment()
	
	/*
	* 댓글 업데이트 버튼의 클릭 이벤트 리스너.
    * 댓글 업데이트 Ajax PUT 요청을 보내고 응답을 처리하는 비동기 함수.
	* argument e: 이벤트 객체.
	*/
	async function updateComment(e) {
		console.log("e.target =", e.target); // e.target: 이벤트가 발생한 타겟. 여기서는 수정 버튼.
		// 수정할 댓글 아이디
		const id = e.target.getAttribute('data-id'); // 댓글 아이디.
		// 수정할 댓글 내용
		const text = document.querySelector(`textarea.cmtText[data-id="${id}"]`).value; // 댓글 내용
		
		console.log("====================")
		console.log('id=${id}, text=${text}');
		console.log("====================")
		
		if (text.trim() === '') { 
			//-> text.trim(): text 문자열의 양쪽 공백을 제거한 결과를 반환.
			//-> 댓글이 비어있으면...
			alert('댓글 내용은 반드시 입력해야 합니다.');
			return; // 콜백 함수 종료.
		}
		
		const result = confirm('변경 내용을 저장할까요?');
		
		if (!result) { // 취소 버튼을 누르면...
			return; // 콜백 함수 종료.
		}
		
		try {
			// Ajax PUT 요청을 보냄.
			const response = await axios.put(`../api/comment/${id}`, { id, text });
			
			console.log("====================")
			console.log("response=", response);
			console.log("====================")
			
			alert('댓글 업데이트 성공!');
			getAllComments(0); // 댓글 목록 갱신. (0: 첫 번째 페이지만 보겠다...)
		
		} catch (error) {
			console.log(error);
		}
		
	} // end async function updateComment()
	
});