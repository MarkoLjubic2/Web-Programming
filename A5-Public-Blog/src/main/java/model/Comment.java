package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private long id;
    private long postId;
    @NotNull
    @NotEmpty(message = "Author is required")
    private String author;
    @NotNull
    @NotEmpty(message = "Comment is required")
    private String comment;
}
