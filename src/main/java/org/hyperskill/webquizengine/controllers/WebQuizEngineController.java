package org.hyperskill.webquizengine.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.hyperskill.webquizengine.domain.Answer;
import org.hyperskill.webquizengine.domain.Quiz;
import org.hyperskill.webquizengine.domain.Response;
import org.hyperskill.webquizengine.services.WebQuizEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;


@RestController()
@Validated
@RestControllerAdvice
@RequestMapping(value = "/api/quizzes")
public class WebQuizEngineController {

    private final WebQuizEngineService webQuizEngineService;

    @Autowired
    public WebQuizEngineController(WebQuizEngineService webQuizEngineService) {
        this.webQuizEngineService = webQuizEngineService;
    }

    @GetMapping(value = "/{id}")
    public MappingJacksonValue getQuiz(@PathVariable Long id) {
        Quiz quiz = webQuizEngineService.getQuiz(id);
        if (quiz != null) {
            return getQuizWOAnswer(webQuizEngineService.getQuiz(id));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provide correct question id.");
        }
    }

    @GetMapping
    public MappingJacksonValue getQuizzes(@RequestParam("page") Integer page,
                                          @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return new MappingJacksonValue(webQuizEngineService.getQuizzes(page, size));
    }

    @PostMapping()
    public MappingJacksonValue createQuiz(
            @CurrentSecurityContext(expression = "authentication.principal") Principal principal,
            @Valid @RequestBody Quiz quiz) {
        return getQuizWOAnswer(webQuizEngineService.createQuiz(quiz, principal.getName()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteQuiz(@CurrentSecurityContext(expression = "authentication.principal") Principal principal,
                                     @PathVariable Long id) {
        return webQuizEngineService.deleteQuiz(id, principal.getName());
    }

    @PostMapping(value = "/{id}/solve")
    public Response solveQuiz(@PathVariable Long id, @Valid @RequestBody Answer answer) {
        if (answer.getAnswer() == null) {
            answer.setAnswer(new Integer[]{});
        }
        Quiz quiz = webQuizEngineService.getQuiz(id);
        if (quiz != null) {
            return webQuizEngineService.solve(quiz, answer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provide correct question id.");
        }
    }

    @GetMapping(value = "/completed")
    public MappingJacksonValue getCompletedQuizzes(@RequestParam("page") Integer page,
                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return new MappingJacksonValue(webQuizEngineService.getCompletedQuizzes(page, size));
    }

    private MappingJacksonValue getQuizWOAnswer(Quiz quiz) {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("answer");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("answerFilter", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(quiz);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    private MappingJacksonValue getQuizzesWOAnswers(Collection<Quiz> quizzes) {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("answer");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("answerFilter", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(quizzes);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

}