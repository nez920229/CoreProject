package com.boco.app.core.auth;

import java.util.List;

/**
 * Created by walle on 2017/9/19.
 */
public class UserInfo {
    public List<String> roles;
    public List<Organization> organizations;
    public LoginUser user;
    public static class Organization{
        public String id;
        public String level;
        public String name;
        public String priorityl;

        @Override
        public String toString() {
            return "Organization{" +
                    "id='" + id + '\'' +
                    ", level='" + level + '\'' +
                    ", name='" + name + '\'' +
                    ", priorityl='" + priorityl + '\'' +
                    '}';
        }
    }
    public static class LoginUser{
        public String organizationId;
        public String lastLoginTime;
        public String realName;
        public String headAddress;
        public String address;
        public String organizationName;
        public String gender;
        public int lockedFlag;
        public String displayName;
        public String name;
        public String priority;
        public String idNumber;

        @Override
        public String toString() {
            return "LoginUser{" +
                    "organizationId='" + organizationId + '\'' +
                    ", lastLoginTime='" + lastLoginTime + '\'' +
                    ", realName='" + realName + '\'' +
                    ", headAddress='" + headAddress + '\'' +
                    ", address='" + address + '\'' +
                    ", organizationName='" + organizationName + '\'' +
                    ", gender='" + gender + '\'' +
                    ", lockedFlag=" + lockedFlag +
                    ", displayName='" + displayName + '\'' +
                    ", name='" + name + '\'' +
                    ", priority='" + priority + '\'' +
                    ", idNumber='" + idNumber + '\'' +
                    '}';
        }
    }
    public String token;

    @Override
    public String toString() {
        return "UserInfo{" +
                "roles=" + roles +
                ", organizations=" + organizations +
                ", user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
