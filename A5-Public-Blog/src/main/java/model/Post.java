package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Long id;
    @NotNull
    @NotEmpty(message = "Author is required")
    private String author;
    @NotNull
    @NotEmpty(message = "Title is required")
    private String title;
    @NotNull
    @NotEmpty(message = "Content is required")
    private String content;
    @NotNull
    private Date date;
    private List<Comment> comments = new CopyOnWriteArrayList<>();
}
