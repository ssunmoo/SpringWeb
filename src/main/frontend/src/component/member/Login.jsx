import React from "react";

export default function Login( props ){
    return(
        <div>
            <form method="post" action="/member/getmember">
                <input type="text" className="memail" name="memail" placeholder="이메일" /> <br />
                <input type="text" className="mpw" name="mpw" placeholder="비밀번호" /> <br />
                <input type="submit" value="로그인" className="Kakaoimg" /> <br />
                <a href="/oauth2/authorization/kakao"> 카카오 </a> <br />
                <a href="/oauth2/authorization/naver"> 네이버 로그인 </a> <br />
                <a href="/oauth2/authorization/google"> 구글 로그인 </a> <br />
            </form>
        </div>
    );
}