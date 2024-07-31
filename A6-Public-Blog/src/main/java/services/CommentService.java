package services;

import model.Comment;
import repositories.abstraction.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public void addComment(Long postId, Comment comment){
        commentRepository.addComment(postId,comment);
    }

    public List<Comment> allComments(Long postId){
        return commentRepository.allComments(postId);
    }

}
