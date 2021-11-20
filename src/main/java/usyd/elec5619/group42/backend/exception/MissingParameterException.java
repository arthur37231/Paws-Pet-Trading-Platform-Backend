package usyd.elec5619.group42.backend.exception;

public class MissingParameterException extends BaseException {
    public MissingParameterException() {
        this("");
    }

    public MissingParameterException(String paramName) {
        super("Missing parameter " + paramName);
        this.code = 1002;
    }
}
