package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Optional;
import java.util.stream.Stream;

public class PsqlMain {

    public static void main(String[] args) {

        Store store = PsqlStore.instOf();

        store.save(new Post(1,"New Java Job"));
        store.save(new Candidate(1,"New Junior Developer"));

        store.save(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }

        store.save(new Candidate(0, "Junior Developer"));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
    }
}
