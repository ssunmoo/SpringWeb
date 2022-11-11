
getboards()
function getboards(){

    $.ajax({
         url        : "/board/getboards",
         type       : "get",
         success    : re => {
            console.log(re);



         }
    })

}