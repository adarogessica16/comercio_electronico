package com.api_usuarios.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorResource implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        // Siempre mostrar un mensaje de error genérico
        String message = "Ha ocurrido un error inesperado. Por favor, inténtelo de nuevo más tarde.";

        // Agrega el mensaje al modelo para mostrarlo en la página
        model.addAttribute("errorMessage", message);
        return "error"; // Devuelve la vista `error.html`
    }
}
