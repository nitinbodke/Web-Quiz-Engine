package org.hyperskill.webquizengine.mappers;

import org.hyperskill.webquizengine.entities.OptionEntity;
import org.hyperskill.webquizengine.entities.QuizEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OptionMapper {
    public List<String> getOptions(QuizEntity q) {
        List<String> options = new LinkedList<>();
        for (OptionEntity option : q.getOptionEntities()) {
            options.add(option.getText());
        }
        return options;
    }
}
