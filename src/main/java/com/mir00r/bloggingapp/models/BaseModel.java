package com.mir00r.bloggingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author mir00r on 2019-09-21
 * @project IntelliJ IDEA
 */
@MappedSuperclass()
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    protected Long id;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date createDate;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP NULL")
    private Date lastUpdate;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        BaseModel other = (BaseModel) obj;
        boolean comparedResult = !this.getId().equals(other.getId())
                && (this.getId() == null || !this.id.equals(other.id));

        return !comparedResult;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [ID=" + id + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
