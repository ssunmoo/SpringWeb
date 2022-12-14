import React, {useEffect, useState} from "react";
import {HashRouter,BrowserRouter , Routes , Route , Link, Router, useParams} from 'react-router-dom';
import axios from "axios";
export default function BoardView(props){

    const params=useParams();   //uesParams() 훅 : 경로[URL]상의 매개변 수 가져올때

    const [board, setBoard]=useState({});   // 게시물 메모리
    const [login, setLogin]=useState(null);
    useEffect( // 1. 서버로부터 해당 게시물번호의 게시물정보 -> useState[board]요청
        ()=>axios.get("/board/getboard", {params:{bno : params.bno } })
            .then(res=>{setBoard(res.data); console.log(res.data.memail)})
        , [])
    useEffect( // 2. 서버로부터 해당 로그인된 회원의 아이디
        // return 반환 1. null 2.
        ()=>axios.get("/member/getloginMno")
            .then(res=>{setLogin(res.data); console.log(res.data)})
        , [])

    const onDelete=()=>{ // 3. 서버로부터 해당 게시물번호를 이용한 삭제 요청
        axios
            .delete("/board/delboard",{ params:{bno : params.bno } })
            .then(res=>{alert("게시물 삭제 성공"); window.location.href="/board/list"})
    }

    const getUpdate=()=>{ // 5. 해당 게시물번호의 해당하는 업데이트 페이지로 이동
        window.location.href="/board/update/"+params.bno;
    }

    return(
        <div>
            <div>{board.btitle}</div>
            <div dangerouslySetInnerHTML={{__html : board.bcontent}}></div>
            {board.bfilename != '' && <a href={"/board/filedownload?filename=" + board.filename} >{board.bfilename}</a>}
            <div>
                {login==board.memail&&<div><button type="button" onClick={onDelete}> 삭제 </button></div>}
                {login==board.memail&&<div><button type="button" onClick={getUpdate} > 수정 </button></div>}

            </div>

        </div>
    );
}
/*
    [ jsx innerHTML 사용방법 ]
    1. <태그명 dangerouslySetInnerHTML = {{__html : 데이터}}> </태그명>

    [ 조건부 렌더링 ]


* */
