package repositories.abstraction;

import model.User;

public interface UserRepository {

    void addUser(User user);
    User findUser(String username);
}
