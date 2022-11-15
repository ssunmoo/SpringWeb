
// 로그인
function getmember(){

    let info = {
        memail : document.querySelector('.memail').value,
        mpw: document.querySelector('.mpw').value
    }
    $.ajax({
        url: "/member/getmember",
        type: "post",
        data : JSON.stringify(info),
        contentType: "application/json",
        success: function(re){
            alert(re)
        }
    })
}

