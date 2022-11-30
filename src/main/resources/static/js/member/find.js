// 비밀번호 찾기
function getpw(){
    let memail = document.querySelector('.memail').value

    $.ajax({
        url: "/member/getpw",
        type: "get",
        data : { "memail": memail },
        success: function(re){
            alert(re)
        }
    })
}