package flight.tracker.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * GlobalErrorHandler
 */

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    /**
     * Handle IllegalArgumentException
     * 
     * @param ex
     * @return error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Exeception", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handle NoSuchElementException
     * 
     * @param ex
     * @return error message
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("Exeception", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handle UnsupportedOperationException
     * 
     * @param ex
     * @return error message
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public Map<String, String> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        log.error("Exeception", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }
}
