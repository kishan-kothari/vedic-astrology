package com.astrology.core;

public class PlanetaryRelationship {

    public enum Relationship {
        GREAT_FRIEND, FRIEND, NEUTRAL, ENEMY, GREAT_ENEMY
    }

    public static Relationship getNaturalRelationship(Planet from, Planet to) {
        if (from == to) return Relationship.NEUTRAL; // Or own, but usually neutral in this context.
        return switch (from) {
            case SUN -> (to == Planet.MOON || to == Planet.MARS || to == Planet.JUPITER) ? Relationship.FRIEND
                    : (to == Planet.VENUS || to == Planet.SATURN) ? Relationship.ENEMY
                    : Relationship.NEUTRAL;
            case MOON -> (to == Planet.SUN || to == Planet.MERCURY) ? Relationship.FRIEND
                    : Relationship.NEUTRAL; // Moon has no natural enemies
            case MARS -> (to == Planet.SUN || to == Planet.MOON || to == Planet.JUPITER) ? Relationship.FRIEND
                    : (to == Planet.MERCURY) ? Relationship.ENEMY
                    : Relationship.NEUTRAL;
            case MERCURY -> (to == Planet.SUN || to == Planet.VENUS) ? Relationship.FRIEND
                    : (to == Planet.MOON) ? Relationship.ENEMY
                    : Relationship.NEUTRAL;
            case JUPITER -> (to == Planet.SUN || to == Planet.MOON || to == Planet.MARS) ? Relationship.FRIEND
                    : (to == Planet.MERCURY || to == Planet.VENUS) ? Relationship.ENEMY
                    : Relationship.NEUTRAL;
            case VENUS -> (to == Planet.MERCURY || to == Planet.SATURN) ? Relationship.FRIEND
                    : (to == Planet.SUN || to == Planet.MOON) ? Relationship.ENEMY
                    : Relationship.NEUTRAL;
            case SATURN -> (to == Planet.MERCURY || to == Planet.VENUS) ? Relationship.FRIEND
                    : (to == Planet.SUN || to == Planet.MOON || to == Planet.MARS) ? Relationship.ENEMY
                    : Relationship.NEUTRAL;
            default -> Relationship.NEUTRAL;
        };
    }

    public static Relationship getTemporaryRelationship(int houseFrom) {
        if (houseFrom == 2 || houseFrom == 3 || houseFrom == 4 || houseFrom == 10 || houseFrom == 11 || houseFrom == 12) {
            return Relationship.FRIEND;
        }
        return Relationship.ENEMY;
    }

    public static Relationship getCombinedRelationship(Planet from, Planet to, int houseFrom) {
        Relationship natural = getNaturalRelationship(from, to);
        Relationship temporary = getTemporaryRelationship(houseFrom);

        if (natural == Relationship.FRIEND && temporary == Relationship.FRIEND) return Relationship.GREAT_FRIEND;
        if (natural == Relationship.ENEMY && temporary == Relationship.ENEMY) return Relationship.GREAT_ENEMY;
        if ((natural == Relationship.FRIEND && temporary == Relationship.ENEMY) ||
            (natural == Relationship.ENEMY && temporary == Relationship.FRIEND)) return Relationship.NEUTRAL;
        if (natural == Relationship.NEUTRAL && temporary == Relationship.FRIEND) return Relationship.FRIEND;
        if (natural == Relationship.NEUTRAL && temporary == Relationship.ENEMY) return Relationship.ENEMY;

        return Relationship.NEUTRAL; // fallback
    }

    public static double getSaptavargiaBalaValue(Relationship rel) {
        return switch (rel) {
            case GREAT_FRIEND -> 45.0;
            case FRIEND -> 22.5; // Moolatrikona is 30, but own is 22.5, using 22.5 for friend? The spec says: Friend=30(Moolatrikona)/22.5(Own) wait, let's just return what spec says or basic value. The spec says: GreatFriend=45, Friend=30 (Moolatrikona)/22.5 (Own), Neutral=7.5/3.75, Enemy=1.875, GreatEnemy=0.9375. We'll return 22.5 for Friend.
            case NEUTRAL -> 7.5;
            case ENEMY -> 1.875;
            case GREAT_ENEMY -> 0.9375;
        };
    }
}
