/**
 * event.js
 * 13_event.html에 포함
 */

document.addEventListener('DOMContentLoaded', function(e) {
	// console.log(e);
	const itemInput = document.querySelector('input#itemInput');
	const btnInput = document.querySelector('button#btnInput');
	const itemList = document.querySelector('ul#itemList');
	const itemInput2 = document.querySelector('input#itemInput2');
	const itemList2 = document.querySelector('ul#itemList2');
	const username = document.querySelector('input#username');
	const result = document.querySelector('div#result');
	
	
	// 1. button#btnInput 버튼에 클릭 이벤트 리스너를 등록:
	// input에 입력된 내용을 ul의 리스트 아이템으로 추가.
	// const content = document.getElementById('itemList');
	btnInput.addEventListener('click', function (e) {
		// console.log(e); //-> PointerEvent9
		const value = itemInput.value; // input#itemInput에 입력된 값
		const item = `<li>${value}</li>`; // list item 문자열을 만듦.
		itemList.innerHTML += item; // ul#itemList에 컨텐트 추가.
		itemInput.value = ''; // input#itemInput에 입력된 값을 지움.
		itemInput.focus(); // input에 포커스를 줌(커서가 깜빡거리도록 함)
	});
	
	
	// 2. #itemInput2에 요소에 'keydown' 이벤트 리스너를 등록:
	// 엔터키가 눌렸을 때, input에 입력된 내용을 ul의 리스트 아이템으로 추가.
	itemInput2.addEventListener('keydown', (e) => {
		// console.log(e); //-> keyboardEvent
		if (e.key === 'Enter') {
			const item = `<li>${itemInput2.value}</li>`; // input에 입력된 값으로 li 문자열을 만듦.
			itemList2.innerHTML += item; // ul에 아이템을 컨텐트로 추가
			itemInput2.value = ''; // input의 내용을 지움
			itemInput2.focus(); // input에 포커스를 줌(커서가 깜빡거리도록 함)
		}
	});
	
	// 3. #username 요소에 이벤트 리스너를 등록:
	// input에 입력된 내용이 바뀔 때마다 div에 입력된 내용을 씀.
	// 중복체크에 사용함.
	username.addEventListener('keydown', function (e) {
		// console.log(e); //-> Event
		// console.log(username, value);
		result.innerHTML = `사용자 이름: <string>${username.value}<strong>`;
	});
	
});