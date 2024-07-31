package repositories.inmemory;

import model.Comment;
import repositories.abstraction.CommentRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InMemoryCommentRepository implements CommentRepository {

    private static final List<Comment> comments = new CopyOnWriteArrayList<>();

    @Override
    public void addComment(Long postId, Comment comment) {
        comment.setId(comments.size());
        comments.add(comment);
    }

    @Override
    public List<Comment> allComments(Long postId) {
        return comments.stream()
                .filter(comment -> comment.getPostId() == postId)
                .collect(Collectors.toList());
    }
}
