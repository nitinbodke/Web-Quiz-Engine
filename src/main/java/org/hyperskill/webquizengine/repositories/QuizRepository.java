package org.hyperskill.webquizengine.repositories;

import org.hyperskill.webquizengine.domain.QuizWOAnswer;
import org.hyperskill.webquizengine.entities.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
    Page<QuizWOAnswer> findAllDtoBy(Pageable pageable);
}
