package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;

public class PsqlMain {

    public static void main(String[] args) {

        Store store = PsqlStore.instOf();

        /*store.save(new Post(0,"Junior Java Job"));
        store.save(new Post(0, "Middle Java Job"));
        store.save(new Post(0, "Senior Java Job"));

        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());

        }
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }*/
    }
}
