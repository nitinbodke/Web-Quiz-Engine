package org.hyperskill.webquizengine.services;

import org.hyperskill.webquizengine.domain.*;
import org.hyperskill.webquizengine.entities.QuizEntity;
import org.hyperskill.webquizengine.entities.SolvedQuizEntity;
import org.hyperskill.webquizengine.entities.UserEntity;
import org.hyperskill.webquizengine.mappers.QuizMapper;
import org.hyperskill.webquizengine.repositories.QuizRepository;
import org.hyperskill.webquizengine.repositories.SolvedQuizRepository;
import org.hyperskill.webquizengine.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class WebQuizEngineService {

    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public Quiz getQuiz(Long id) {
        Optional<QuizEntity> quizEntity = quizRepository.findById(id);
        return quizEntity.map(quizMapper::toDomain).orElse(null);
    }

    public Collection<Quiz> getQuizzes() {
        List<QuizEntity> quizEntities = quizRepository.findAll();
        List<Quiz> quizzes = new ArrayList<>();
        for (QuizEntity quizEntity : quizEntities) {
            quizzes.add(quizMapper.toDomain(quizEntity));
        }
        return quizzes;
    }

    public Page<QuizWOAnswer> getQuizzes(final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return quizRepository.findAllDtoBy(pageable);
    }

    public Page<SolvedQuiz> getCompletedQuizzes(final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("completedAt").descending());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Long userId = userRepository.findByName(authentication.getName()).getId(); ;
        return solvedQuizRepository.findAllByUserId(userId, pageable);
    }

    public Quiz createQuiz(Quiz quiz, final String username) {
        // If answer array is null, initialize with emtpy array
        if (quiz.getAnswer() == null) {
            quiz.setAnswer(new Integer[]{});
        }

        // Fetch user id
        UserEntity userEntity = userRepository.findByName(username);

        // persist quiz into db
        QuizEntity quizEntity = quizMapper.toEntity(quiz);
        quizEntity.setUserEntity(userEntity);
        quizEntity = quizRepository.save(quizEntity);
        quiz.setId(quizEntity.getId());
        return quiz;
    }

    @Transactional
    public ResponseEntity<?> deleteQuiz(final Long id, final String username) {
        QuizEntity quizEntity = quizRepository.getOne(id);
        if (quizEntity.getUserEntity() != null && quizEntity.getUserEntity().getName().equals(username)) {
            solvedQuizRepository.deleteByQuizId(quizEntity.getId());
            quizRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public Response solve(Quiz quiz, Answer answer) {
        Response response = new Response();
        if (Arrays.equals(quiz.getAnswer(), answer.getAnswer())) {
            response.setSuccess(true);
            response.setFeedback("Congratulations, you're right!");
            addToSolvedQuiz(quiz);
        } else {
            response.setSuccess(false);
            response.setFeedback("Wrong answer! Please, try again.");
        }
        return response;
    }

    private void addToSolvedQuiz(Quiz quiz) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByName(authentication.getName()); ;
        // TODO is there any way to avoid this query
        Optional<QuizEntity> quizEntity = quizRepository.findById(quiz.getId());
        SolvedQuizEntity solvedQuiz = new SolvedQuizEntity(user, quizEntity.get());
        solvedQuizRepository.save(solvedQuiz);
    }
}
