function post() {
    let questionId = $("#question_id").val();
    let commentContent = $("#comment_content").val();
    console.log(questionId);
    console.log(commentContent);
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            type: 1
        }),
        success: function (response) {
            console.log(response);
            if(response.code == 200){
                $("#comment_section").hide();
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