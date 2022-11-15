
getloginMno()
function getloginMno(){

    $.ajax({
        url: "/member/getloginMno",
        type: "get",
        success: function(re){
            alert(re);

            let btnbox = '';
            if( re == "0" ){    // 로그인 안했다
                btnbox += '<a href="/member/login"> <button type="button"> 로그인 </button>'
                        + '<a href="/member/signup"> <button type="button"> 회원가입 </button> </a></a> <br><br>'

            }else{               // 로그인 했다
                btnbox += ' <a href="/member/logout"> <button type="button"> 로그아웃 </button> </a><br>'
                         + '<a href="/member/findpw"> <button type="button"> 비밀번호찾기 </button> </a><br>'
                         + '<a href="/member/update"> <button type="button"> 비밀번호수정 </button> </a><br>'
                         + '<a href="/member/delete"> <button type="button"> 회원탈퇴 </button> </a><br>';
            }
            document.querySelector('.btnbox').innerHTML = btnbox;
        }
    })
}