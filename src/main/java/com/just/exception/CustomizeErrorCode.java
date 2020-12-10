package com.just.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不在了,建议换一个问题试试！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！"),
    NO_LOGIN(2003,"您未登录，当前操作需要登录！请登录后评论"),
    SYS_ERROR(2004,"服务器冒烟了，请稍后再试"),
    TYPE_PARAM_WRONG(2005,"评论类型不存在"),
    COMMENT_NOT_FOUNT(2006,"你操作的评论不存在了"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"这是别人的信息哟"),
    NOTIFICATION_NOT_FOUND(2009,"找不到消息")
    ;
    private Integer code;
    private String message;


    CustomizeErrorCode(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
