
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
            //var o = JSON.parse(result);
            if(result.errorCode == 4){
                var flag = window.confirm("是否立即登录？");
                if(flag == true){
                    console.log("确认登录");
                    window.open('https://github.com/login/oauth/authorize?client_id=5f47304a01c84a4830ac&scope=user&redirect_uri=http://localhost:8080/callback');

                    window.localStorage.setItem("login-window-close", "true");
                }
            }
            $("#content").val('');
            window.location.reload();
        }
    });

}

// 查询评论
function showComment(commentDiv){

    var reply_id = $(commentDiv).attr("reply-id");
    var targetDiv = $("#comment-div" + reply_id);
    var question_id = $(targetDiv).attr("question-id");
    var user_id = $(targetDiv).attr("user-id");
    var reply_id = $(targetDiv).attr("reply-id");

    if(targetDiv.css("display") == 'none'){
        targetDiv.css("display", "");
        $.ajax({
            url: "/comment",
            data: JSON.stringify({
                question_id: question_id,
                user_id: user_id,
                type: 2,
                reply_id: reply_id
            }),
            contentType: "application/json",
            type: "POST",
            success: function (result) {
                console.log(result);
                // debugger;
                if(result.errorCode == 200){
                    // 获取评论数据
                    // 拼接
                    targetDiv.html('');//清空
                    var commentListDiv = "";
                    for(var i = 0; i < result.data.length; i++){
                    commentListDiv += "<div class='media' style='margin-bottom: 10px;'>" +
                                            "<div class='media-left'>" +
                                                "<a href='#'>" +
                                                "<img class='media-object avator-size' src='" + result.data[i].user.user_avator_url + "' alt='avator'>" +
                                                "</a>" +
                        "                   </div>" +
                        "                   <div class='media-body'>" +
                                                "<span class='question-font'>"+ result.data[i].user.user_name + "</span>" +
                                                "<div class='question-font'>" + result.data[i].content + "</div>" +
                                            "</div>" +
                                        "</div>";
                    }
                    var comment_input = "<input" + " id=content-" + reply_id + " type='text' class='form-control' placeholder='评论...'>";
                    var comment_button = "<button" + " reply-id=" + reply_id + " question-id=" + question_id + " user-id=" + user_id + " class='btn btn-success pull-right' style='margin-top: 10px;' onclick='doComment(this)'>评论</button>";
                    targetDiv.html(commentListDiv + comment_input + comment_button);

                }
            }
        });
    }else{
        targetDiv.css("display", "none");
    }
}

// 添加评论
function doComment(commentBtn){

    var question_id = $(commentBtn).attr("question-id");
    var user_id = $(commentBtn).attr("user-id");
    var reply_id = $(commentBtn).attr("reply-id");
    var content = $("#content-" + $(commentBtn).attr("reply-id"));
        $.ajax({
        url: "/comment/add",
        data: JSON.stringify({
            question_id: question_id,
            user_id: user_id,
            type: 2,
            content: $(content).val(),
            reply_id: reply_id
        }),
        contentType: "application/json",
        type: "POST",
        success: function (result) {
            console.log(result);
            // debugger;
            if(result.errorCode == 200){
                console.log(result.errorMsg);
                // 刷新页面
            }
        }
    });

}

var _question_tag_show_flag = true;
var _current_tag_content = new Set();// 当前选中的标签集合
function doShowQuestionTag() {
    var question_tag_div = $("#question-tag-tab");
    question_tag_div.html('');
    if(_question_tag_show_flag){
        //请求tag数据
        $.ajax({
            url: "/question/tags",
            type: "GET",
            success: function (result) {
                console.log(result);
                // debugger;
                if(result.code == 0){
                    // 显示标签
                    var data = result.data;
                    var tagTab = "<ul class='nav nav-tabs'>";
                    var tagContent = "";
                    if(data.tagList.length < 1){
                        tagTab += "标签库为空";
                    }else{
                        for(var i = 0; i < data.tagList.length; i++){
                            tagTab += "<li role='presentation' class=" + (i == 0 ? 'active' : '') + "><a tagName='" + data.tagList[i].name + "'href='javascript:void(0);' onclick='doTagTabClick(this)'>" +
                                data.tagList[i].name + "</a></li>";
                            tagContent += "<div class='tag-content' " + (i != 0 ? "style='display:none'":'') + " tagName='" + data.tagList[i].name + "'>";
                            for(var j = 0; j < data.tagList[i].data.length; j++){
                                tagContent += "<span class='glyphicon glyphicon-tags' aria-hidden='true' style='background-color: darkgray' " + "onclick='doAddTag(this)'" + ">" +
                                    data.tagList[i].data[j]  +  "</span>  ";
                            }
                            tagContent += "</div>";
                        }
                    }
                    tagTab += "</ul>";
                    question_tag_div.append(tagTab);
                    question_tag_div.append(tagContent);
                    question_tag_div.css("display", "");
                    _question_tag_show_flag = false;
                }else{
                    console.log("操作失败");
                }
            }
        });

    }else{
        question_tag_div.css("display", "none");
        _question_tag_show_flag = true;
    }
}

function doTagTabClick(subtab){
    var tagName = $(subtab).attr("tagName");
    var tabContent = $(".tag-content");
    for(var i = 0; i < tabContent.length; i++){
        if(tagName == $(tabContent[i]).attr("tagName")){
            $(tabContent[i]).css("display", "");
        }else{
            $(tabContent[i]).css("display", "none");
        }
    }

    var tagTab = $($(subtab)[0].parentNode).siblings();
    //console.log($(subtab)[0].pare);
    for(var i = 0; i < tagTab.length; i++){
        $(tagTab[i]).removeClass("active");
    }
    $($(subtab)[0].parentNode).addClass("active");

}
function doAddTag(tag){

    var tagName = $(tag).text().trim();

    if(_current_tag_content.has(tagName)){
        // 闪烁已添加的标签
    }else{
        var tagContent = '';
        _current_tag_content.add(tagName);
        tagContent += "<div style='display: inline-block; margin-right: 10px;' class='label label-info'>" +
            "<div style='position: relative;'>" +
            tagName +
            "<span style='color: red;position: absolute;top: -10px;right: -15px;' onclick='doRemoveTag(this)' tagName='" + tagName + "'>x</span>" +
            "</div></div>";

        $($(".input-tag")[0]).append(tagContent);
    }

}

function doRemoveTag(tag){

    var tagName = $(tag).attr("tagName");
    if(_current_tag_content.has(tagName)){
        _current_tag_content.delete(tagName);
        $(tag)[0].parentNode.parentNode.remove();
    }
}

function doPub() {
    //var title = $("#title").val();
    //var description = $("#description").val();
    var tags = Array.from(_current_tag_content).join(",");

    $("#tags").val(tags);

    $("#form-question-info").submit();

}