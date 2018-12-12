package com.github.lyd.common.exception;

/**
 * 提示消息异常
 *
 * @author admin
 */
public class OpenMessageException extends OpenException {
    private static final long serialVersionUID = 4908906410210213271L;

    public OpenMessageException() {
    }

    public OpenMessageException(String msg) {
        super(msg);
    }

    public OpenMessageException(int code, String msg) {
        super(code, msg);
    }

    public OpenMessageException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
