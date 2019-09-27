package com.mir00r.bloggingapp.utils;

/**
 * @author mir00r on 2019-09-21
 * @project IntelliJ IDEA
 */
public class Constant {
    public static final String USERS = "users";
    public static final String ROLES = "roles";
    public static final String MODE = "mode";
    public static final String BLOG = "blogType";

    public enum ATTRIBUTE_NAME {
        rule(1, "rule"), auth(2, "auth"), control(3, "control");
        private final long id;
        private final String name;

        ATTRIBUTE_NAME(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public enum ROLE_TYPE {
        admin(1L, "ADMIN"), blogger(2L, "BLOGGER");
        private final long roleId;
        private final String roleName;

        ROLE_TYPE(long roleId, String roleName) {
            this.roleId = roleId;
            this.roleName = roleName;
        }

        public long getRoleId() {
            return roleId;
        }

        public String getRoleName() {
            return roleName;
        }
    }

    public enum ACTION_MODE {
        newMode(1, "MODE_NEW"), allMode(2, "MODE_ALL"),
        updateMode(3, "MODE_UPDATE"), infoMode(4, "MODE_INF"),
        detailsMode(5, "MODE_DETAILS"), passMode(6, "MODE_PASS"),
        editMode(7, "MODE_EDIT");
        private final long Id;
        private final String Name;

        ACTION_MODE(long id, String name) {
            Id = id;
            Name = name;
        }

        public long getId() {
            return Id;
        }

        public String getName() {
            return Name;
        }
    }

    public enum BLOG_TYPE {
        mine(1, "mine"), other(2, "other"), all(3, "all");
        private final long Id;
        private final String name;

        BLOG_TYPE(long id, String name) {
            Id = id;
            this.name = name;
        }

        public long getId() {
            return Id;
        }

        public String getName() {
            return name;
        }
    }
}
