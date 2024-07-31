package resources;

import model.Comment;
import services.CommentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> find(@PathParam("id") Long id) {
        return commentService.allComments(id);
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void create(@PathParam("id") Long id, Comment comment){
        commentService.addComment(id, comment);
    }

}
