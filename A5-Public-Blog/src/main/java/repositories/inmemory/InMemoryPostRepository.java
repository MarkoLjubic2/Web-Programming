package repositories.inmemory;

import lombok.Getter;
import model.Post;
import repositories.abstraction.PostRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class InMemoryPostRepository implements PostRepository {

    private static final List<Post> posts = new CopyOnWriteArrayList<>();

    @Override
    public void addPost(Post post) {
        post.setId((long) posts.size());
        posts.add(post);
    }

    @Override
    public List<Post> allPosts() {
        return posts;
    }

    @Override
    public Post findPost(Long id) {
        return posts.get(id.intValue());
    }
}
