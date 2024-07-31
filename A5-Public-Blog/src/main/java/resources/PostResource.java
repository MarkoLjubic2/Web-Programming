package resources;

import model.Post;
import services.PostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/posts")
public class PostResource {
    @Inject
    private PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){
        return Response.ok(postService.allPosts()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post find(@PathParam("id") Long id){
        return postService.findPost(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Post create(Post post, @Context HttpHeaders headers){
        return postService.addPost(post);
    }
}
