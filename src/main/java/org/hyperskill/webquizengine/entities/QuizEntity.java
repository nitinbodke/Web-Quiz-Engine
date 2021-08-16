package org.hyperskill.webquizengine.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "quiz")
@Getter
@Setter
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String text;
    @OneToMany(mappedBy = "quizEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionEntity> optionEntities = new ArrayList<>();
    @OneToMany(mappedBy = "quizEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerEntity> answerEntities = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public void addOptionEntity(OptionEntity optionEntity) {
        optionEntities.add(optionEntity);
        optionEntity.setQuizEntity(this);
    }

    public void removeOptionEntity(OptionEntity optionEntity) {
        optionEntities.remove(optionEntity);
        optionEntity.setQuizEntity(null);
    }

    public void addAnswerEntity(AnswerEntity answerEntity) {
        answerEntities.add(answerEntity);
        answerEntity.setQuizEntity(this);
    }

    public void removeAnswerEntity(AnswerEntity answerEntity) {
        answerEntities.remove(answerEntity);
        answerEntity.setQuizEntity(null);
    }
}
