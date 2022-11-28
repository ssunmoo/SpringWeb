import React from 'react';
import style from './signup.css'
import axios from 'axios' // npm install axios 설치 했을 경우만 가능

function Signup( props ){
    
    // class -> className로 변경
    //onclick -> onClick 변경
    // / 태그닫기

    // 1. setmember 이벤트 함수 정의
    const setmember = () => {
        // 2. 입력 받은 값 가져오기
        let info = {
            memail : document.querySelector('.memail').value,
            mpw : document.querySelector('.mpw').value,
            mphone : document.querySelector('.mphone').value
        }
        console.log( info )
        // 비동기 통신 ajax / fetch [리액트 내장] / axios [ 리액트 별도설치 ] 에시오스
        // 3. axios 비동기 통신을 이용한 서버 [ Spring] 통신
        axios
            .post( "http://localhost:8080/member/setmember", info )    // 요청
            .then( res => {                                                // 응답
                alert( res.data )
            })

    }
    
    // 2. 인증코드 요청 함수
    const getauth = () => {
        alert("클릭이벤트")
    }
    // 3. 타이머 함수
    const setimer = () => {
        alert("클릭이벤트")
    }
    // 4. 인증 버튼 확인 함수
    const authcode = () => {
        alert("클릭이벤트")
    }
    return(

        <div>
            <h3> 회원가입 </h3>
            <div>
            이메일 <input type="text" className="memail" />
            <button type="button" onClick={ getauth } className="getauthbtn"> 인증코드 발급</button><br />
                <div className="authbox">
                    <input type="text" className="authinput" />
                        <button type="button" className="authbtn" onClick={ authcode }>인증</button>
                        <span className="timerbox">  </span>
                </div>
            </div>

            연락처 <input type="text" className="mphone" /> <br/>
            비밀번호 <input type="text" className="mpw" /> <br/>
            <button type="button" onClick={ setmember }> 가입하기</button>
            <br /><br />
        </div>
    );
}
export default Signup;
