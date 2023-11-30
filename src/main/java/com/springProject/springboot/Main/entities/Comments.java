package com.springProject.springboot.Main.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comments {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String email;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Column(columnDefinition = "TEXT")
        private String comment;
        private Date createdAt;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private Date updatedAt;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

}
