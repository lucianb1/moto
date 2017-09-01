package ro.motorzz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import ro.motorzz.core.exception.*;
import ro.motorzz.core.utils.JsonUtils;

import javax.xml.bind.ValidationException;

/**
 * Created by Luci on 21-Jun-17.
 */
@ControllerAdvice
public class ExceptionController {
    private static final JsonUtils jsonUtils = new JsonUtils();
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler({MissingServletRequestPartException.class, UnsupportedTypeException.class, BadRequestException.class, MethodArgumentNotValidException.class, ValidationException.class, InvalidArgumentException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        LOGGER.error("400 bad request: ", ex);
        return new ResponseEntity<>(toJson(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExists(Exception ex) {
        LOGGER.error("already exists exception", ex);
        return new ResponseEntity<>(toJson(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        LOGGER.error("not found exception ", ex);
        return new ResponseEntity<>(toJson(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class, PermissionDeniedException.class, AccessDeniedException.class})
    public ResponseEntity<String> handleAuthenticatioException(Exception ex) {
        LOGGER.error("Authentication exception", ex);
        return new ResponseEntity<>(toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleInternalExceptions(Throwable t) {
        LOGGER.error("Internal exception occurred: ", t);
        String responseMessage = String.format("Exception: %s, message: %s", t.getClass().getSimpleName(), t.getMessage());
        return new ResponseEntity<>(toJson(responseMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<String> handlePreconditionFailedException(Exception ex) {
        LOGGER.error("Precondition failed exception ", ex);
        return new ResponseEntity<>(toJson(ex.getMessage()), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(Exception ex) {
        LOGGER.error("Conflict exception ", ex);
        return new ResponseEntity<>(toJson(ex.getMessage()), HttpStatus.CONFLICT);
    }

    private String toJson(String message) {
        return jsonUtils.toJson(message);
    }

}
