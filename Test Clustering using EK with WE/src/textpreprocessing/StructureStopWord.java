/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpreprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import static textpreprocessing.WriteReadFile.WriteTofile;
import static textpreprocessing.WriteReadFile.readTextFile;

/**
 *
 * @author Dess
 */
public class StructureStopWord {
    static String allst="";
       public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
    }
    public static String readTextFile(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return content;
    }
    
//     public static List<String> readTextFileByLines(String fileName) throws IOException {
//        List<String> lines = Files.readAllLines(Paths.get(fileName));
//        return lines;
//    }
    public static void main(String args[]) throws Exception{
        
        String []result;
        String checkresult=null;
       int countWord=0;
     AmharicStemmer as=new AmharicStemmer();
        String directory = "F:/Encyclopidic Knowlage/Amharic Corpus/FinalStopwords.txt";
        String directory1 = "F:/Encyclopidic Knowlage/Amharic Corpus/stopwordstructured.txt";
        String stcontent=readTextFile(directory1);
             int count=0;   
             String []spline=stcontent.split(" ");
  for(String v:spline){
       v=as.Stem(v);
       if(!allst.contains(v)) {
       allst=allst+v+" ";
       count++;
       System.out.println(count+"="+v);
                
        }
        }
  writeToTextFile(directory,allst);
//  try (BufferedReader br = new BufferedReader(new FileReader(directory1))) {
//    String line;
//   int c=1;
//    while ((line = br.readLine()) != null) {
//        if (line.length()==0){
//            continue;
//        }
//        else{
//        String []spline=line.split(" ");
//        for(String v:spline){
//           // System.out.println(v);
//       v=as.Stem(v);
//       if(!allst.contains(v)) 
//       allst=allst+v+" ";
//            System.out.println(c+"="+v);
//                c++;
//        }
//        }
//// process the line.
//    }
//     // System.out.println(allst);
//     // writeToTextFile(directory1,allst);
//}

   //  System.out.println("preprocessing result="+checkresult);
 

}
}
