package com.api_productos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ManejadorExcepcionesGlobal {

    private static final Logger logger = LoggerFactory.getLogger(ManejadorExcepcionesGlobal.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView manejarExcepcion(HttpServletRequest request, Exception ex) {
        logger.error("Excepción ocurrida al procesar la solicitud: " + request.getRequestURL(), ex);

        // Obtiene el código de estado HTTP de la excepción
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "Ha ocurrido un error.");
        mav.addObject("message", ex.getMessage());
        mav.addObject("url", request.getRequestURL());
        mav.addObject("httpStatus", httpStatus.value());

        return mav;
    }
}