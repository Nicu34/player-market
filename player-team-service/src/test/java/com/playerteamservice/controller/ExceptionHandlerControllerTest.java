package com.playerteamservice.controller;

import com.playerteamservice.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static com.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExceptionHandlerControllerTest {

    private ExceptionHandlerController exceptionHandlerController = new ExceptionHandlerController();

    @Test
    void handleRegistrationException() {
        EntityNotFoundException e = new EntityNotFoundException(MESSAGE);

        ResponseEntity<String> responseBody = exceptionHandlerController.handleEntityNotFoundException(e);

        assertNotNull(responseBody);
        assertEquals(MESSAGE, responseBody.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseBody.getStatusCode());
    }

    @Test
    void handleValidationExceptions() {
        MethodArgumentNotValidException e = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(e.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("", FIELD, MESSAGE)));

        ResponseEntity<Map<String, Map<String, String>>> responseEntity = exceptionHandlerController.handleValidationExceptions(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        Map<String, Map<String, String>> responseMap = responseEntity.getBody();
        assertNotNull(responseMap);
        assertTrue(responseMap.containsKey(ERROR_MESSAGE));

        Map<String, String> errorMap = responseMap.get(ERROR_MESSAGE);
        assertNotNull(errorMap);
        assertTrue(errorMap.containsKey(FIELD));
        assertEquals(MESSAGE, errorMap.get(FIELD));
    }
}