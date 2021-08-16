package org.hyperskill.webquizengine.domain;

import org.springframework.beans.factory.annotation.Value;
import java.util.List;

public interface QuizWOAnswer {
    Long getId();
    String getTitle();
    String getText();
    @Value("#{@optionMapper.getOptions(target)}")
    List<String> getOptions();
}
