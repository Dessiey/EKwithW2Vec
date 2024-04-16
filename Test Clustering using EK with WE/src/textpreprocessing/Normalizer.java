package textpreprocessing;

/**
 *
 * @author user
 */
public class Normalizer {

    public String Textnormalizer(String word) {
        String reg = "[\\፡\\።\\፤\\፦\\፣\\፥\\!\\)\\(\\\"\\]\\[\\<\\>\\']";
        word = word.replaceAll(reg, "");

        return word;

    }

    public String normalize(String words) {

        String reg1 = "[\\ሃ\\ኅ\\ሀ\\ኃ\\ሐ\\ሓ\\ኻ]";
        String reg2 = "[\\ሁ\\ሑ\\ኁ\\ዅ]";
        String reg3 = "[\\ሂ\\ኂ\\ሒ\\ኺ]";
        String reg4 = "[\\ሄ\\ኌ\\ሔ\\ዄ]";
        String reg5 = "[\\ህ\\ሕ\\ኅ]";
        String reg6 = "[\\ሆ\\ኆ\\ሖ\\ኾ]";
        String reg7 = "[\\ሰ\\ሠ]";
        String reg8 = "[\\ሱ\\ሡ]";
        String reg9 = "[\\ሲ\\ሢ]";
        String reg10 = "[\\ሳ\\ሣ]";
        String reg11 = "[\\ሴ\\ሤ]";
        String reg12 = "[\\ስ\\ሥ]";
        String reg13 = "[\\ሶ\\ሦ]";
        String reg14 = "[\\አ\\ዓ\\ኣ\\ዐ]";
        String reg15 = "[\\ኡ\\ዑ]";
        String reg16 = "[\\ኢ\\ዒ]";
        String reg17 = "[\\ዔ\\ኤ]";
        String reg18 = "[\\እ\\ዕ]";
        String reg19 = "[\\ኦ\\ዖ]";
        String reg20 = "[\\ጸ\\ፀ]";
        String reg21 = "[\\ጹ\\ፁ]";
        String reg22 = "[\\ጺ\\ፂ]";
        String reg23 = "[\\ጻ\\ፃ]";
        String reg24 = "[\\ጼ\\ፄ]";
        String reg25 = "[\\ጽ\\ፅ]";
        String reg26 = "[\\ጾ\\ፆ]";

        words = words.replaceAll(reg1, "ሀ");
        words = words.replaceAll(reg2, "ሁ");
        words = words.replaceAll(reg3, "ሂ");
        words = words.replaceAll(reg4, "ሄ");
        words = words.replaceAll(reg5, "ህ");
        words = words.replaceAll(reg6, "ሆ");
        words = words.replaceAll(reg7, "ሰ");
        words = words.replaceAll(reg8, "ሱ");
        words = words.replaceAll(reg9, "ሲ");
        words = words.replaceAll(reg10, "ሳ");
        words = words.replaceAll(reg11, "ሴ");
        words = words.replaceAll(reg12, "ስ");
        words = words.replaceAll(reg13, "ሶ");
        words = words.replaceAll(reg14, "አ");
        words = words.replaceAll(reg15, "ኡ");
        words = words.replaceAll(reg16, "ኢ");
        words = words.replaceAll(reg17, "ኤ");
        words = words.replaceAll(reg18, "እ");
        words = words.replaceAll(reg19, "ኦ");
        words = words.replaceAll(reg20, "ፀ");
        words = words.replaceAll(reg21, "ፁ");
        words = words.replaceAll(reg22, "ፂ");
        words = words.replaceAll(reg23, "ፃ");
        words = words.replaceAll(reg24, "ፄ");
        words = words.replaceAll(reg25, "ፅ");
        words = words.replaceAll(reg26, "ፆ");

        return words;
    }

    public static void main(String args[]) {
        String sen = "ሕግ";
        Normalizer n = new Normalizer();
        String norm = n.normalize(sen);
        System.out.println("="+norm);
        System.out.println(Character.UnicodeScript.ETHIOPIC.toString().equals("ፅ"));

    }
}
