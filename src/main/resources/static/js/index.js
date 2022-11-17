
getloginMno()
function getloginMno(){

    $.ajax({
        url: "/member/getloginMno",
        type: "get",
        success: function(re){

            let btnbox = '';
            if( re == "0" ){    // 로그인 안했다
                btnbox += '<a href="/member/login"> <button type="button"> 로그인 </button>'
                        + '<a href="/member/signup"> <button type="button"> 회원가입 </button> </a></a> <br><br>'

            }else{               // 로그인 했다
                btnbox += ' <a href="/member/logout"> <button type="button" onclick="logout"> 로그아웃 </button> </a><br>'
                         + '<a href="/member/findpw"> <button type="button"> 비밀번호찾기 </button> </a><br>'
                         + '<a href="/member/update"> <button type="button"> 비밀번호수정 </button> </a><br>'
                         + '<a href="/member/delete"> <button type="button"> 회원탈퇴 </button> </a><br>';
            }
            document.querySelector('.btnbox').innerHTML = btnbox;
        }
    })
}

// 로그아웃
function logout(){
    $.ajax({
        url : "/member/logout" , //  요청url
        type : "get" , // 요청 메소드
        success : function(re){ // 응답
            location.href="/"; // index.html 반환 해주는 매핑 주소
                // location.href = URL
        }
    })
}


// 회원 목록
list()
function list(){
    $.ajax({
        url: "/member/list",
        type: "get",
        success: function(re){
            let html = '<tr>'
                     + '<th>번호</th><th>이메일</th><th>비밀번호</th><th>연락처</th>'
                     + '</tr>';
            re.forEach( (m) => {
                html += '<tr>'
                      + '<td>'+ m.mno +'</td><td>'+ m.memail +'</td><td>'+ m.mpw +'</td><td>'+ m.mphone +'</td>'
                      + '</tr>'
            })
            document.querySelector('.mtable').innerHTML = html;
        }
    })
} // list e






