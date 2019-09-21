package com.mir00r.bloggingapp.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author mir00r on 2019-09-20
 * @project IntelliJ IDEA
 */
@Entity
@Table(name = "role")
public class Role extends BaseModel {
    private static final long serialVersionUID = 1L;

    private String name;
    private String menuIds;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
