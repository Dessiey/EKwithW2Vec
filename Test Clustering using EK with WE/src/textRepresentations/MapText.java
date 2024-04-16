/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textRepresentations;

import StructureKB.StructureEKltree;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import textpreprocessing.AmharicStemmer;
import textpreprocessing.Normalizer;
import textpreprocessing.StopwordRemoval;
import static textpreprocessing.WriteReadFile.readTextFile;
/**
 * @author Dess
 */
public class MapText {
     static String res="";
   //  static StringBuilder sb=new StringBuilder();
     static BufferedWriter bw = null;
     static FileWriter fw = null;
     static  String []KBcategory=new String[1576];
     static  String []KBrelated1=new String[21894]; 
      static String []KBrelated2=new String[21894]; 
    public MapText() {
    String jdbcUrl = "jdbc:postgresql://localhost:5432/EncyclopidicKnowledge";
    String username = "postgres";
    String password = "abushay";
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    Statement stmt1 = null;
    ResultSet rs1 = null;
   String st="";
      int l=0;
      // String []content=input.split(" ");
        Normalizer n=new Normalizer();
      try {
    
      con = DriverManager.getConnection(jdbcUrl, username, password);
        String sel= "SELECT * FROM public.\"category\";";
        String sel1= "SELECT * FROM public.\"RelatedLink\";";
      stmt = con.createStatement();
      rs = stmt.executeQuery(sel);
      int i=0;
       while(rs.next()) {
         Integer id=rs.getInt(1);
         st=rs.getString(2);
         st=st.trim();
         st=n.normalize(st);
      KBcategory[i]=st;
   //  System.out.println(KBcategory[i]);
       i++;
          // System.out.println(KBcategory[i]);
       }
       int k=0;
     System.out.println("i="+i);
     stmt1 = con.createStatement();
     rs1 = stmt1.executeQuery(sel1);
         while(rs1.next()) {
      String r1,r2;
       //  Integer id=rs.getInt(1);
         r1=rs1.getString(2);
         r1=r1.trim();
         r1=n.normalize(r1);
         r2=rs1.getString(3);
         r2=r2.trim();
         r2=n.normalize(r2);
         KBrelated1[k]=r1;
         KBrelated2[k]=r2;
         k++;
      }
         
      }
  catch (SQLException e) {
      e.printStackTrace();
    } 
     finally {
      try {
        // Step 5 Close connection
        if (st != null) {
          stmt.close();
          stmt1.close();
        }
        if (rs != null) {
          rs.close();
          rs1.close();
        }
        if (con != null) {
          con.close();
          
        }
      }
        catch (Exception e) {
        e.printStackTrace();
      }
      }
    
    }
     
     public static String readFile(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return content;
    }
   public static String WriteTofile(String mp) throws Exception
       { 
 String fname= "F:/Encyclopidic Knowlage/Amharic Corpus/Mapped/arrdocfeaturestest.txt";
        File file = new File(fname);
    fw = new FileWriter(file.getAbsoluteFile(), true);
    bw = new BufferedWriter(fw);
      bw.write(mp);
      bw.newLine();
 
    bw.flush();
    bw.close();
     
          return "Successfull";
    
}
 public static String mapWIthKB(String input) throws IOException, Exception{
  String mapres="";
   String st="";
    int l=0;
      String []content=input.split(" ");

    for (String term : content) {
        if (term.length()<2)
            continue;
      //  System.out.println("term="+term);
        for(int i=0;i<KBcategory.length;i++){
          //  System.out.println(KBcategory[i]);
            if((term!=null) && (KBcategory[i]!=null)){
               
                   if(KBcategory[i].equals(term)){
                      // if(!res.contains(term)){
                     mapres=mapres+term+":"; 
                     break;
                  // }
                   }
            else if(term.length()>3){
                   String temp=term.replace(term.substring(term.length()-1), "");
            if (temp.length()>3){   
                if(KBcategory[i].contains(temp)){
                    // if(!res.contains(term))
                   mapres=mapres+term+":";  
                   break;
                  }
                }
                }
            }
        }
         
       }
    String catres=res;
  //  System.out.println("res="+res);
   for (String term : content) {
         if (term.length()<2)
            continue;
        for(int i=0;i<KBrelated1.length;i++){
          //  System.out.println(KBcategory[i]);
            if((term!=null) && (KBrelated1[i]!=null)){
               //String temp=term.replace(term.substring(term.length()-1), "");
                   if(KBrelated1[i].equals(term)){
                       if(!catres.contains(term)){
                     mapres=mapres+term+":"; 
                     break;
                   }
                   }
               
//           else if (temp.length()>3){   
//                if(KBrelated1[i].contains(temp)){
//                     if(!catres.contains(term)){
//                   res=res+term+":"; 
//                     break;
//                     }
//                  }
//                }
                }
        }
   }
    //System.out.println(res);
    for (String term : content) {
         if (term.length()<2)
            continue;
         for(int i=0;i<KBrelated2.length;i++){
          
            if((term!=null) && (KBrelated2[i]!=null)){
              String temp=term.replace(term.substring(term.length()-1), "");
                   if(KBrelated2[i].equals(term)){
                       if(!catres.contains(term)){
                     mapres=mapres+term+":"; 
                     break;
                   }
                   }
               
           else if (temp.length()>3){   
                if(KBrelated2[i].contains(temp)){
                     if(!catres.contains(term)){
                   res=res+term+":";  
                  break;
                     }
//                     
//                }
//                }
        }
         
       }
   }
         }     
  // String res2=res;
int c=0;
    for (int i=0;i<content.length;i++) {
      if(!mapres.contains(content[i])){
          c=0;
        for(int j=0;j<content.length;j++){
                       if(content[i].equals(content[j])){
                    c=c+1; 
                       }
        }
          
        if(c>3){
      for(int k=0;i<c;i++)
        mapres=mapres+content[i]+":";    
   
       }
    
        
        

        }
      else 
          continue;
       
    }
    
    }
  return  mapres;
 }
 
    
 public static String mapToEKtree(String mapped) throws IOException{
      String jdbcUrl = "jdbc:postgresql://localhost:5432/EncyclopidicKnowledge";
    String username = "postgres",stdet="";
    String password = "abushay";
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    Normalizer n=new Normalizer();
   String resAfterTree=mapped;
     String []content=mapped.split(":");
         try {
    
      con = DriverManager.getConnection(jdbcUrl, username, password);
      for (int i=0;i<content.length;i++){
          String str=content[i];
          str=str.replace(".","");
          str=str.replace("(","");
          str=str.replace(")","");
          str=str+"."+"*";
     String selPar="SELECT conceptlink FROM public.\"Ekitree\" WHERE conceptlink ~ '"+str+"' ORDER by conceptlink";
              String sft="SELECT conceptlink FROM public.\"Ekitree\" WHERE conceptlink ~ '"+str+"' order by conceptlink" ;
      stmt = con.createStatement();
      rs = stmt.executeQuery(sft);
      if(rs!=null)
       while(rs.next()) {
       String  st=rs.getString(1);
       st=n.normalize(st);
       String temp;
       st=st.replace(content[i],"");
        for (String term : content) {
//            if(term.length()>3){
//           temp=term.replace(term.substring(term.length()-1), "");
//            if(st.contains(temp)){
//                     resAfterTree=resAfterTree+term+":";
//                     
//                   }
//            }
//          
//            else
            
            if(term.length()>3){
                if(st.contains(term)){
                     resAfterTree=resAfterTree+term+":";
                     stdet=stdet+" ";
                   break;
                   }
         }
           
       }
      }
      
      }
      
      
      System.out.println("result="+resAfterTree);
         }
  catch (SQLException e) {
      e.printStackTrace();
    } 
     finally {
      try {
        // Step 5 Close connection
        if (rs != null) {
          stmt.close();
        }
        if (rs != null) {
          rs.close();
        }
        if (con != null) {
          con.close();
        }
      }
      
      catch (Exception e) {
        e.printStackTrace();
      }
    } 
     return resAfterTree;
 }
 public static String  mapToPhrasalCncepts(String inputtext) throws IOException{
      String jdbcUrl = "jdbc:postgresql://localhost:5432/EncyclopidicKnowledge";
    String username = "postgres";
    String password = "abushay";
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
   String resAfter ="";
   String temp=inputtext;
   String [] resall =new String[3];
  // String location = "F:/Encyclopidic Knowlage/Amharic Corpus/preprocessed/"+"pol ("+105+").txt";
     //  String input = readTextFile(location);
      //String x1=mapToPhrasalCncepts(input);
    // String []content=mapped.split(":");
         try {
    
      con = DriverManager.getConnection(jdbcUrl, username, password);
  
    String sft="SELECT * FROM public.\"Phrasal Concepts\";" ;
      stmt = con.createStatement();
      rs = stmt.executeQuery(sft);
     // if(rs!=null)
      
       while(rs.next()) {
           
       String  st1=rs.getString(2);
     //  String  st2=rs.getString(2);
       String  st3=rs.getString(4);
     
       while(temp.contains(st1)) {
              if(temp.contains(st1)){
                     resAfter=resAfter+st1+":";  
                     temp=temp.replaceFirst(st1, "");
                   }
       }
        if(st3!=null){ 
       while(temp.contains(st3)) { 
      
              if(temp.contains(st3)){
                     resAfter=resAfter+st1+":";  
                     temp=temp.replaceFirst(st3, "");
                   }
       }
       }
      //  System.out.println("resultAfter="+resAfter);  
        }
          
    // System.out.println("resultAfter="+resAfter);
         }
  catch (SQLException e) {
      e.printStackTrace();
    } 
     finally {
      try {
        // Step 5 Close connection
        if (rs != null) {
          stmt.close();
        }
        if (rs != null) {
          rs.close();
        }
        if (con != null) {
          con.close();
        }
      }
      
      catch (Exception e) {
        e.printStackTrace();
      }
    }
       //  resall[0]=resAfter;
         //resall[1]=temp;
        // System.out.println("Res="+resall[0]);
         //System.out.println("temp="+resall[1]);
     return resAfter;
 }
    public static void main(String[] args) throws Exception
    {
       
        int dno=0;
        String [] mappedphrases=new String[3];
        MapText mp = new MapText();
        for(int j=1;j<101;j++){
            String mappedphrase="";
      //  String directory = "F:/Encyclopidic Knowlage/Amharic Corpus/preprocessed/";
     String fname="pol ("+j+").txt";
     String fname1="tel ("+j+").txt";
     String fname2="sp ("+j+").txt";
     String fname3="bibl ("+j+").txt";
     String fname4="art ("+j+").txt";
     String fname5="bus ("+j+").txt";
     String fname6="med ("+j+").txt";
       String location = "F:/Encyclopidic Knowlage/Amharic Corpus/preprocessed/bible/"+fname3;
       String input = readTextFile(location);
         mappedphrase=mp.mapToPhrasalCncepts(input);
            System.out.println("mp="+mappedphrase);           
           //res=mappedphrase[0];
          //String inputText=mappedphrase[1];
     String mappedConcept=mp.mapWIthKB(input);
        System.out.println(mappedConcept);
        String doc="bib";
         ++dno;
      String phraseandConcept=mappedphrase+mappedConcept;
    String withEkt=mapToEKtree(mappedConcept);
     //System.out.println("res="+res);
     String x=WriteTofile(doc+dno+":"+mappedphrase+withEkt);
        }
    }
   
}