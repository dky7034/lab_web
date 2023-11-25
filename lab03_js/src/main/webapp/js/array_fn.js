/**
 * array_fn.js
 * 09_arrayfn.html에 포함
 */


document.addEventListener('DOMContentLoaded', function () {
	let numbers = []; // 빈 배열
	
	// numbers 배열에 정수 1 ~ 10까지 차례로 저장하고 콘솔 로그 출력.
	for (let i = 1; i <= 10; i++) {
		// numbers.push(i); // push -> 원본 배열에 원소를 추가. 원본 배열이 변경. 리턴값을 저장할 필요 없음.
		numbers = numbers.concat(i);// concat -> 원본 배열을 변경하지 않음! 새 원소가 추가된 새로운 배열을 만들어서 리턴. 리턴된 새로운 배열을 저장해야 함. 
	}
	console.log('numbers 배열:', numbers);
	
	// numbers에서 홀수들만 찾아서 저장하는 배열을 만들고 콘솔 로그 출력.
	let oddNumbers = []; // 홀수들을 저장하기 위한 빈 배열.
	for (let x of numbers) {
		if (x % 2) { // x % 2 === 1 // 값이 있으면 true이므로!
			oddNumbers = oddNumbers.concat(x);
		}
	}
	console.log('홀수 배열:', oddNumbers);
	//
	oddNumbers = [];
	for (let i = 0; i < numbers.length; i++) {
		if (numbers[i] % 2 !== 0) {
			oddNumbers.push(numbers[i]);
		}
	}
	console.log('홀수 배열:', oddNumbers);
	// 익명 함수 사용.
	oddNumbers = numbers.filter(function (x) {
		return x % 2;
	});
	console.log('홀수 배열:', oddNumbers);
	// 화살표 함수 사용.
	oddNumbers = numbers.filter((x) => x % 2);
	console.log('홀수 배열:', oddNumbers);
	
	// numbers 원소들의 제곱을 저장하는 배열을 만들고 콘솔 로그 출력.
	let squaredNumbers = []; // 제곱을 저장할 빈 배열.
	for (let x of numbers) {
		squaredNumbers = squaredNumbers.concat(x ** 2); // 거듭제곱 연산자: x ** 2 = x * x, x ** 3 = x * x * x
	}
	console.log('제곱 배열', squaredNumbers);
	//
	squaredNumbers = numbers.map((x) => x ** 2); // map 사용.
	console.log('제곱 배열', squaredNumbers);
	//
	for (let i = 0; i < numbers.length; i++) {
		squaredNumbers.push(numbers[i]*numbers[i]);
	}
	console.log('제곱 배열:', squaredNumbers);
	//
	squaredNumbers = numbers.map(function (x) {
    	return Math.pow(x, 2);
  	});
  	console.log('제곱 배열:', squaredNumbers);
	
	// numbers에서 홀수들의 제곱을 저장하는 배열을 만들고, 콘솔 로그 출력.
	let squaredOddNumbers = [];
	for (let i = 0; i < oddNumbers.length; i++) {
		squaredOddNumbers.push(oddNumbers[i]*oddNumbers[i]);
	}
	console.log('홀수의 제곱 배열:', squaredOddNumbers);
	//
	squaredOddNumbers = oddNumbers.map(function (x) {
    	return Math.pow(x, 2);
  	});
  	console.log('홀수의 제곱 배열:', squaredOddNumbers);
  	//
  	squaredOddNumbers = numbers.filter((x) => x % 2).map((x) => x ** 2);
  	console.log('홀수의 제곱 배열:', squaredOddNumbers);
});