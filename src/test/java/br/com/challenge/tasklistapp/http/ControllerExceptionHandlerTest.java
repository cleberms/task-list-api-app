package br.com.challenge.tasklistapp.http;

import br.com.challenge.tasklistapp.http.json.ExceptionJson;
import com.mongodb.MongoException;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ControllerExceptionHandlerTest {
    ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();

    @Test public void shouldReturnNotFoundException() {
        ResponseEntity<Set<ExceptionJson>> result = controllerExceptionHandler.notFoundException(new NotFoundException("not found!"), null);

        final ExceptionJson ex = result.getBody().iterator().next();

        assertEquals("NOT_FOUND", ex.getCode());
        assertEquals("not found!", ex.getMessage());
    }

    @Test public void shouldReturnMongoExceptionException() {
        ResponseEntity<Set<ExceptionJson>> result = controllerExceptionHandler.mongoExceptionException(new MongoException("mongo Exception!"), null);

        final ExceptionJson ex = result.getBody().iterator().next();

        assertEquals("SERVICE_UNAVAILABLE", ex.getCode());
        assertEquals("mongo Exception!", ex.getMessage());
    }

    @Test public void shouldReturnGenericError() {
        Set<ExceptionJson> result = controllerExceptionHandler.genericError(null);

        final ExceptionJson ex = result.iterator().next();

        assertEquals("INTERNAL_SERVER_ERROR", ex.getCode());
        assertEquals("An internal error occurred, please inform the admin.", ex.getMessage());
    }

    @Test public void shouldHandleHttpMessageNotReadableException() {
        Set<ExceptionJson> result = controllerExceptionHandler.handleHttpMessageNotReadableException(new HttpMessageNotReadableException("invalid field"));

        final ExceptionJson ex = result.iterator().next();

        assertEquals("body.incorrectValue", ex.getCode());
        assertEquals("invalid field", ex.getMessage());
    }

    @Test public void shouldHandleRequiredParameters() {
        Set<ExceptionJson> result = controllerExceptionHandler.handleRequiredParameters(new ServletRequestBindingException("invalid request"));

        final ExceptionJson ex = result.iterator().next();

        assertEquals("missingParam", ex.getCode());
        assertEquals("invalid request", ex.getMessage());
    }
}