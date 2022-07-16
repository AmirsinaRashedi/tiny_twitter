package repository;

import entity.User;

public interface UserRepository {

    void save(User user);

    void read(Long id);

    void readAll();

    void delete(User user);

    void update(User user);

}
