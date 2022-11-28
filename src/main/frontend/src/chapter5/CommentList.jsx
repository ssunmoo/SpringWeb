import React from "react";
import Comment from "./Comment";



// 1. 데이터 리스트 [ 서버 통신과 통신된 결과물 ]
const comments = [ // 댓글 3개 객체를 저장하는 리스트 객체
    {
        name : "홍길동",
        comment : "안녕하세요"
    },
    {
        name : "김아무개",
        comment : "리액트 하는 중"
    },
    {
        name : "김춘배",
        comment : "리액트는 이런거구나"
    }
];


function CommentList( props ){
    // 리스트명.map(( 반복변수명 )=> { 실행문 })
    // forEach는 값이 리턴 되지 않음
    return(
        <div>
            { comments.map( (c) => {
              return(
                  <Comment name={c.name} comment={c.comment} />
              );
            })}
        </div>
    )
    // 리턴 값은 props 매개변수로 들어감
}




export default CommentList;