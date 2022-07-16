package service;

import entity.User;

public interface UserService {

    void save(User user);

    void read(Long id);

    void readAll();

    User getByUsername(String username);

    void delete(User user);

    void update(User user);

    User creatUser();

}
