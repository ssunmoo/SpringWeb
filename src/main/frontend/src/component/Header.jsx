import React, {useState} from "react";
import Styles from '../css/header.css';
import Logo from '../img/logo.gif';
import { HashRouter, BrowserRouter, Routes, Route, Link, Router } from 'react-router-dom';
import axios from "axios"; // react 비동기 통신 라이브러리


export default function Header( props ){

    // 로그인된 회원정보에 state 생명주기 // 변경정보 있을 경우 재 렌더링
    const [ login, setLogin ] = useState( null );

    // 1. 서버와 통신 [ axios ]
    // axios.type('URL', 데이터 ).then( res => { 응답 })

    axios
        .get('/member/getloginMno')
        .then( response => {
            setLogin( response.data );
        });
    // .get("url")
    // .post("url", data)
    // .delete("url")
    // .put("url", data)
    
    return (
        <div>
            <div className="header">
                <div className="header_logo">
                    <Link to="/" > <img src={Logo} className="logo" /> </Link>
                </div>
                <ul className="top_menu">
                    { login == "" ?
                        (
                            <>
                                <li> <Link to="/member/signup" > 회원가입 </Link> </li>
                                <li> <Link to="/member/login" > 로그인 </Link> </li>
                            </>
                        )
                        :
                        (
                            <>
                                <li> {login} </li>
                                <li> <a href="/member/logout" > 로그아웃 </a> </li>
                                <li> <Link to="/book/list" > 리액트 공부방 </Link> </li>
                            </>
                        )
                    }
                    <li> <Link to="/board/list" > 자유게시판 </Link></li>
                    <li> <Link to="/chatting" > 익명채팅방 </Link> </li>

                </ul>
            </div>
        </div>


    );
}