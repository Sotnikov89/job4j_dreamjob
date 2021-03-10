package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;



public class PsqlMain {

    public static void main(String[] args) {

        Store store = PsqlStore.instOf();
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getName());
        }
    }
}
