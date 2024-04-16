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


public class WriteReadFile {
  public  List<List<String>> alldoc;
  public List<String> allterms;
    public static String WriteTofile(List Stemed,String fname) throws Exception
{ 
  
    FileOutputStream fout = new FileOutputStream(new File("F:/Encyclopidic Knowlage/Amharic Corpus/preprocessed1/art/"+fname)); 
      AmharicStemmer r=new AmharicStemmer();
     	OutputStreamWriter rt = new OutputStreamWriter (fout,"UTF8");
     	BufferedWriter rtt = new BufferedWriter(rt);
        //rtt.write(" ");
      //  rtt.newLine();
	// bw.flush();
    for(int i=0;i<Stemed.size();i++)
    {
    String res= ((String) Stemed.get(i));
 
        rtt.write(res);
     rtt.write(" ");
    
          
    }
          rtt.close();
     
          return "Successfull";
    
}
  public static String readTextFile(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        content=content.replaceAll("\n"," ");
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
        String checkresult=null;
       int countWord=0;
         String directory = "F:/Encyclopidic Knowlage/Amharic Corpus/preprocessed1/art/";
    for(int j=1;j<230;j++){ 
       String fname="pol ("+j+").txt";
     String fname1="tel ("+j+").txt";
     String fname2="sp ("+j+").txt";
     String fname3="bibl ("+j+").txt";
     String fname4="art ("+j+").txt";
     String fname5="bus ("+j+").txt";
     String fname6="med ("+j+").txt";
       File myFile = new File(directory+fname1);  
  if (myFile.createNewFile()){
   System.out.println("File is created!");
         }
      
       String location = "F:/Encyclopidic Knowlage/Amharic Corpus/arranged am corpus/arts/"+fname4;
       String input = readTextFile(location);
             input = input.replaceAll("[0-6]","");
             input = input.replaceAll("[8-9]","");
       AmharicStemmer as=new AmharicStemmer();
       result=input.split(" ");
         Normalizer nor = new Normalizer();
    for (int i=0;i<result.length;i++){        
          result[i]=result[i].replace(".","");
          result[i]=result[i].replace("፡","");
          result[i]=result[i].replace("።","");
          result[i]=result[i].replace("፥","");
          result[i]=result[i].replace("‹‹","");
          result[i]=result[i].replace("››","");
          result[i]=result[i].replace("/","");
          result[i]=result[i].replace("፤","");
          result[i]=result[i].replace("፣","");
          result[i]=result[i].replace("(","");
          result[i]=result[i].replace(")","");
          result[i]=result[i].replace("?","");
          result[i]=result[i].replace("፦","");
          result[i]=result[i].replace("፥","");
          result[i]=result[i].replace("-","");
          result[i]=result[i].replace("”","");
          result[i]=result[i].replace("”","");
            result[i]=result[i].replace("\"","");
            result[i]=result[i].replace("\"","");
            result[i]=result[i].replace("…","");
            result[i]=result[i].replace(":-","");
            result[i]=result[i].replace("‹","");
            result[i]=result[i].replace("›","");
            result[i]=result[i].replace("!","");
            result[i]=result[i].replace("!","");
          result[i]=result[i].trim();
          result[i]=nor.normalize(result[i]);
          
      }
       List list = (List) Arrays.asList(result);
     //Remove the stopwords   
      
   for (int i =0; i < list.size(); i++){
        
            result[i] = as.Stem((String) list.get(i)); 
             
        }
    StopwordRemove sw=new StopwordRemove();
      List list1 = (List) Arrays.asList(result);
      List tokensWithOutSW=sw.removeStopWord(list1);

      countWord=countWord+result.length;
     
      checkresult=WriteTofile(tokensWithOutSW,fname4);
         
         }
     System.out.println("preprocessing result="+checkresult);
        System.out.println("total number of words="+countWord);
}
}