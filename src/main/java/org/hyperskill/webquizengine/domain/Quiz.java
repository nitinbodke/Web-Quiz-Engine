package org.hyperskill.webquizengine.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonFilter("answerFilter")
public class Quiz {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotNull
    @Size(min = 2)
    private String[] options;
    private Integer[] answer;

}
