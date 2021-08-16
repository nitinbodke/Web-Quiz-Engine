package org.hyperskill.webquizengine.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "solved_quiz")
@Getter
@Setter
public class SolvedQuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "quiz_id")
    private Long quizId;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private QuizEntity quiz;
    private Date completedAt = new Date();

    public SolvedQuizEntity() {}

    public SolvedQuizEntity(UserEntity user, QuizEntity quiz) {
        this.userId = user.getId();
        this.quizId = quiz.getId();
        this.user = user;
        this.quiz = quiz;
    }

}
