import React from "react";
import Styles from '../css/header.css';
import Logo from '../img/logo.gif';
import {Link} from "react-router-dom";
import axios from "axios"; // react 비동기 통신 라이브러리


export default function Header( props ){
    
    // 1. 서버와 통신 [ axios ]
    // axios.type('URL', 데이터 ).then( res => { 응답 })
    axios.get('http://localhost:8080/member/getloginMno').then( res => {
        alert("서버와 통신완료")
    });

    
    return (
        <div>
            <div className="header">
                <div className="header_logo">
                    <Link to="/" > <img src={Logo} className="logo" /> </Link>
                </div>
                <ul className="top_menu">
                    <li> <Link to="/"> Home </Link> </li>
                    <li> <Link to="/member/signup" > 회원가입 </Link> </li>
                    <li> <Link to="/member/login" > 로그인 </Link> </li>
                    <li> <Link to="/member/logout" > 로그아웃 </Link> </li>
                    <li> <Link to="/board/list" > 게시판  </Link> </li>
                </ul>
            </div>
        </div>


    );
}