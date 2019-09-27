package com.mir00r.bloggingapp.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
@Entity
@Table(name = "user_blog")
public class CommentBlog extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "id")
    private Blog blog;

    private String comment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
