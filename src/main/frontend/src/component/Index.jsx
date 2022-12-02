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

// 라우터 설치 [ 터미널 ] npm i react-router-dom
import { BrowserRouter, Routes, Route, Link, Router } from 'react-router-dom';
// BrowserRouter :
// Routes : Route 목록/리스트
// Route : 가상 URL 만들기 --> 해당 URL에 따른 컴포넌트 렌더링 [ SPA ]
// Link : <--> a 태그 : 하이퍼링크
// Router :

export default function Index(){
    return (
        <div>
            < BrowserRouter>

                <Header/>
                   <h3>메인페이지</h3>
                <Footer/>
                <Routes>
                    <Route path="/" />
                    <Route path="/member/signup" element={ <Signup/> } />
                </Routes>

            </BrowserRouter>
        </div>
    );
}

