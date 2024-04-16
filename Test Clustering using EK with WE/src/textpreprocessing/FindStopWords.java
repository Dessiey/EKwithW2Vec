package textpreprocessing;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;


public class FindStopWords {
  public  List<List<String>> alldoc;
  public List<String> allterms;
    public static String WriteTofile(String stword) throws Exception
{ 
  
    FileOutputStream fout = new FileOutputStream(new File("F:/Encyclopidic Knowlage/Amharic Corpus/stopword2.txt/")); 
      AmharicStemmer r=new AmharicStemmer();
     	OutputStreamWriter rt = new OutputStreamWriter (fout,"utf-16");
     	BufferedWriter rtt = new BufferedWriter(rt);
        rtt.write(" ");
   
     //   System.out.println(stemres);
        rtt.write(stword);
    // rtt.write(" ");
    

          rtt.close();
     
          return "Successfull";
    
}
  public static String readTextFile(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return content;
    }

    public static List<String> readTextFileByLines(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        return lines;
    }

    public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
    }
    
    public static void main(String args[]) throws Exception{
        String []result;
         String []resu;
        // String directory = "F:/Encyclopidic Knowlage/Amharic Corpus/stopword2.txt";
         String stword="";
        String checkresult=null;
       int countWord=0;
     for(int j=198;j<900;j++){
       
       String fname="pol ("+j+")"+".txt";
//       File myFile = new File(directory);  
//  if (myFile.createNewFile()){
//   System.out.println("File is created!");
//         }
      
       String location = "F:/Encyclopidic Knowlage/Amharic Corpus/pol corpus/"+fname;
       String input = readTextFile(location);
       result=input.split(" ");
    
    for (int i=0;i<result.length;i++){   
        if(result[i].contains("፡፡")){
          result[i]=result[i].replace("፡፡"," ");
          result[i]=result[i].trim();
          stword=stword+result[i]+" ";
        }
      }
       //List list = (List) Arrays.asList(result);
     
         //List preproccesed=(List) Arrays.asList(result);
     //  result=(String[]) tokensWithOutStopWord.toArray();
    //  countWord=countWord+result.length;
    
         
         }
   checkresult=WriteTofile(stword);
        System.out.println("ch="+checkresult);
    System.out.println("result="+stword);

}
}