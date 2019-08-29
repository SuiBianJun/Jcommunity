
// 提交回复
function doReply() {

    $.ajax({
        url: "/reply",
        data: JSON.stringify({
            type: 1,
            content: $("#content").val(),
            question_id: $("#question-id").val(),
            user_id: $("#user-id").val()
        }),
        contentType: "application/json",
        // dataType: "application/json",
        type: "POST",
        success: function (result) {
            console.log(result);
            // debugger;
            var o = JSON.parse(result);
            if(o.errorCode == 4){
                var flag = window.confirm("是否立即登录？");
                if(flag == true){
                    console.log("确认登录");
                    window.open();
                }
            }
        }
    });

}