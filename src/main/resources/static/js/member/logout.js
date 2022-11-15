

function getlogoutMno(){
    $.ajax({
        url: "/member/getlogoutMno",
        type: "get",
        success: function(re){
            if( re == 1 ){
                alert('로그아웃 되었습니다.')
                location.href = "http://192.168.17.131:8080";
            }
        }
    })
}