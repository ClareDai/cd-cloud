package cn.claredai.common.core.exception;

/**
 * @Description 分布式锁异常
 * @Author ClareDai
 * @Date create in 2019/6/23 23:02
 **/
public class LockException extends RuntimeException {
    private static final long serialVersionUID = 1573086029434832199L;

    public LockException(String message) {
        super(message);
    }
}
