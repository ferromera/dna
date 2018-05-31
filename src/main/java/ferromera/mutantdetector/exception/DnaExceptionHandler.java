package ferromera.mutantdetector.exception;

import ferromera.mutantdetector.dto.InvalidDnaResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by ferromera on 5/30/2018.
 */

@Primary
@RestControllerAdvice
public class DnaExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidDnaException.class)
    public InvalidDnaResponse handleInvalidDna(InvalidDnaException e){
        return new InvalidDnaResponse(e.getMessage()) ;
    }
}
