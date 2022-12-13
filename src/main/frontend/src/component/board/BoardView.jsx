import React, {useEffect, useState} from "react";
import { HashRouter, BrowserRouter, Routes, Route, Link, Router, useParams } from 'react-router-dom';
import axios from "axios";
// useParams : 라우터 경로상의 매개변수 호출 훅 [ 쿼리 스트링 형식 키 :값 ]

export default function BoardView( props ){

    const params = useParams();
    const [ board, setBoard ] = useState( {} ); // 객체여서 중괄호 초기 값

    useEffect(() =>
        axios
            .get("/board/getboard", { params : { bno : params.bno } } )
            .then( res => {
                setBoard( res.data )
            })
            .catch( err => { console.log(err)}), []
    )


    return(
        <div>
            <div>{ board.btitle }</div>
            <div>{ board.bcontent }</div>
            <div>
                <button type="button"> 삭제 </button>
                <button type="button"> 수정 </button>
            </div>
        </div>
    );
}