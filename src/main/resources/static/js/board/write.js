

function setboard(){

    let data = {
        btitle : document.querySelector('.btitle').value,
        bcontent : document.querySelector('.bcontent').value
    }

    $.ajax({
         url        : "/board/setboard",
         type       : "POST",
         data       : JSON.stringify( data ),
         contentType: "application/json", // 전송 타입 : application/json
         success    : re => {
            console.log(re)
            
         }
    })

}

