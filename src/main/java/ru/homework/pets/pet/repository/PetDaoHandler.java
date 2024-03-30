package ru.homework.pets.pet.repository;

import ru.homework.pets.pet.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PetDaoHandler implements PetRepository {
    public static Connection connectDB() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/petsdb",
                    "pet", "1234");
        }

        catch (Exception message) {
            System.out.println(message);
        }
        return connection;
    }

    public void save(Pet pet) throws SQLException {
        Connection connect = PetDaoHandler.connectDB();

        PreparedStatement preparedStatement
                = connect.prepareStatement(
                "insert into pets(type,name,age) values (?,?,?)");

        preparedStatement.setString(1, pet.getType());
        preparedStatement.setString(2, pet.getName());
        preparedStatement.setInt(3, pet.getAge());
        preparedStatement.executeUpdate();

        connect.close();
    }

    public Pet getById(int id) throws SQLException {
        Pet pet = new Pet();

        Connection connect = PetDaoHandler.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "select * from pets where id=?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet
                = preparedStatement.executeQuery();

        if (resultSet.next()) {
            pet.setId(resultSet.getInt(1));
            pet.setType(resultSet.getString(2));
            pet.setName(resultSet.getString(3));
            pet.setAge(resultSet.getInt(4));
        }
        connect.close();
        return pet;
    }

    public List<Pet> getAll() throws SQLException {
        List<Pet> list = new ArrayList<>();

        Connection connect = PetDaoHandler.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "select * from pets");
        ResultSet resultSet
                = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Pet pet = new Pet();
            pet.setId(resultSet.getInt(1));
            pet.setType(resultSet.getString(2));
            pet.setName(resultSet.getString(3));
            pet.setAge(resultSet.getInt(4));
            list.add(pet);
        }
        connect.close();
        return list;
    }

    public void delete(int id) throws SQLException {
        Connection connect = PetDaoHandler.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "delete from pets where id =?");

        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();

        connect.close();
    }

    public void update(int id, Pet pet) throws SQLException {
        Connection connect = PetDaoHandler.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "update pets set type=?,name=?,age=? where id=?");

        preparedStatement.setString(1, pet.getType());
        preparedStatement.setString(2, pet.getName());
        preparedStatement.setInt(3, pet.getAge());
        preparedStatement.setInt(4, id);

        preparedStatement.executeUpdate();

        connect.close();

    }

    public List<Pet> getPetsByIds(List<Integer> idsPets) throws SQLException {
        List<Pet> list = new ArrayList<>();

        Connection connect = PetDaoHandler.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "select * from pets where id = any (?)");
        Array ids = connect.createArrayOf("bigint", idsPets.toArray());
        preparedStatement.setArray(1, ids);
        ResultSet resultSet
                = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Pet pet = new Pet();
            pet.setId(resultSet.getInt(1));
            pet.setType(resultSet.getString(2));
            pet.setName(resultSet.getString(3));
            pet.setAge(resultSet.getInt(4));
            list.add(pet);
        }

        connect.close();
        return list;
    }
}
