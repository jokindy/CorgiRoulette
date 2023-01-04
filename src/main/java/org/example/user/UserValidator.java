package org.example.user;

import java.time.Duration;
import java.time.LocalDateTime;

public class UserValidator {

    private static final int INTERVAL = 7;

    public boolean validatePair(User u1, User u2) {
        Duration duration1 = Duration.between(u1.getLastCompetition(), LocalDateTime.now());
        long durationInDays1 = duration1.toDays();
        Duration duration2 = Duration.between(u2.getLastCompetition(), LocalDateTime.now());
        long durationInDays2 = duration2.toDays();

        boolean isUserOneAvailable = false;
        if (!u1.isChosen()) {
            isUserOneAvailable = true;
        } else if (u1.isChosen() && durationInDays1 >= INTERVAL) {
            isUserOneAvailable = true;
        }

        boolean isUserTwoAvailable = false;
        if (!u2.isChosen()) {
            isUserTwoAvailable = true;
        } else if (u2.isChosen() && durationInDays2 >= INTERVAL) {
            isUserTwoAvailable = true;
        }

        return isUserOneAvailable && isUserTwoAvailable;
    }
}