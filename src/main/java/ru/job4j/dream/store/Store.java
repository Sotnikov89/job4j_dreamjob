package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    int savePhotoReturnId(String name);

    String getPhotoNameById (int id);

    void deleteCandidateAndHisPhotoById(int candidate_id, int photo_id);

    User findUserByEmail(String email);

    User save(User user);

    void deleteUserById(int id);
}
