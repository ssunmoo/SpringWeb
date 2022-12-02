import React from "react";
import Styles from '../css/header.css';
import Logo from '../img/logo.gif';
import {Link} from "react-router-dom";

export default function Header( props ){
    return (
        <div>
            <img src={Logo} className="logo" />
            <h3 className="header-name">헤더</h3>
            <ul>
                <li> <Link to="/"> Home </Link> </li>
                <li> <Link to="/member/signup" > 회원가입 </Link> </li>
                <li> <Link to="/member/logout" > 로그아웃 </Link> </li>
            </ul>
        </div>


    );
}