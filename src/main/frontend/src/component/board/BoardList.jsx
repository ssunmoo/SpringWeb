import React, {useEffect, useState} from "react";
import axios from "axios";

export default function BoardList(){

    // 1. 게시물 리스트 state
    const [boardList, setBoardList ] = useState([]);

    // 2. 서버로부터 게시물 리스트를 가져오는 함수
    const getboardList = () => {
        axios
            .get("/board/boardlist", { params : { bcno: 0, page : 1 , key : "", keyword : "" }})
            .then( res => {
                console.log( res.data )
                setBoardList( res.data )
            })
            .catch( err => { console.log(err)})
    }

    useEffect( getboardList(), [] )


    return(
        <div>
            <a href="/board/write">
                <button type="button">글쓰기[로그인했을때만]</button>
            </a>
            <h1> 글 목록 페이지 </h1>
            <div className="bcategorytbox"></div>
            <table className="btable">
            </table>

        </div>
    );
}