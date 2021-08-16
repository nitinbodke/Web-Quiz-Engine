package org.hyperskill.webquizengine.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "option")
@Getter
@Setter
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quizEntity;
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof OptionEntity))
            return false;

        return id != null && id.equals(((OptionEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
