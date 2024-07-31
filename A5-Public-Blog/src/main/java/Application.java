import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import repositories.abstraction.CommentRepository;
import repositories.abstraction.PostRepository;
import repositories.inmemory.InMemoryCommentRepository;
import repositories.inmemory.InMemoryPostRepository;
import services.CommentService;
import services.PostService;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Application extends ResourceConfig {

    public Application() {

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE,true);
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {

                this.bind(InMemoryPostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bind(InMemoryCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                this.bindAsContract(PostService.class);
                this.bindAsContract(CommentService.class);
            }
        };
        register(binder);
        packages("resources");
    }

}
