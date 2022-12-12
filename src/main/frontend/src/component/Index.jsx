// jsx : 리액트 확장 표현식 파일 HTML + js
// 컴포넌트 단위 애플리케이션 제작
// 컴포넌트 만들기 준비물
// 1. 첫글자는 대문자 [ 컴포넌트명 == 파일명 ]
// 2. 리액트[ 프레임워크가 아님 ] 라이브러리 집합소 [ import 많음 ]
// - import React from "react";
// - function 컴포넌트명() { return (렌더링할 코드 ); }
// - export default Index;

import React from "react";
import Header from "./Header";
import Footer from "./Footer";
import Signup from "./member/Signup";
import Login from "./member/Login";
import BoardList from "./board/BoardList";
import Home from "./Home";
import BoardWrite from "./board/BoardWrite";
import BoardView from "./board/BoardView";


// 리액트 공부방 import
import Library from "../book/chapter3/Library";
import Clock from "../book/chapter4/Clock";
import CommentList from "../book/chapter5/CommentList";
import NotificationList from "../book/chapter6/NotificationList";
import Accommodate from "../book/chapter7/Accommodate";
import ConfirmButton2 from "../book/chapter8/ConfirmButton2";
import LandingPage from "../book/chapter9/LandingPage";
import BookList from "../book/BookList"
import AttendanceBook from "../book/chapter10/AttendanceBook";
import SignUp from "../book/chapter11/SignUp";



// 라우터 설치 [ 터미널 ] npm i react-router-dom
import { HashRouter, BrowserRouter, Routes, Route, Link, Router } from 'react-router-dom';


// BrowserRouter :
// Routes : Route 목록/리스트
// Route : 가상 URL 만들기 --> 해당 URL에 따른 컴포넌트 렌더링 [ SPA ]
// Link : <--> a 태그 : 하이퍼링크
// Router :

export default function Index( props ){
    return (
        <div className="webbox">
            <BrowserRouter>
                <Header/>

                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/member/signup" element={ <Signup /> } />
                    <Route path="/member/login" element={ <Login /> } />
                    <Route path="/board/list" element={ <BoardList /> } />
                    <Route path="/board/write" element={ <BoardWrite /> } />
                    <Route path="/board/view/:bno" element={ <BoardView /> } />

                    <Route path="/book/list" element={ <BookList /> } />
                    <Route path="/book/Library" element={ <Library /> } />
                    <Route path="/book/Clock" element={ <Clock /> } />
                    <Route path="/book/CommentList" element={ <CommentList /> } />
                    <Route path="/book/NotificationList" element={ <NotificationList /> } />
                    <Route path="/book/Accommodate" element={ <Accommodate /> } />
                    <Route path="/book/ConfirmButton2" element={ <ConfirmButton2 /> } />
                    <Route path="/book/LandingPage" element={ <LandingPage /> } />
                    <Route path="/chapter10/AttendanceBook" element={ <AttendanceBook /> } />
                    <Route path="/chapter11/SignUp" element={ <SignUp /> } />
                </Routes>

                <Footer/>
            </BrowserRouter>
        </div>
    );
}

