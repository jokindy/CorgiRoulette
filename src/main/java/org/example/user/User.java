package org.example.user;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private long id;
    private String name;
    private long groupId;
    private boolean isChosen;
    private LocalDateTime lastCompetition;

    public User(long id, String name, long groupId, boolean isChosen, LocalDateTime lastCompetition) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.isChosen = isChosen;
        this.lastCompetition = lastCompetition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getGroupId() == user.getGroupId() && Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getGroupId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groupId=" + groupId +
                ", isChosen=" + isChosen +
                ", lastCompetition=" + lastCompetition +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }


    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public LocalDateTime getLastCompetition() {
        return lastCompetition;
    }

    public void setLastCompetition(LocalDateTime lastCompetition) {
        this.lastCompetition = lastCompetition;
    }
}
