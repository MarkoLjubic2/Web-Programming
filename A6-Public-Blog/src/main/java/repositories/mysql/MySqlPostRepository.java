package repositories.mysql;

import model.Post;
import repositories.abstraction.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class MySqlPostRepository implements PostRepository {
    private final Executor executor = new Executor();

    @Override
    public void addPost(Post post) {
        String query = "INSERT INTO posts (author, title, content, date) VALUES(?, ?, ?, ?)";
        executor.execute(query, statement -> {
            statement.setString(1, post.getAuthor());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getContent());
            statement.setDate(4, post.getDate());
        }, resultSet -> {
            if (resultSet.next()) {
                post.setId(resultSet.getLong(1));
            }
            return null;
        }, true);
    }

    @Override
    public List<Post> allPosts() {
        String query = "SELECT * FROM posts";
        return executor.execute(query, statement -> {}, resultSet -> {
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                Post post = new Post(
                        resultSet.getLong("id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getDate("date"),
                        new ArrayList<>()
                );
                posts.add(post);
            }
            return posts;
        }, false);
    }

    @Override
    public Post findPost(Long id) {
        String query = "SELECT * FROM posts WHERE id = ?";
        return executor.execute(query, statement -> statement.setLong(1, id), resultSet -> {
            if (resultSet.next()) {
                Post post = new Post(
                        resultSet.getLong("id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getDate("date"),
                        new ArrayList<>()
                );
                return post;
            }
            return null;
        }, false);
    }
}