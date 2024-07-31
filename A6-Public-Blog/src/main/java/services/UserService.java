package services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import repositories.abstraction.UserRepository;

import javax.inject.Inject;
import java.util.Date;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public String login(String username, String password) {
        String hashedPassword = hashPassword(password);

        User user = this.userRepository.findUser(username);
        if (user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        return createJwtToken(username);
    }

    public String register(String username, String password) {
        User existingUser = this.userRepository.findUser(username);
        if (existingUser != null) {
            throw new RuntimeException("User with this username already exists");
        }
        String hashedPassword = hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        this.userRepository.addUser(user);

        return createJwtToken(username);
    }

    private String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    private String createJwtToken(String username) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return "Bearer " + JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(algorithm);
    }

}
