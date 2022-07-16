package repository;

import entity.User;

public interface UserRepository {

    void save(User user);

    void read(Long id);

    void readAll();

    User getByUsername(String username);

    void delete(User user);

    void update(User user);

}
