package org.hyperskill.webquizengine.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "answer")
@Getter
@Setter
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private OptionEntity option;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quizEntity;
}
