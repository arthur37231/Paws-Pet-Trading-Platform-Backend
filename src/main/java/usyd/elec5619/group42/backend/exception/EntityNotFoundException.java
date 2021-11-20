package usyd.elec5619.group42.backend.exception;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(String entityName) {
        super("Cannot find entity " + entityName);
        this.code = 1006;
    }
}
