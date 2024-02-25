package com.example.studyPlanner.board.repository;

import com.example.studyPlanner.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Board> findAll() {
        String sql = "SELECT * FROM board";
        return jdbcTemplate.query(sql, boardRowMapper());
    }

    public Optional<Board> findById(Long id) {
        try {
            Board board = jdbcTemplate.queryForObject(
                    "SELECT * FROM board WHERE board_id = ?",
                    boardRowMapper(),
                    new Object[]{id}
            );
            return Optional.ofNullable(board);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Board save(Board board) {
        if (board.getId() == null) {
            // Insert new contact
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO board (name) VALUES (?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                        ps.setString(1, board.getName());
                        return ps;
                    },
                    keyHolder
            );
            Number key = keyHolder.getKey();
            if (key != null) {
                board.setId(key.longValue());
            } else {
                throw new RuntimeException("Failed to generate contactId for new contact");
            }

        } else {
            // Update existing contact
            jdbcTemplate.update(
                    "UPDATE board SET name = ? WHERE board_id = ?",
                    board.getName(), board.getId()
            );
        }
        return board;
    }

    public void delete(Board board) {
        jdbcTemplate.update(
                "DELETE FROM board WHERE board_id = ?",
                board.getId()
        );
    }

    public Optional<Board> findByName(String name) {
        String sql = "SELECT * FROM board WHERE name = ?";
        Object[] params = {name};

        return jdbcTemplate.query(sql, params, boardRowMapper())
                .stream()
                .findFirst();
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getLong("board_id"));
            board.setName(rs.getString("name"));
            return board;
        };
    }
}
