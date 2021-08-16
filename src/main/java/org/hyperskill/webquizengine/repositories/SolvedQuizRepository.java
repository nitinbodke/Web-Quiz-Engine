package org.hyperskill.webquizengine.repositories;

import org.hyperskill.webquizengine.domain.SolvedQuiz;
import org.hyperskill.webquizengine.entities.SolvedQuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedQuizRepository extends JpaRepository<SolvedQuizEntity, Long> {

    Page<SolvedQuiz> findAllByUserId(final Long UserId, Pageable page);
    void deleteByQuizId(final Long quizId);
}
