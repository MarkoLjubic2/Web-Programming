package services;

import model.Post;
import repositories.abstraction.PostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostService {

    @Inject
    private PostRepository postRepository;

    public Post addPost(Post post){
        postRepository.addPost(post);
        return post;
    }

    public List<Post> allPosts(){
        return postRepository.allPosts();
    }

    public Post findPost(Long id){
        return postRepository.findPost(id);
    }
}
