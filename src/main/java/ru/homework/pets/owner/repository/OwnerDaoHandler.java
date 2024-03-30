package ru.homework.pets.owner.repository;

import ru.homework.pets.owner.model.Owner;
import ru.homework.pets.pet.model.Pet;

import java.sql.*;
import java.util.*;

public class OwnerDaoHandler implements OwnerRepository {
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

    public void save(Owner owner) throws SQLException {
        Connection connect = OwnerDaoHandler.connectDB();
        try {
            connect.setAutoCommit(false);

            PreparedStatement preparedStatement = connect.prepareStatement(
                    "insert into owners(name) values (?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, owner.getName());
            preparedStatement.executeUpdate();

            ResultSet ownerId = preparedStatement.getGeneratedKeys();
            if (ownerId.next()) {
                int newId = ownerId.getInt(1);
                owner.setId(newId);
            }

            PreparedStatement preparedStatement2 = connect.prepareStatement(
                    "insert into pets_for_owner(id_owner,id_pet) values (?,?)");

            for (int i = 0; i < owner.getPets().size(); i++) {
                preparedStatement2.setInt(1, owner.getId());
                preparedStatement2.setInt(2, owner.getPets().get(i).getId());
                preparedStatement2.addBatch();
            }
            preparedStatement2.executeBatch();

            connect.commit();
            connect.setAutoCommit(true);
        } catch (Exception e) {
            connect.rollback();
        } finally {
            connect.close();
        }
    }

    public Owner getById(int id) throws SQLException {
        Owner owner = new Owner();
        List<Integer> petsIds = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();
        Connection connect = OwnerDaoHandler.connectDB();
        try {
            connect.setAutoCommit(false);

            PreparedStatement preparedStatement
                    = connect.prepareStatement(
                    "select * from owners where id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet
                    = preparedStatement.executeQuery();

            if (resultSet.next()) {
                owner.setId(resultSet.getInt(1));
                owner.setName(resultSet.getString(2));
            }

            PreparedStatement preparedStatement2 = connect.prepareStatement(
                    "select id_pet from pets_for_owner where id_owner=?");

            preparedStatement2.setInt(1, owner.getId());
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            int petId = 0;

            while (resultSet2.next()) {
                petId = resultSet2.getInt(1);
                petsIds.add(petId);
            }

            PreparedStatement preparedStatement3 = connect.prepareStatement(
                    "select * from pets where id = any (?)");

            Array array = preparedStatement3.getConnection().createArrayOf("BIGINT", petsIds.toArray());
            preparedStatement3.setArray(1, array);
            ResultSet resultSet3 = preparedStatement3.executeQuery();

            while (resultSet3.next()) {
                Pet pet = new Pet();
                pet.setId(resultSet3.getInt(1));
                pet.setType(resultSet3.getString(2));
                pet.setName(resultSet3.getString(3));
                pet.setAge(resultSet3.getInt(4));
                pets.add(pet);
            }
            owner.setPets(pets);

            connect.commit();
            connect.setAutoCommit(true);
        } catch (Exception e) {
            connect.rollback();
        } finally {
            connect.close();
        }
        return owner;
    }

    public List<Owner> getAll() throws SQLException {
        HashMap<Integer, Owner> owners = new HashMap<>();
        HashMap<Integer, Pet> pets = new HashMap<>();
        HashMap<Owner, List<Pet>> petsForOwner= new HashMap<>();
        List<Owner> result = new ArrayList<>();
        Connection connect = OwnerDaoHandler.connectDB();
        try {
            connect.setAutoCommit(false);

            PreparedStatement preparedStatement = connect.prepareStatement(
                "select * from owners");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Owner owner = new Owner();
                owner.setId(resultSet.getInt(1));
                owner.setName(resultSet.getString(2));
                owners.put(owner.getId(), owner);
            }

            PreparedStatement preparedStatement1 = connect.prepareStatement(
                    "select * from pets");

            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while (resultSet1.next()) {
                Pet pet = new Pet();
                pet.setId(resultSet1.getInt(1));
                pet.setType(resultSet1.getString(2));
                pet.setName(resultSet1.getString(3));
                pet.setAge(resultSet1.getInt(4));
                pets.put(pet.getId(), pet);
            }

            PreparedStatement preparedStatement2 = connect.prepareStatement(
                    "select * from pets_for_owner");

            ResultSet resultSet2 = preparedStatement2.executeQuery();
            int petId = 0;
            int ownerId = 0;

            while (resultSet2.next()) {
                ownerId = resultSet2.getInt(1);
                petId = resultSet2.getInt(2);
                List<Pet> petList = petsForOwner.getOrDefault(owners.get(ownerId), new ArrayList<>());
                petList.add(pets.get(petId));
                petsForOwner.put(owners.get(ownerId), petList);
            }

            for (Map.Entry<Owner, List<Pet>> entry : petsForOwner.entrySet()) {
                Owner owner = entry.getKey();
                owner.setPets(entry.getValue());
                result.add(owner);
            }
            connect.commit();
            connect.setAutoCommit(true);
        } catch (Exception e) {
            connect.rollback();
        } finally {
            connect.close();
        }
        return result;
    }

    public void delete(int id) throws SQLException {
        Connection connect = OwnerDaoHandler.connectDB();
        try {
            connect.setAutoCommit(false);

            PreparedStatement preparedStatement = connect.prepareStatement(
                    "delete from pets_for_owner where id_owner=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connect.prepareStatement(
                "delete from owners where id =?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();

            connect.commit();
            connect.setAutoCommit(true);
        } catch (Exception e) {
            connect.rollback();
        } finally {
            connect.close();
        }
    }

    public void update(int id, Owner owner) throws SQLException {
        Connection connect = OwnerDaoHandler.connectDB();
        try {
            connect.setAutoCommit(false);

            PreparedStatement preparedStatement = connect.prepareStatement(
                "update owners set name=? where id=?");

            preparedStatement.setString(1, owner.getName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connect.prepareStatement(
                    "delete from pets_for_owner where id_owner=?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();

            PreparedStatement preparedStatement3 = connect.prepareStatement(
                    "insert into pets_for_owner(id_owner,id_pet) values (?,?)");

            for (int i = 0; i < owner.getPets().size(); i++) {
                preparedStatement3.setInt(1, id);
                preparedStatement3.setInt(2, owner.getPets().get(i).getId());
                preparedStatement3.addBatch();
            }
            preparedStatement3.executeBatch();

            connect.commit();
            connect.setAutoCommit(true);
        } catch (Exception e) {
            connect.rollback();
        } finally {
            connect.close();
        }
    }
}
