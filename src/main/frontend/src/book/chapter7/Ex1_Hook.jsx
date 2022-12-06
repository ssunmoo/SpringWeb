import React, { useState } from "react";

/*
// 오류 : return 한번이기 때문에 재 렌더링이 불가
export default function Counter( props ){
    // 2. js혹은 라이브러리
    var count = 0;

    // 3. 렌더링 되는 HTML + JSX 표현식 {} + 컴포넌트
    return(
        <div>
            <p> 총 {count} 번 클릭했습니다. </p>
            <button onClick={ ()=> count++ }>
                클릭
            </button>
        </div>
        
    );
}*/

// 해결 방법 : 리액트 훅 이라는 곳에서 useState 라이브러리 사용
export default function Counter( props ){

    const [ count, setCount ] = useState(0);
    // useState(초기값) : 배열 [ 변수명, set 함수명 ]이 리턴 / 생명주기 [ 생성,업데이트,삭제 ]
    // [ count, setCount ] count : 변수명, setCount : 해당 변수의 값을 변경하는 함수명


    // 3. 렌더링 되는 HTML + JSX 표현식 {} + 컴포넌트
    return(
        <div>
            <p> 총 {count} 번 클릭했습니다. </p>
            <button onClick={ ()=> setCount(count+1) }>
                클릭
            </button>
        </div>

    );
}