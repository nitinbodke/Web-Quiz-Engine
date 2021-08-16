package org.hyperskill.webquizengine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Date;

@Value
public class SolvedQuiz {
    @JsonProperty("id")
    Long quizId;
    Date completedAt;
}
