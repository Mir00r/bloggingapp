package com.mir00r.bloggingapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
@Entity
@Table(name = "blog")
public class Blog extends BaseModel {
    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private int numberOfLike;
    private int blogStatus;
    private Long blogViews;
    private boolean enableComment;
    private boolean isDeleted;

    @ManyToOne
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfLike() {
        return numberOfLike;
    }

    public void setNumberOfLike(int numberOfLike) {
        this.numberOfLike = numberOfLike;
    }

    public int getBlogStatus() {
        return blogStatus;
    }

    public void setBlogStatus(int blogStatus) {
        this.blogStatus = blogStatus;
    }

    public Long getBlogViews() {
        return blogViews;
    }

    public void setBlogViews(Long blogViews) {
        this.blogViews = blogViews;
    }

    public boolean isEnableComment() {
        return enableComment;
    }

    public void setEnableComment(boolean enableComment) {
        this.enableComment = enableComment;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}