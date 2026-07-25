package com.astrology.report;

import com.astrology.core.Planet;

public class LalKitabPrediction {

    public static String getLalKitabPrediction(Planet planet, int houseNum) {
        String remedy = "\n\n**Lal Kitab Remedy:** ";
        
        return switch (planet) {
            case SUN -> {
                String pred = switch (houseNum) {
                    case 1 -> "Sun in the 1st house makes you righteous, authoritative, and physically strong. However, if afflicted, you may face health issues related to the heart or eyes.";
                    case 2 -> "Sun in the 2nd house ensures a steady income but may cause harshness in speech and disputes within the family.";
                    case 3 -> "Sun in the 3rd house makes you courageous and wealthy. You will defeat your enemies but may have strained relations with brothers.";
                    case 4 -> "Sun in the 4th house may cause unrest in the family and health issues for the mother, though you will possess good vehicles.";
                    case 5 -> "Sun in the 5th house brings success and intellect but can cause delays or issues regarding male progeny.";
                    case 6 -> "Sun in the 6th house is an excellent placement for defeating enemies and achieving success in competitive exams or litigation.";
                    case 7 -> "Sun in the 7th house can cause immense ego clashes in marriage and partnerships. Health of the spouse may be a concern.";
                    case 8 -> "Sun in the 8th house brings an interest in the occult but can cause sudden health issues and a loss of paternal wealth.";
                    case 9 -> "Sun in the 9th house makes you highly spiritual, fortunate, and respected in society. You will undertake many pilgrimages.";
                    case 10 -> "Sun in the 10th house is a powerful placement for career and government success. You will achieve high status and authority.";
                    case 11 -> "Sun in the 11th house brings fulfillment of desires and gains from influential friends or the government.";
                    case 12 -> "Sun in the 12th house may cause sleep disturbances and expenses on medical bills, though you may travel abroad successfully.";
                    default -> "";
                };
                remedy += switch (houseNum) {
                    case 1 -> "Drink a glass of water sweetened with sugar before leaving the house.";
                    case 2 -> "Donate coconut, mustard oil, and almonds to a temple.";
                    case 3 -> "Offer water to the Sun daily and respect your elder siblings.";
                    case 4 -> "Distribute free medicines to the needy.";
                    case 5 -> "Do not delay having children; feed red-faced monkeys.";
                    case 6 -> "Keep a silver piece or coin in your wallet.";
                    case 7 -> "Bury a square piece of copper in the ground.";
                    case 8 -> "Throw jaggery (gud) into running water.";
                    case 9 -> "Donate silver or rice to a temple.";
                    case 10 -> "Wear a white cap or turban to cover your head.";
                    case 11 -> "Avoid eating meat and consuming alcohol.";
                    case 12 -> "Feed blind people and offer them sweets.";
                    default -> "";
                };
                yield pred + remedy;
            }
            case MOON -> {
                String pred = switch (houseNum) {
                    case 1 -> "Moon in the 1st house makes you highly emotional, attractive, and imaginative. You have a restless mind.";
                    case 2 -> "Moon in the 2nd house brings soft speech, good wealth, and strong family ties.";
                    case 3 -> "Moon in the 3rd house makes you courageous but emotionally dependent on siblings. You may change jobs frequently.";
                    case 4 -> "Moon in the 4th house is excellent for mental peace, real estate, and a strong bond with the mother.";
                    case 5 -> "Moon in the 5th house makes you highly creative, romantic, and blessed with good children.";
                    case 6 -> "Moon in the 6th house can cause frequent health issues, cold, cough, and mental anxiety. Enemies may trouble you secretly.";
                    case 7 -> "Moon in the 7th house brings a beautiful and emotional spouse, but you may suffer from jealousy or jealousy in the relationship.";
                    case 8 -> "Moon in the 8th house can cause intense emotional turmoil, fear of water, and a fascination with the occult.";
                    case 9 -> "Moon in the 9th house makes you highly religious, fortunate, and a lover of long journeys.";
                    case 10 -> "Moon in the 10th house brings career success, especially in public-facing roles or the liquid/dairy industry.";
                    case 11 -> "Moon in the 11th house brings immense gains, a large social circle, and fulfillment of desires.";
                    case 12 -> "Moon in the 12th house causes overspending, a highly imaginative mind, and a love for isolation or foreign lands.";
                    default -> "";
                };
                remedy += switch (houseNum) {
                    case 1 -> "Avoid drinking milk at night. Wear a silver chain.";
                    case 2 -> "Take the blessings of your mother daily.";
                    case 3 -> "Donate rice, silver, and milk to a temple.";
                    case 4 -> "Do not engage in the milk or dairy trade.";
                    case 5 -> "Serve cows and never use abusive language.";
                    case 6 -> "Serve your father and feed birds.";
                    case 7 -> "Avoid marrying before the age of 24.";
                    case 8 -> "Avoid swimming in deep waters and respect the elderly.";
                    case 9 -> "Offer milk to a Shivalinga.";
                    case 10 -> "Avoid night shifts and respect female colleagues.";
                    case 11 -> "Offer milk in a Bhairav temple.";
                    case 12 -> "Keep rain water in a glass bottle inside your house.";
                    default -> "";
                };
                yield pred + remedy;
            }
            case MARS -> {
                String pred = "Mars in house " + houseNum + " indicates high energy and passion in this area of life. It can bring both courage and conflict.";
                remedy += "Recite Hanuman Chalisa daily and donate red lentils (masoor dal) on Tuesdays.";
                yield pred + remedy;
            }
            case MERCURY -> {
                String pred = "Mercury in house " + houseNum + " sharpens your intellect and communication skills in this domain. It brings business acumen but also nervous energy.";
                remedy += "Feed green grass or spinach to cows on Wednesdays and respect your sisters.";
                yield pred + remedy;
            }
            case JUPITER -> {
                String pred = "Jupiter in house " + houseNum + " expands your wisdom, wealth, and luck in this area. It acts as a protective shield.";
                remedy += "Apply a saffron (kesar) or turmeric tilak on your forehead daily.";
                yield pred + remedy;
            }
            case VENUS -> {
                String pred = "Venus in house " + houseNum + " brings luxury, romance, and artistic flair to this part of your life. It ensures comfort and material gains.";
                remedy += "Respect your spouse, wear clean and ironed clothes, and use mild fragrances.";
                yield pred + remedy;
            }
            case SATURN -> {
                String pred = "Saturn in house " + houseNum + " brings delays, hard work, and profound life lessons to this area. Success requires immense patience.";
                remedy += "Serve the elderly, avoid eating non-vegetarian food, and donate mustard oil on Saturdays.";
                yield pred + remedy;
            }
            case RAHU -> {
                String pred = "Rahu in house " + houseNum + " amplifies your desires and ambitions here. It brings sudden events, foreign connections, and illusions.";
                remedy += "Keep a square piece of silver with you and avoid wearing dark blue or black clothes.";
                yield pred + remedy;
            }
            case KETU -> {
                String pred = "Ketu in house " + houseNum + " brings spiritual detachment, isolation, and past-life karma to this domain. It grants deep intuition.";
                remedy += "Feed street dogs regularly and wear a gold ring.";
                yield pred + remedy;
            }
            default -> "Lal Kitab prediction not available.";
        };
    }
}
