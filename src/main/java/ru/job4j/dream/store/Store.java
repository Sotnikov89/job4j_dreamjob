package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;

public interface Store {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Map<Integer, String> findAllCities();

    void save(Post post);

    void save(Candidate candidate);

    void save(User user);

    void savePhoto(int photoId);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    User findUserByEmail(String email);

    void deleteCandidate(int id);

    void deletePost(int id);

}
