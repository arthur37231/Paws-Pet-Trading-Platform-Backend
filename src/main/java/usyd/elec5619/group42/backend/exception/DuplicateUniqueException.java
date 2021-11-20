package usyd.elec5619.group42.backend.exception;

public class DuplicateUniqueException extends BaseException {
    public DuplicateUniqueException() {
        this("");
    }

    public DuplicateUniqueException(String attribute) {
        super("Cannot create new entity with unique attribute " + attribute);
        this.code = 1005;
    }
}
