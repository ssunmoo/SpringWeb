
function setmember(){

    let info = {
        memail : document.querySelector('.memail').value,
        mpw : document.querySelector('.mpw').value
    }

    $.ajax({    // 아작스의 기본 전송 타입은 [문자열]
        url : "/member/setmember",
        type : "POST",
        data : JSON.stringify(info),        // 문자열을 제이슨 언어로 변환해서 값 전달
        contentType : "application/json",   // 제이슨 타입으로 보내야 컨트롤러에서 @RequestBody 으로 받을 수 있음
        success : function(re){
            alert(re);
        }
    })
}