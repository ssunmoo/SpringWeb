
function setboard(){

    let data = {
        btitle : document.querySelector('.btitle').value,
        bcontent : document.querySelector('.bcontent').value,
        bfile : document.querySelector('.bfile').value
    }

    $.ajax({
         url        : "/board/setboard",
         type       : "post",
         data       : JSON.stringify( data ), // 객체로 통신하기 위해
         contentType: "application/json", // 전송 타입 : application/json
         success    : re => {
            if( re == true ){
                location.href = "/board/list"
            }else{
                alert("글 작성 실패");
            }

            
         }
    })

}

