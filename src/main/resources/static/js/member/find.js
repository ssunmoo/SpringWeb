// 로그인
function getpw(){
    let memail = document.querySelector('.memail').value

    $.ajax({
        url: "/member/getpw",
        type: "get",
        data : { "memail": memail },
        success: function(re){
            alert(re)
            location.href = "http://192.168.17.131:8080/";
        }
    })
}