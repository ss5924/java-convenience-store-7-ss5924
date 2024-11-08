package store.membership;

import java.util.concurrent.ConcurrentHashMap;

public class MembershipManager {
    private static final int MAX_LIMIT = 8000;
    private final ConcurrentHashMap<String, Membership> membership = new ConcurrentHashMap<>();

    public Membership getMembership(String userId) {
        return membership.computeIfAbsent(userId, id -> new Membership(MAX_LIMIT));
    }

}
