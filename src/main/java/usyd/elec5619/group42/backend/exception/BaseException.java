package usyd.elec5619.group42.backend.exception;

public abstract class BaseException extends RuntimeException {
    protected int code;

    public BaseException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }
}
