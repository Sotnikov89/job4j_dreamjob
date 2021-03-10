package ru.job4j.dream.store;

import com.google.common.io.Files;
import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store{

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(new FileReader("db.properties"))) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"), it.getInt("photo_id")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
        try (Connection cn = pool.getConnection(); PreparedStatement ps =  cn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePhoto(int photoId) {
        String sql = "UPDATE candidate " + "SET photoid = ? " + "WHERE id = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps =  cn.prepareStatement(sql)) {
            ps.setInt(1, photoId);
            ps.setInt(2, photoId);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public Post findPostById(int id) {
        String sql = "SELECT name FROM post WHERE id = ?";
        return new Post(id, getNameById(sql, id));
    }

    @Override
    public Candidate findCandidateById(int id) {
        String sql = "SELECT name FROM candidate WHERE id = ?";
        return new Candidate(id, getNameById(sql,id), id);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps =  cn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    user.setId(it.getInt("id"));
                    user.setName(it.getString("name"));
                    user.setEmail(it.getString("email"));
                    user.setPassword(it.getString("password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void deleteCandidate(int id) {
        String sql = "DELETE FROM candidate WHERE id = ?";
        try(Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            for (File file : new File("C:\\Users\\Вадим\\Desktop\\images\\").listFiles()) {
                if (Integer.toString(id).equals(Files.getNameWithoutExtension(file.getName()))) {
                    file.delete();
                    break;
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)")
        ) {
            ps.setString(1, post.getName());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name) VALUES (?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Post post) {
        String sql = "UPDATE post " + "SET name = ? " + "WHERE id = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps =  cn.prepareStatement(sql)) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void update(Candidate candidate) {
        String sql = "UPDATE candidate " + "SET name = ?" + "WHERE id = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps =  cn.prepareStatement(sql)) {
            ps.setString(1, candidate.getName());
            ps.setInt(3, candidate.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private String getNameById(String sql, int id) {
        String name = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    name = it.getString("name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
