
getboards()
function getboards(){

    $.ajax({
         url        : "/board/getboards",
         type       : "get",
         success    : re => {
            html = '<tr>';
                + '<th> 번호 </th><th> 제목 </th><th> 조회수 </th> ';
                + '</tr>'

            re.forEach( (b)=> {
                html += '<tr>'
                     + '<td>'+ b.bno +'</td><td>'+ b.btitle +'</td><td>'+ b.bview +'</td>'
                     + '</tr>'
            })
            document.querySelector('.blist').innerHTML = html;

         }
    })

}