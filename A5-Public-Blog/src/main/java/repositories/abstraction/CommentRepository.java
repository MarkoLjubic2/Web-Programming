package repositories.abstraction;

import model.Comment;

import java.util.List;

public interface CommentRepository {

    void addComment(Long postId, Comment comment);
    List<Comment> allComments(Long postId);
}
