package org.example.pair;

import org.example.user.User;
import org.example.user.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PairGenerator {

    private static final Random RANDOM = new Random();

    private final PairRepository pairRepository;
    private final UserRepository userRepository;

    public PairGenerator() {
        this.pairRepository = new PairRepository();
        this.userRepository = new UserRepository();
        generatePairs();
    }

    public Pair getPair() {
        List<Pair> pairs = pairRepository.getPairs();
        Set<Integer> tries = new HashSet<>();
        while (tries.size() != pairs.size()) {
            int anotherTry = RANDOM.nextInt(pairs.size());
            Pair pair = pairs.get(anotherTry);
            if (userRepository.checkPair(pair)) {
                userRepository.handlePair(pair);
                return pair;
            }
            tries.add(anotherTry);
        }
        throw new RuntimeException("All users already answered today");
    }

    public void generatePairs() {
        List<User> users = userRepository.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            for (int j = users.size() - 1; j > 0; j--) {
                User u1 = users.get(i);
                User u2 = users.get(j);
                if (u1.getGroupId() != u2.getGroupId()) {
                    Pair pair = new Pair(u1.getId(), u2.getId());
                    if (!pairRepository.checkPair(pair)) {
                        pairRepository.handlePair(pair);
                    }
                }
            }
        }
    }

    public void refresh() {
        userRepository.refresh();
    }
}