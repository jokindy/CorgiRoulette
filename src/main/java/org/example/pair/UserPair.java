package org.example.pair;

import org.example.user.User;

public class UserPair {

    private User user1;

    private User user2;

    public UserPair() {
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public UserPair(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "UserPair{" +
                "user1=" + user1 +
                ", user2=" + user2 +
                '}';
    }
}
