package usyd.elec5619.group42.backend.exception;

public class UnmatchedParameterTypeException extends BaseException {
    public UnmatchedParameterTypeException() {
        this("");
    }
    
    public UnmatchedParameterTypeException(String paramName) {
        super("Parameter " + paramName + " has wrong data type");
        this.code = 1004;
    }
}
