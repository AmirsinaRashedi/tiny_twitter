package repository.impl;

import entity.User;
import repository.UserRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(User user) {

        File userDatabase = new File("user_database.txt");

        try (FileWriter userWriter = new FileWriter(userDatabase, true)) {

            userWriter.write(user.getUserDefinitionForFile() + "\n");

        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    @Override
    public void read(Long id) {

        File userDatabase = new File("user_database.txt");

        Scanner userDatabaseReader;

        try {

            userDatabaseReader = new Scanner(userDatabase);

        } catch (FileNotFoundException e) {

            System.out.println("file not found");

            return;

        }

        boolean foundFlag = false;

        while (userDatabaseReader.hasNextLine()) {

            String input = userDatabaseReader.nextLine();

            String[] token = input.split("-");

            if (Long.parseLong(token[0]) == id) {

                System.out.println(input);

                foundFlag = true;

            }

            if (foundFlag)
                break;

        }

        if (!foundFlag)
            System.out.println("this id does not exist");

        userDatabaseReader.close();

    }

    @Override
    public void readAll() {

        File userDatabase = new File("user_database.txt");

        Scanner userDatabaseReader;

        try {

            userDatabaseReader = new Scanner(userDatabase);

        } catch (FileNotFoundException e) {

            System.out.println("file not found");

            return;

        }

        while (userDatabaseReader.hasNextLine()) {

            System.out.println(userDatabaseReader.nextLine());

        }

    }

    @Override
    public void delete(User user) {

        File userDatabase = new File("user_database.txt");

        File tempUserDatabase = new File("temp_user_database.txt");

        Scanner userDatabaseReader;

        try {

            userDatabaseReader = new Scanner(userDatabase);

        } catch (FileNotFoundException e) {

            System.out.println("database file not found");

            return;

        }

        FileWriter newUserDatabaseWriter;

        try {

            newUserDatabaseWriter = new FileWriter(tempUserDatabase);

        } catch (IOException e) {

            System.out.println("error");

            return;

        }

        boolean foundFlag = false;

        while (userDatabaseReader.hasNextLine()) {

            String input = userDatabaseReader.nextLine();

            String[] token = input.split("-");

            if (Long.parseLong(token[0]) == user.getId()) {

                foundFlag = true;

            } else {

                try {

                    newUserDatabaseWriter.write(input + "\n");

                } catch (IOException e) {

                    System.out.println("error");

                    userDatabaseReader.close();

                    try {

                        newUserDatabaseWriter.close();

                    } catch (IOException ignored) {

                    }

                    tempUserDatabase.delete();

                    return;
                }

            }

        }

        userDatabaseReader.close();

        try {

            newUserDatabaseWriter.close();

        } catch (IOException ignored) {

        }

        if (foundFlag) {

            userDatabase.delete();

            tempUserDatabase.renameTo(userDatabase);

        } else {

            System.out.println("this user does not exist");

            tempUserDatabase.delete();

        }

    }

    @Override
    public void update(User user) {

        File userDatabase = new File("user_database.txt");

        File tempUserDatabase = new File("temp_user_database.txt");

        Scanner userDatabaseReader;

        try {

            userDatabaseReader = new Scanner(userDatabase);

        } catch (FileNotFoundException e) {

            System.out.println("database file not found");

            return;

        }

        FileWriter newUserDatabaseWriter;

        try {

            newUserDatabaseWriter = new FileWriter(tempUserDatabase);

        } catch (IOException e) {

            System.out.println("error");

            return;

        }

        boolean foundFlag = false;

        while (userDatabaseReader.hasNextLine()) {

            String input = userDatabaseReader.nextLine();

            String[] token = input.split("-");

            if (Long.parseLong(token[0]) == user.getId()) {

                try {

                    newUserDatabaseWriter.write(user.getUserDefinitionForFile() + "\n");

                } catch (IOException ignored) {

                }

                foundFlag = true;

            } else {

                try {

                    newUserDatabaseWriter.write(input + "\n");

                } catch (IOException e) {

                    System.out.println("error");

                    userDatabaseReader.close();

                    try {

                        newUserDatabaseWriter.close();

                    } catch (IOException ignored) {

                    }

                    tempUserDatabase.delete();

                    return;
                }

            }

        }

        userDatabaseReader.close();

        try {

            newUserDatabaseWriter.close();

        } catch (IOException ignored) {

        }

        if (foundFlag) {

            userDatabase.delete();

            tempUserDatabase.renameTo(userDatabase);

        } else {

            System.out.println("this user does not exist");

            tempUserDatabase.delete();
        }

    }
}
