package service.impl;

import entity.User;
import repository.UserRepository;
import service.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public void save(User user) {

        File userDatabase = new File("user_database.txt");

        if (userDatabase.isFile() && user != null && user.getId() != null && user.getUsername() != null && user.getFirstname() != null && user.getLastname() != null) {

            if (user.getId() > 0L && !user.getUsername().isBlank() && !user.getFirstname().isBlank() && !user.getLastname().isBlank()) {

                userRepository.save(user);

            } else
                System.out.println("invalid file or user");

        } else
            System.out.println("invalid file or user");

    }

    @Override
    public void read(Long id) {

        File databaseMetadata = new File("database_metadata.txt");

        if (databaseMetadata.isFile()) {

            try (Scanner metadataReader = new Scanner(databaseMetadata)) {

                String input = metadataReader.nextLine();

                String[] token = input.split(":");

                if (Long.parseLong(token[1]) > id) {

                    userRepository.read(id);

                } else {

                    System.out.println("this id does not exist");

                }


            } catch (FileNotFoundException ignored) {


            }

        } else
            System.out.println("invalid file or user");

    }

    @Override
    public void readAll() {

        File userDatabase = new File("user_database.txt");

        if (userDatabase.isFile())
            userRepository.readAll();
        else
            System.out.println("invalid file or user");

    }

    @Override
    public User getByUsername(String username) {

        File userDatabase = new File("user_database.txt");

        if (userDatabase.isFile() && username != null && !username.isBlank()) {

            return userRepository.getByUsername(username);

        } else
            System.out.println("invalid file or user");

        return null;

    }

    @Override
    public void delete(User user) {

        File userDatabase = new File("user_database.txt");

        if (userDatabase.isFile() && user != null && user.getId() != null && user.getUsername() != null && user.getFirstname() != null && user.getLastname() != null) {

            if (user.getId() > 0L && !user.getUsername().isBlank() && !user.getFirstname().isBlank() && !user.getLastname().isBlank()) {

                userRepository.delete(user);

            } else
                System.out.println("invalid file or user");

        } else
            System.out.println("invalid file or user");

    }

    @Override
    public void update(User user) {

        File userDatabase = new File("user_database.txt");

        if (userDatabase.isFile() && user != null && user.getId() != null && user.getUsername() != null && user.getFirstname() != null && user.getLastname() != null) {

            if (user.getId() > 0L && !user.getUsername().isBlank() && !user.getFirstname().isBlank() && !user.getLastname().isBlank()) {

                userRepository.update(user);

            } else
                System.out.println("invalid file or user");

        } else
            System.out.println("invalid file or user");

    }

    @Override
    public User creatUser() {

        File databaseMetadata = new File("database_metadata.txt");

        File userDatabase = new File("user_database.txt");

        User newUser = new User();

        try (Scanner metadataReader = new Scanner(databaseMetadata)) {

            String input = metadataReader.nextLine();

            String[] token = input.split(":");

            newUser.setId(Long.parseLong(token[1]));

        } catch (FileNotFoundException ignored) {

            System.out.println("file not found");

            return null;

        }

        try (Scanner stringInput = new Scanner(System.in)) {

            System.out.print("enter your username:");

            newUser.setUsername(stringInput.nextLine());

            System.out.print("enter your firstname:");

            newUser.setFirstname(stringInput.nextLine());

            System.out.print("enter your lastname:");

            newUser.setLastname(stringInput.nextLine());

        } catch (Exception e) {

            System.out.println("error");

            return null;

        }

        save(newUser);

        File tempDatabaseMetadata = new File("temp_database_metadata.txt");

        boolean firstLine = true;

        try (Scanner metadataReader = new Scanner(databaseMetadata)) {

            try (FileWriter tempMetadataWriter = new FileWriter(tempDatabaseMetadata, true)) {

                while (metadataReader.hasNextLine()) {

                    String input = metadataReader.nextLine();

                    if (firstLine) {

                        firstLine = false;

                        String[] token = input.split(":");

                        long newId = (Long.parseLong(token[1]) + 1L);

                        token[1] = Long.toString(newId);

                        input = token[0].concat(":".concat(token[1]));

                    }

                    tempMetadataWriter.write(input + "\n");

                }


            } catch (IOException ignored) {

            }

        } catch (FileNotFoundException ignored) {

        }

        databaseMetadata.delete();

        tempDatabaseMetadata.renameTo(databaseMetadata);

        return newUser;

    }
}
