package repositories.mysql;

import model.Comment;
import repositories.abstraction.CommentRepository;

import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository implements CommentRepository {
    private final Executor executor = new Executor();

    @Override
    public void addComment(Long postId, Comment comment) {
        String query = "INSERT INTO comments (author, content, post_id) VALUES(?, ?, ?)";
        executor.execute(query, statement -> {
            statement.setString(1, comment.getAuthor());
            statement.setString(2, comment.getComment());
            statement.setLong(3, postId);
        }, resultSet -> {
            if (resultSet.next()) {
                comment.setId(resultSet.getLong(1));
            }
            return null;
        }, true);
    }

    @Override
    public List<Comment> allComments(Long postId) {
        String query = "SELECT * FROM comments WHERE post_id = ?";
        return executor.execute(query, statement -> statement.setLong(1, postId), resultSet -> {
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                comments.add(new Comment(
                        resultSet.getLong("id"),
                        postId,
                        resultSet.getString("author"),
                        resultSet.getString("content")
                ));
            }
            return comments;
        }, false);
    }
}