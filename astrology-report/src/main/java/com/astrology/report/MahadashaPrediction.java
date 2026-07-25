package com.astrology.report;

import com.astrology.core.Planet;

public class MahadashaPrediction {

    public static String getMahadashaPrediction(Planet lord, int houseNum) {
        String base = switch (lord) {
            case SUN -> "The Sun Mahadasha lasts for 6 years. During this period, your focus will shift towards career, authority, and public recognition. The Sun represents the soul, the father, and the government. You will feel a strong desire to lead and assert your independence. Health and vitality are generally strong, provided the Sun is well-placed. If afflicted, you may experience issues with ego, conflicts with authority figures, or health problems related to the heart or eyes.";
            case MOON -> "The Moon Mahadasha lasts for 10 years. This is a period of emotional development, domestic focus, and changes in your personal life. The Moon represents the mind, the mother, and public popularity. You may experience significant shifts in your living situation, mental peace, or emotional relationships. If the Moon is strong, you will enjoy fame, happiness, and a nurturing environment. If weak, you may suffer from mood swings, anxiety, or issues with your mother.";
            case MARS -> "The Mars Mahadasha lasts for 7 years. This is a highly active, energetic, and sometimes turbulent period. Mars represents courage, siblings, real estate, and physical energy. You will possess the drive to overcome obstacles and achieve your goals through sheer willpower. It is an excellent time for acquiring property or achieving victory over enemies. If Mars is afflicted, beware of accidents, impulsiveness, surgeries, or conflicts with siblings.";
            case RAHU -> "The Rahu Mahadasha lasts for 18 years. Rahu is the North Node of the Moon, representing worldly desires, foreign connections, and sudden events. This period is characterized by intense ambition and a drive for material success. You may experience rapid career growth, unconventional experiences, or foreign travel. Rahu magnifies the traits of the house it occupies. If well-placed, it brings immense wealth and status. If afflicted, it can cause confusion, sudden downfalls, or illusions.";
            case JUPITER -> "The Jupiter Mahadasha lasts for 16 years. Jupiter is the great benefic, representing wisdom, wealth, children, and spirituality. This is generally considered one of the most favorable periods in a person's life. You will experience growth in wisdom, financial stability, and perhaps the birth of children. It is an excellent time for higher education, long-distance travel, and spiritual pursuits. Even if afflicted, Jupiter rarely causes extreme harm, though it may bring laziness or over-optimism.";
            case SATURN -> "The Saturn Mahadasha lasts for 19 years. Saturn represents discipline, hard work, delays, and karma. This period is often a time of significant maturation and structural building in your life. Success comes, but only after relentless effort and patience. Saturn strips away illusions and forces you to face reality. If well-placed, it brings lasting achievements, land, and immense stability. If afflicted, it can cause depression, delays, chronic illness, and poverty.";
            case MERCURY -> "The Mercury Mahadasha lasts for 17 years. Mercury represents intellect, communication, business, and youth. This is a highly stimulating period focused on learning, writing, trade, and networking. You may experience success in business, literature, or analytical fields. Your mind will be sharp and adaptable. If Mercury is afflicted, you may suffer from nervous exhaustion, skin issues, or miscommunications.";
            case KETU -> "The Ketu Mahadasha lasts for 7 years. Ketu is the South Node of the Moon, representing spirituality, detachment, and past-life karma. This period often brings a sense of isolation or a desire to withdraw from material pursuits. It is an excellent time for meditation, astrology, and spiritual liberation (Moksha). You may experience sudden endings or a loss of interest in worldly affairs. If well-placed, it grants deep intuition and spiritual insights. If afflicted, it brings confusion, bizarre illnesses, or sudden losses.";
            case VENUS -> "The Venus Mahadasha lasts for 20 years. Venus represents love, luxury, art, and harmony. This is often the most enjoyable period in a person's life, focused on romance, marriage, the acquisition of vehicles, and artistic pursuits. You will desire comfort and aesthetic pleasure. If Venus is strong, it brings significant wealth, a happy marriage, and social success. If afflicted, it can lead to overindulgence, scandals, or issues in relationships.";
            default -> "Mahadasha prediction not available.";
        };

        String houseContext = "\n\nIn your birth chart, the Mahadasha lord " + lord.getName() + " is placed in House " + houseNum + ". ";
        houseContext += switch (houseNum) {
            case 1 -> "This placement brings the focus entirely on yourself, your physical body, and your personal path in life. You will undergo a significant personal transformation and emerge stronger.";
            case 2 -> "This highlights matters of wealth, family lineage, and speech. You will see fluctuations in your bank balance and may need to take on more responsibilities within your immediate family.";
            case 3 -> "This emphasizes courage, short travels, and relationships with younger siblings. You will rely heavily on your own efforts and communication skills to achieve success during this period.";
            case 4 -> "This brings focus to your home environment, mother, real estate, and inner peace. You may purchase property, renovate your home, or experience significant events related to your mother.";
            case 5 -> "This highlights creativity, romance, intellect, and children. It is an excellent period for higher education, artistic pursuits, or expanding your family.";
            case 6 -> "This emphasizes overcoming obstacles, daily routines, health, and conflicts. You may face competition or health issues, but you will also develop the resilience to defeat your enemies.";
            case 7 -> "This brings focus to partnerships, marriage, and public dealings. Significant developments in your romantic life or business partnerships are highly likely during this time.";
            case 8 -> "This highlights transformation, occult sciences, inheritance, and sudden changes. You may experience unexpected events or delve deep into research, psychology, or esoteric subjects.";
            case 9 -> "This emphasizes luck, long-distance travel, higher philosophy, and the father. You may undertake religious pilgrimages, pursue advanced studies, or experience a stroke of good fortune.";
            case 10 -> "This brings focus to your career, public status, and professional achievements. You will work hard to establish your reputation and may achieve significant authority or a promotion.";
            case 11 -> "This highlights gains, large networks, and the fulfillment of desires. You will benefit from your social circle and experience an increase in your income through various sources.";
            case 12 -> "This emphasizes spirituality, foreign lands, isolation, and expenses. You may travel abroad, engage in charity work, or feel a need to withdraw from the hustle and bustle of daily life.";
            default -> "";
        };

        return base + houseContext;
    }
}
