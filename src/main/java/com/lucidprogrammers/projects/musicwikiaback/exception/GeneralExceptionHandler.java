package com.lucidprogrammers.projects.musicwikiaback.exception;

import com.lucidprogrammers.projects.musicwikiaback.util.Constants;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.HTTPErrorResponse;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.MusicWikiaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * Handles controlled and unexpected exceptions
 * thrown in the execution of the program
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@ControllerAdvice
public class GeneralExceptionHandler {

        /**
         * Handles HTTP response when a controlled exception is thrown
         *
         * @param responseStatusException the response status exception instance
         * @return controlled HTTP response
         */
        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<MusicWikiaResponse<HTTPErrorResponse>> handleResponseStatusException(
                ResponseStatusException responseStatusException) {
                MusicWikiaResponse<HTTPErrorResponse> failureResponse = new MusicWikiaResponse<>(
                        Constants.STATUS_MESSAGE_FAILURE,
                        Constants.STATUS_TYPE_FAILURE,
                        Constants.STATUS_CODE_UNAUTHORIZED,
                        new HTTPErrorResponse(responseStatusException.getReason()));
                return new ResponseEntity<>(failureResponse, responseStatusException.getStatus());
        }

        /**
         * Handles HTTP response when uncontrolled exceptions are thrown
         *
         * @param e an uncontrolled exception
         * @return uncontrolled HTTP response
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<MusicWikiaResponse<HTTPErrorResponse>> handleAnyException(Exception e) {
                MusicWikiaResponse<HTTPErrorResponse> fatalResponse = new MusicWikiaResponse<>(
                        Constants.STATUS_MESSAGE_FATAL,
                        Constants.STATUS_TYPE_FATAL,
                        Constants.STATUS_CODE_SERVER_ERROR,
                        new HTTPErrorResponse(e.getMessage())
                );
                return new ResponseEntity<>(fatalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
