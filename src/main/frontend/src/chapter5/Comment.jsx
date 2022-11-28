// 컴포넌트 만들기
// 1. 컴포넌트 이름은 첫글자가 대문자로 시작, 파일명과 컴포넌트 이름 동일하게

// ** 리액트 라이브러리 imfort 필수

// 컴포넌트 = 함수
// 1. 입력 : props [ 매개변수 = 속성개체 ]
// 2. 출력 : return [ 엘리먼트 = 가상 DOM ]

import React from "react";
import styles from "./Comment.css" // ** CSS파일 import 하기

function Comment ( props ) {
    return (
        <div className="wrapper">
            <div className="imgContainer">
                <img src="https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png"
                    className="image" />
            </div>

            <div className="contentContainer">
                <span className="nameText">{props.name}</span>
                <span className="commentText">{props.comment}</span>
            </div>
        </div>
    )
}

// ** 하단에 export default 컴포넌트명 필수 / 컴포넌트 내보내기
export default Comment;
