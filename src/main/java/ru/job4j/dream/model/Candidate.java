package ru.job4j.dream.model;

import java.util.Objects;

public class Candidate {

    private int id;
    private String name;
    private int photoId;
    private int city_id;

    public Candidate(int id, String name, int photoId, int city_id) {
        this.id = id;
        this.name = name;
        this.photoId = photoId;
        this.city_id = city_id;
    }

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id
                && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static class Builder {
        private int id;
        private String name;
        private int photoId;
        private int city_id;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPhotoId(int photoId) {
            this.photoId = photoId;
            return this;
        }

        public Builder setCity_id(int city_id) {
            this.city_id = city_id;
            return this;
        }

        public Candidate build() {
            return new Candidate(id, name, photoId, city_id);
        }
    }
}
