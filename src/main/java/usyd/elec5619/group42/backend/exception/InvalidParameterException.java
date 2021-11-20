package usyd.elec5619.group42.backend.exception;

public class InvalidParameterException extends BaseException {
    public InvalidParameterException() {
        this("");
    }

    public InvalidParameterException(String paramName) {
        super("Invalid parameters in the request " + paramName);
        this.code = 1001;
    }
}
