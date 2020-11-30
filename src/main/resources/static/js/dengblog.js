function post() {
    let questionId = $("#question_id").val();
    let commentContent = $("#comment_content").val();
    commentToTarget(questionId,1,commentContent)
}

function commentToTarget(targetId,type,content) {
    if(!content){
        alert("回复内容不能为空！")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            type: type
        }),
        success: function (response) {
            console.log(response);
            if(response.code == 200){
                window.location.reload();
            }else if(response.code==2003) {
                //未登录
                let isAccepted = confirm(response.message);
                if(isAccepted){
                    window.open("https://github.com/login/oauth/authorize?client_id=a6d29b46c5781446f014&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                    window.localStorage.setItem("closable",true);
                }
            }else {
                alert(response.message);
            }
        },
        dataType: "json",
        contentType:"application/json"
    });

}

function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-"+commentId).val();
    commentToTarget(commentId,2,content);
}
//展开二级评论
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-"+id);
    let collapse = e.getAttribute("data-collapse");
    if(collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active")
    }else {
        let subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length!=1){
            //展开二级评论
            comments.addClass("in");
            //标记二级品论状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/"+id, function( data ) {
                $.each( data.data.reverse(), function( index, comment ) {
                    console.log(comment);
                    let avatarElement=$("<img/>",{
                        "class":"media-object img-rounded avatar",
                        "src":comment.user.avatarUrl
                    });
                    let mediaLeft=$("<div/>",{
                        "class":"media-left"
                    });
                    mediaLeft.append(avatarElement);
                    let usernameSpan=$("<span/>",{
                        "class":"media-heading",
                        html:comment.user.name
                    });
                    let usernameHeader=$("<h6/>",{
                        "class":"media-heading"
                    });
                    usernameHeader.append(usernameSpan);

                    let commentContentDiv=$("<div/>",{
                        html:comment.content
                    });

                    let dateDiv=$("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        html:moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }));

                    let mediaBody=$("<div/>",{
                        "class":"media-body"
                    });
                    mediaBody.append(usernameHeader);
                    mediaBody.append(commentContentDiv);
                    mediaBody.append(dateDiv);
                    let mediaElement=$("<div/>",{
                        "class":"media"
                    });
                    mediaElement.append(mediaLeft);
                    mediaElement.append(mediaBody);
                    let commentElement = $("<div/>", {
                        "class": "comments"
                    });
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });

                //展开二级评论
                comments.addClass("in");
                //标记二级品论状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function selectTag(e) {
    value=e.getAttribute("data-tag")
    let previous = $("#tag").val();
    if(previous.indexOf(value)==-1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
    $("#select_tag").show();
}