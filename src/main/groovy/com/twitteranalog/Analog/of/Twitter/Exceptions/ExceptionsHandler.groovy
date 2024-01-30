package com.twitteranalog.Analog.of.Twitter.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

import java.time.LocalDateTime

@ControllerAdvice
class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<?> handleException(Exception ex) {
        HashMap<String, String> errorObject = new HashMap<>()
        errorObject.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
        errorObject.put("message", ex.getMessage())
        errorObject.put("timestamp", LocalDateTime.now().toString())

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorObject)
    }

}
