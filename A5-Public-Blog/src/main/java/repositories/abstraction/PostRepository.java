package repositories.abstraction;

import model.Post;

import java.util.List;

public interface PostRepository {

    void addPost(Post post);
    List<Post> allPosts();
    Post findPost(Long id);
}
