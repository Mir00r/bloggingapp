package com.mir00r.bloggingapp.utils;

/**
 * @author mir00r on 2019-09-21
 * @project IntelliJ IDEA
 */
public class Constant {
    public static final String USERS = "users";
    public static final String ROLES = "roles";

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
}
