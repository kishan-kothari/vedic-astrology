package com.astrology.report;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;

public class PredictionEngine {
    
    public static String getGeneralNature(Rashi lagna, String lang) {
        if ("hi".equalsIgnoreCase(lang)) {
            return lagna.getSanskritName() + " लग्न के लोग स्वभाव से बहुत " + (lagna.getElement() == Rashi.Element.FIRE ? "ऊर्जावान और साहसी" : "शांत और स्थिर") + " होते हैं। आपकी निर्णय लेने की क्षमता अद्वितीय है।";
        }
        return "People born with " + lagna.getEnglishName() + " Ascendant are generally very " + (lagna.getElement() == Rashi.Element.FIRE ? "energetic and courageous" : "calm and stable") + " by nature. Your decision-making ability is unique.";
    }

    public static String getHealthPrediction(Rashi lagna, String lang) {
        if ("hi".equalsIgnoreCase(lang)) {
            return "स्वास्थ्य के मामले में " + lagna.getSanskritName() + " लग्न आपको " + (lagna.getElement() == Rashi.Element.WATER ? "कफ और सर्दी" : "पित्त और गर्मी") + " से संबंधित परेशानियाँ दे सकता है।";
        }
        return "In terms of health, the " + lagna.getEnglishName() + " Ascendant may give you problems related to " + (lagna.getElement() == Rashi.Element.WATER ? "phlegm and cold" : "bile and heat") + ".";
    }

    public static String getFinancePrediction(Rashi moonSign, String lang) {
        if ("hi".equalsIgnoreCase(lang)) {
            return "चंद्रमा के " + moonSign.getSanskritName() + " में होने से आपके वित्त में उतार-चढ़ाव आ सकते हैं, लेकिन आप अपने कौशल से धन अर्जित करेंगे।";
        }
        return "With the Moon in " + moonSign.getEnglishName() + ", there might be fluctuations in your finances, but you will earn wealth through your skills.";
    }

    public static String getMangalDoshaExplanation(boolean isManglik, String lang) {
        if (!isManglik) {
            return "hi".equalsIgnoreCase(lang) ? "आपकी कुण्डली मंगल दोष से मुक्त है।" : "Your chart is free from Mangal Dosha.";
        }
        if ("hi".equalsIgnoreCase(lang)) {
            return "सामान्यतः मंगल दोष जन्म-कुण्डली में लग्न और चन्द्र से देखा जाता है। मंगल के प्रभाव से विवाह में विलंब या वैवाहिक जीवन में समस्याएँ हो सकती हैं। उपाय के तौर पर कुंभ विवाह या मंगल शांति करवानी चाहिए।";
        }
        return "Generally, Mangal Dosha is checked from Ascendant and Moon. Mars's influence can cause delay in marriage or marital issues. As a remedy, Kumbh Vivah or Mangal Shanti is recommended.";
    }

    public static String getDashaPrediction(Planet lord, String lang) {
        if ("hi".equalsIgnoreCase(lang)) {
            return "यह " + lord.getName() + " की दशा है। इस अवधि में आपके जीवन में कई बदलाव आएँगे। " + lord.getName() + " के कारक तत्वों से सम्बंधित फलों की प्राप्ति होगी।";
        }
        return "This is the Dasha of " + lord.getName() + ". Many changes will occur in your life during this period. You will experience results related to the significations of " + lord.getName() + ".";
    }

    public static String getDivisionalChartExplanation(int divisor, String lang) {
        if ("hi".equalsIgnoreCase(lang)) {
            return switch (divisor) {
                case 1 -> "D1 (लग्न): यह आपका मुख्य चार्ट है जो आपके सम्पूर्ण जीवन को दर्शाता है।";
                case 2 -> "D2 (होरा): धन और संपत्ति का विश्लेषण।";
                case 3 -> "D3 (द्रेष्काण): भाई-बहन और साहस का विश्लेषण।";
                case 4 -> "D4 (चतुर्थांश): भाग्य और संपत्ति का विश्लेषण।";
                case 7 -> "D7 (सप्तमांश): संतान और पोते-पोतियों का विश्लेषण। इस चार्ट के स्वामी की दशा में संतान प्राप्ति के योग बनते हैं।";
                case 9 -> "D9 (नवांश): विवाह और जीवनसाथी का विश्लेषण।";
                case 10 -> "D10 (दशमांश): करियर और व्यवसाय का विश्लेषण।";
                case 12 -> "D12 (द्वादशांश): माता-पिता का विश्लेषण।";
                case 16 -> "D16 (षोडशांश): वाहन सुख का विश्लेषण।";
                case 20 -> "D20 (विंशांश): धार्मिक रुचि का विश्लेषण।";
                case 24 -> "D24 (चतुर्विंशांश): शिक्षा का विश्लेषण।";
                case 27 -> "D27 (सप्तविंशांश): बल और कमज़ोरी का विश्लेषण।";
                case 30 -> "D30 (त्रिंशांश): दुर्भाग्य और बीमारियों का विश्लेषण।";
                case 40 -> "D40 (खवेदांश): शुभ फलों का विश्लेषण।";
                case 45 -> "D45 (अक्षवेदांश): सामान्य जीवन का विश्लेषण।";
                case 60 -> "D60 (षष्ट्यंश): पूर्व जन्म के कर्मों का विश्लेषण।";
                default -> "D" + divisor + " चार्ट।";
            };
        }
        return switch (divisor) {
            case 1 -> "D1 (Lagna): This is your main chart showing overall life.";
            case 2 -> "D2 (Hora): Analysis of wealth and assets.";
            case 3 -> "D3 (Drekkana): Analysis of siblings and courage.";
            case 4 -> "D4 (Chaturthamsha): Analysis of destiny and property.";
            case 7 -> "D7 (Saptamsha): Analysis of children and grandchildren. Positive timing for childbirth occurs during the Dasha of this chart's lords.";
            case 9 -> "D9 (Navamsha): Analysis of marriage and spouse.";
            case 10 -> "D10 (Dashamsha): Analysis of career and profession.";
            case 12 -> "D12 (Dwadashamsha): Analysis of parents.";
            case 16 -> "D16 (Shodashamsha): Analysis of vehicles and happiness.";
            case 20 -> "D20 (Vimshamsha): Analysis of religious inclinations.";
            case 24 -> "D24 (Chaturvimshamsha): Analysis of education.";
            case 27 -> "D27 (Saptavimshamsha): Analysis of strengths and weaknesses.";
            case 30 -> "D30 (Trimshamsha): Analysis of misfortunes and diseases.";
            case 40 -> "D40 (Khavedamsha): Analysis of auspicious results.";
            case 45 -> "D45 (Akshavedamsha): Analysis of general life.";
            case 60 -> "D60 (Shashtyamsha): Analysis of past life karma.";
            default -> "D" + divisor + " Chart.";
        };
    }
}
