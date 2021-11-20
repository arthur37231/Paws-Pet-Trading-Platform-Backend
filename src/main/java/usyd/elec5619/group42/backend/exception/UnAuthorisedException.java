package usyd.elec5619.group42.backend.exception;

public class UnAuthorisedException extends BaseException {
    public UnAuthorisedException() {
        super("Invalid token or token has been expired");
        this.code = 1003;
    }
}
