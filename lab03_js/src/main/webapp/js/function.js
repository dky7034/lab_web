/**
 * function.js
 * 08_function.html에 포함.
 */

/*
자바스크립트에서 함수를 선언(정의)하는 방법:
function 함수이름([파라미터 선언, ...]) {
	실행 코드;
	[return [반환값];]
}
- 함수의 리턴 타입을 선언하지 않음.
- 파라미터를 선언할 때는 const, let, var 키워드를 사용하지 않음!
*/

// 함수 선언:
function add(x, y) {
	console.log('x =', x, ', y =', y);
	return x + y;
}

let result = add(1, 20); // 함수 호출
console.log('result =', result);

// 자바스크립트 함수는 파라미터의 타입을 검사하지 않음!
result = add('Hello', 'JavaScript');
console.log('result =', result);

// 자바스크립트 함수는 파라미터의 개수도 검사하지 않음!
result = add(1); // 파라미터 개수보다 적은 수의 아규먼트를 전달.
console.log('result =', result); // result = NaN 출력 -> NaN: Not a Number
//-> 아규먼트를 전달하지 않은 파라미터는 undefined(값이 할당되지 않은 변수).
//-> 1 + undefined = NaN(Not a Number)

result = add(1, 2, 3); // 파라미터의 개수보다 많은 수의 아규먼트를 전달. 3은 버려지고 계산됨.
console.log('result =', result); // x = 1, y = 2, 3은 버려짐, result = 3 출력.

// 자바스크립트 함수는 arguments 속성(property)을 가지고 있음.
// argument 속성은 함수를 호출하는 곳에서 전달한 모든 아규먼트들을 저장하는 객체.
function testArgs() {
	console.log(arguments); // arguments 객체 - 배열.
	for (let x of arguments) {
		console.log(x);
	}
}

testArgs();
testArgs('안녕');
testArgs('안녕', 10, 20, 'hello');

/*
자바스크립트 함수는 객체(object)!
1. 프로퍼티(property, 멤버)를 가질 수 있음. (예) arguments
2. 변수에 저장할 수 있음.
3. 다른 함수를 호출할 때 아규먼트로 함수를 아규먼트로 전달할 수 있음.
4. 함수 내부에서 다른 함수를 선언(정의)할 수 있음.
5. 함수 자체를 리턴할 수 있음.
*/

const plus = add; // 함수 객체 add를 변수 plus에 할당(저장).
console.log(plus); // 함수 "객체"를 출력.
console.log(plus(1, 5)); // 함수 "호출 결과"를 출력.

// 익명 함수를 선언하고, 변수에 저장.
const minus = function (x, y) {
	return x - y;
};

console.log(minus(1, 2));

// 함수를 아규먼트로 갖는 함수:
function calculate(x, y, op) { // op -> 함수를 넘겨주는 자리
	return op(x, y);
}

console.log('plus =', calculate(1, 2, plus));
console.log('minus =', calculate(1, 2, minus));
console.log('divide =', calculate(1, 2, function (x, y) { // op = functino () {}
	return x / y;
}));

// 콜백(callback): (나중에 호출하기 위해서) 다른 함수의 아규먼트로 전달되는 함수.
// 이벤트 리스너(listener), 핸들러(handler), 콜백(callback) -> 특정 상황 & 이벤트가 발생해야 호출.

// 4, 5번 예시.
function increase(n) {
	// 지역(내부) 함수: 함수 내부에서 선언하는 함수. (4번)
	function addN(x) {
		return x + n;
	}
	
	return addN; // 함수 객체를 리턴. (5번)
}

const increaseTwo = increase(2); // 상수에 함수 저장... increase(2)는 function addN(x) {return x + 2);}을 리턴함. 즉, 함수를 addN 함수 객체를 리턴.
console.log(increaseTwo); // addN(x) {return x + n;} 출력. 여기서 n은 2가 됨.
console.log('increaseTwo =', increaseTwo(10)); // increaseTwo = 12 출력.
const increaseFive = increase(5);
console.log('increaseFive =', increaseFive(10)); // 함수를 만들어주는 함수...

console.log(increase(10)(10)); // 20 출력. increase(10)은 functio addN(x) {return x + 10;}을 리턴함. 이후, addN(10)을 계산하여 20이 출력됨.

// 화살표 함수(arrow function)
// (파라미터, ...) => { 실행코드; }
// (파라미터, ...) => 리턴값

const multiply = (x, y) => { return x * y; };
const multiply2 = (x, y) => x * y;

console.log(multiply(2, 3));
console.log(multiply2(3, 4));