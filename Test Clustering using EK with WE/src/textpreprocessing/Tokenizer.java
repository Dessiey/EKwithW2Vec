package textpreprocessing;

import textpreprocessing.StopwordRemoval;
import textpreprocessing.WriteReadFile;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import textpreprocessing.Normalizer;
//import filemanipulations.impl.thesis.ReadFromFile;

public class Tokenizer {

    public List parseSentence(String sentence) {
        List words = new ArrayList<>();
        String delimiter = " ";
        String wrd[] = sentence.trim().split(delimiter);
        int wCount = 0;
        for (String w : wrd) {
            words.add(new Normalizer().normalize(w));
        }
        return new StopwordRemoval().removeStopList(words);
    }

    public List parseParagraph(String text, String lang) {
        List sentences = new ArrayList<>();
        String regex = "[\\።\\?\\!\\፤]";
        Pattern delimiter = Pattern.compile(regex);
        String sen[] = delimiter.split(text);
        for (String s : sen) {
            sentences.add(s);
        }
        return sentences;
    }

    public void createDictionary(Set word) throws Exception {
        try (FileWriter dictWriter = new FileWriter("C:\\Users\\user\\Documents\\NetBeansProjects\\TheImpl\\Corpus\\written_file")) {
            for (Object t : word) {
                dictWriter.write(t.toString() + "\n");
            }
            dictWriter.flush();
        }

    }

//    public List getToken() throws Exception {
//        String text = new WriteReadFile().read("C:\\Users\\user\\Documents\\NetBeansProjects\\TheImpl\\Corpus\\news.txt");
//        List sen = parseParagraph(text.trim(), "Amh");
//        List allWords = new ArrayList<>();
//        Set<String> type = new HashSet<>();
//        for (Object p : sen) {
//            List words = parseSentence(p.toString().trim());
//            for (Object w : words) {
//                allWords.add(w.toString());
//                type.add(w.toString());
//            }
//        }
//        createDictionary(type);
//        return allWords;
//    }

    public static void main(String[] args) throws Exception {
        Tokenizer t = new Tokenizer();
//        t.getToken();
//
//        System.out.println(t.getToken());

    }
}
