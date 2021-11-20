package usyd.elec5619.group42.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import usyd.elec5619.group42.backend.utils.ResponseBody;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ResponseBody> handleInvalidParameterException(InvalidParameterException e) {
        return ResponseEntity.badRequest().body(
                new ResponseBody(e)
        );
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<ResponseBody> handleMissingParameterException(MissingParameterException e) {
        return ResponseEntity.badRequest().body(
                new ResponseBody(e)
        );
    }

    @ExceptionHandler(UnAuthorisedException.class)
    public ResponseEntity<ResponseBody> handleUnAuthorisedException(UnAuthorisedException e) {
        return ResponseEntity.status(401).body(
                new ResponseBody(e)
        );
    }

    @ExceptionHandler(UnmatchedParameterTypeException.class)
    public ResponseEntity<ResponseBody> handleUnmatchedParameterTypeException(UnmatchedParameterTypeException e) {
        return ResponseEntity.badRequest().body(
                new ResponseBody(e)
        );
    }

    @ExceptionHandler(DuplicateUniqueException.class)
    public ResponseEntity<ResponseBody> handleDuplicateUniqueException(DuplicateUniqueException e) {
        return ResponseEntity.badRequest().body(
                new ResponseBody(e)
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseBody> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(
                new ResponseBody(e)
        );
    }
}
