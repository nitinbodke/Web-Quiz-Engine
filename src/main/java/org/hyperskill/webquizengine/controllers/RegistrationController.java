package org.hyperskill.webquizengine.controllers;

import org.hyperskill.webquizengine.domain.User;
import org.hyperskill.webquizengine.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RestControllerAdvice
@RequestMapping(value = "/api/register")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void register(@Valid @RequestBody User user) {
        registrationService.register(user);
    }
}
