

function setupdate(){

   let mpw = document.querySelector('.mpw').value

    $.ajax({
        url: "/member/setupdate",
        type: "put",
        data : { "mpw": mpw },
        success: function(re){
            alert(re)
        }
    })

}
