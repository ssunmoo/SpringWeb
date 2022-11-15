

function setupdate(){

   let mpw = document.querySelector('.mpw').value

    $.ajax({
        url: "/member/setupdate",
        type: "put",
        data : { "mpw": mpw },
        success: function(re){
            alert(re)
            location.href = "http://192.168.17.131:8080/";
        }
    })

}
