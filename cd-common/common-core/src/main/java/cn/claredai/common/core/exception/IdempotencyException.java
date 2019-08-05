package cn.claredai.common.core.exception;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/23 23:36
 **/
public class IdempotencyException extends RuntimeException {
    private static final long serialVersionUID = 3681368742386960156L;

    public IdempotencyException(String message) {
        super(message);
    }
}
