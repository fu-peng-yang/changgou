$(function() {
    $("#u1261_text").click(function(){
        $.get("系统首页.html", function(result){
            $("div").html(result);
        });
    });
})