package com.techcourse.dao;

import com.techcourse.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final User user) {
        final String sql = "insert into users (account, password, email) values (?, ?, ?)";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getEmail());
    }

    public void update(final User user) {
        final String sql = "update users set account=?, password=?, email=? where id=?";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getEmail(), user.getId());
    }

    public List<User> findAll() {
        final String sql = "select * from users";
        return jdbcTemplate.query(sql, getUserRowMapper());
    }

    private RowMapper<User> getUserRowMapper() {
        return ((resultSet, rowNum) ->
                new User(
                        resultSet.getLong("id"),
                        resultSet.getString("account"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                )
        );
    }

    public Optional<User> findById(final Long id) {
        final String sql = "select id, account, password, email from users where id = ?";
        return jdbcTemplate.queryForObject(sql, getUserRowMapper(), id);
    }

    public Optional<User> findByAccount(final String account) {
        final String sql = "select * from users where account = ?";
        return jdbcTemplate.queryForObject(sql, getUserRowMapper(), account);
    }

}
