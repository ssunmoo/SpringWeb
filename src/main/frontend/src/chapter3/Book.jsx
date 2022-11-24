
// 1. React 라이브러리 임포트
import React from "react";

// 2. 컴포넌트 만들기 [ 함수 만들기 ]
function Book( props ){ // props : 매개변수
    return(
        <div>
            <h1> 이 책의 이름은 { props.name } 입니다. </h1>
            <h2> 이 책은 총 { props.numOfPage } 페이지로 이루어져 있습니다. </h2>
        </div>
    )
}

// 3. 내보내기할 함수명
export default Book;