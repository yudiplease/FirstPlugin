package com.yudiprojects.firstplugin.repository;

import com.yudiprojects.firstplugin.exceptions.PlayerNotFoundException;
import com.yudiprojects.firstplugin.model.PlayerEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class BasePlayerRepository implements PlayerRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL_PLAYERS = "select * from account;";

    //language=SQL
    private static final String SQL_INSERT_PLAYER = "insert into account(id, username, hash_password) " +
            "values (?, ?, ?);";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from account where id = ?;";

    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from account where id = ?;";

    //language=SQL
    private static final String SQL_SELECT_BY_NICKNAME = "select * from account where username = ?;";

    private static final Function<ResultSet, PlayerEntity> playerMapper = row -> {
        try {
            UUID id = row.getObject("id", UUID.class);
            String userName = row.getString("username");
            String password = row.getString("hash_password");
            return new PlayerEntity(id, userName, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    private final DataSource dataSource;

    public BasePlayerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<PlayerEntity> findAll() {
        List<PlayerEntity> players = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_PLAYERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                players.add(playerMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return players;
    }


    /**
     * Deletes the entity with the given id.
     *
     * @param uuid must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    @Override
    public void deleteById(UUID uuid) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setObject(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */


    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public void save(PlayerEntity entity) {
        String userName = entity.getUsername();
        String password = entity.getHashPassword();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PLAYER);
            statement.setObject(1, UUID.randomUUID());
            statement.setString(2, userName);
            statement.setString(3, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * Retrieves an entity by its id.
     *
     * @param uuid must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public Optional<PlayerEntity> findById(UUID uuid) {
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_BY_ID);
            while (resultSet.next()) {
                if (resultSet.getObject("id", UUID.class) == uuid) {
                    Optional<PlayerEntity> player = Optional.of(new PlayerEntity(uuid,
                            resultSet.getString("username"),
                            resultSet.getString("hash_password")));
                    return player;
                }
            }
        } catch (SQLException e) {
            throw new PlayerNotFoundException(uuid);
        }
        return Optional.empty();
    }

    @Override
    public Optional<PlayerEntity> findByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NICKNAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(name)) {
                    Optional<PlayerEntity> player = Optional.of(new PlayerEntity(
                            resultSet.getObject("id", UUID.class),
                            resultSet.getString("username"),
                            resultSet.getString("hash_password")));
                    return player;
                }
            }
        } catch (SQLException e) {
            throw new PlayerNotFoundException(name);
        }
        return Optional.empty();
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param uuid must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */


    /**
     * Flushes all pending changes to the database.
     */
}