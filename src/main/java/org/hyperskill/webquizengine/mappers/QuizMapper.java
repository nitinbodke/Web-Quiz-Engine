package org.hyperskill.webquizengine.mappers;

import org.hyperskill.webquizengine.domain.Quiz;
import org.hyperskill.webquizengine.entities.AnswerEntity;
import org.hyperskill.webquizengine.entities.OptionEntity;
import org.hyperskill.webquizengine.entities.QuizEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizMapper {

    public QuizEntity toEntity(final Quiz quiz) {
        QuizEntity quizEntity = new QuizEntity();
        quizEntity.setText(quiz.getText());
        quizEntity.setTitle(quiz.getTitle());

        for (String option : quiz.getOptions()) {
            OptionEntity optionEntity = new OptionEntity();
            optionEntity.setText(option);
            quizEntity.addOptionEntity(optionEntity);
        }

        for(Integer answer : quiz.getAnswer()) {
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setOption(quizEntity.getOptionEntities().get(answer));
            quizEntity.addAnswerEntity(answerEntity);
        }

        return quizEntity;
    }

    public Quiz toDomain(final QuizEntity quizEntity) {
        Quiz quiz = new Quiz();
        quiz.setId(quizEntity.getId());
        quiz.setTitle(quizEntity.getTitle());
        quiz.setText(quizEntity.getText());
        List<String> options = new ArrayList<>();
        for (OptionEntity optionEntity : quizEntity.getOptionEntities()) {
            options.add(optionEntity.getText());
        }
        quiz.setOptions(options.toArray(new String[0]));
        List<Integer> answers = new ArrayList<>();
        for (AnswerEntity answerEntity : quizEntity.getAnswerEntities()) {
            answers.add(quizEntity.getOptionEntities().indexOf(answerEntity.getOption()));
        }
        quiz.setAnswer(answers.toArray(new Integer[0]));
        return quiz;
    }
}
