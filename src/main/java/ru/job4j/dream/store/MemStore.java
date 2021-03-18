package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {

    private static final MemStore INST = new MemStore();

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Map<Integer, User> users = new ConcurrentHashMap<>();

    private Map<Integer, String> cities = new ConcurrentHashMap<>();

    private static AtomicInteger ID_Supplier = new AtomicInteger(4);

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate.Builder().setId(1).setName("Артем").setCity_id(2).build());
        candidates.put(2, new Candidate.Builder().setId(2).setName("Андрей").setCity_id(4).build());
        candidates.put(3, new Candidate.Builder().setId(3).setName("Антон").setCity_id(5).build());
        users.put(1, new User(1, "Admin", "root@local", "root"));
        cities.put(1, "Москва");
        cities.put(2, "Нижний Новгород");
        cities.put(3, "Волгоград");
        cities.put(4, "Краснодар");
        cities.put(5, "Уфа");
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public Map<Integer, String> findAllCities() {
        return cities;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(ID_Supplier.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(ID_Supplier.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(ID_Supplier.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public void savePhoto(int photoId) {
        candidates.get(photoId).setPhotoId(photoId);
    }

    @Override
    public Post findPostById(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public User findUserByEmail(String email) {
        User userReturn = null;
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                userReturn = user;
            }
        }
        return userReturn;
    }

    @Override
    public void deleteCandidate(int id) {
        candidates.remove(id);
    }

    @Override
    public void deletePost(int id) {
        posts.remove(id);
    }
}
