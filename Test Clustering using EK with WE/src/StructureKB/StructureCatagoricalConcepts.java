package StructureKB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import textpreprocessing.StopwordRemoval;
import textpreprocessing.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dess
 */
public class StructureCatagoricalConcepts {
  static  boolean ch=false;
  static int idx=26236;
     public static void main(String[] args) throws Exception
	{
  //  StemData d= new StemData();
        //d.displayaffixes();
    String jdbcUrl = "jdbc:postgresql://localhost:5432/EncyclopidicKnowledge";
    String username = "postgres";
    String password = "abushay";
    String st12="";
    Connection conn = null;
    Statement stmt = null;
     Statement stm = null;
    ResultSet rs = null;
     ResultSet rs1 = null;
   StructureEKltree str=new StructureEKltree();
    try {
      // Step 1 - Load driver
     // Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0
      // Step 2 - Open connection
      conn = DriverManager.getConnection(jdbcUrl, username, password);
      // Step 3 - Execute statement
         String selste= "SELECT * FROM public.\"RelatedLink\";";
       //  String selst= "SELECT * FROM public.\"category\";";
      stmt = conn.createStatement();
     // stm = conn.createStatement();
      rs = stmt.executeQuery(selste);
       // System.out.println(d.Stem("ሰዎች"));
         
          int cou=5;
          Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
    while(rs.next()) {
    
      
      Integer x=rs.getInt(1);
        //    System.out.println("="+x);
//        String x2=x.toString()+".";
//           // System.out.println(x2);
         String st=rs.getString(2);
         
      
      String chckval=str.checkDB(st);
       if(!chckval.equalsIgnoreCase("true")){
          ++idx;
           //st1=st1.replace("(ወረዳ)","_");
        //  st1=st1.replaceAll("-","_"); 
//            String st2=rs.getString(3);
//              st2=st2.replaceAll(" ","_");  
           //   st1=st1.replaceAll(" ","_"); 
             // relationstem rstem=new relationstem();
          //    st1=rstem.Stem(st1);
//               st2=st2.replaceAll("፡","_"); 
//               st2=st2.replaceAll("-","_"); 
//              st2=st2.replace("(","__");
        //     st1=st1.replace("(","__"); 
//              st2=st2.replaceAll("·","");
//                 st2=st2.replaceAll("፩","_1");  
//                 st2=st2.replaceAll("«","_1");
//                 st2=st2.replaceAll("»","_1"); 
//                  st2=st2.replaceAll(",","_"); 
//                   st2=st2.replaceAll("፥","_");
//                   st2=st2.replaceAll("!","");
//                    st2=st2.replaceAll("/","");
//                  st2=st2.replace("?","");
//                    st2=st2.replaceAll("፤","_");
//                   // st2=st2.replaceAll(".","");
//                    st2=st2.replaceAll("፣","_");
//                    st2=st2.replaceAll("።","_"); 
//                     st2=st2.replaceAll("፡","_");
//                   st1=st1.replaceAll("፥","_"); 
//                  st1=st1.replaceAll("፣","_"); 
//                  st1=st1.replaceAll("፡","_");
//                  st1=st1.replaceAll("-","_");
//                  st1=st1.replaceAll(",","_"); 
////              //System.out.println(stemres);
////              st2=st2.replace(")","__"); 
//              st1=st1.replace(")","__"); 
//            //  System.out.println("st2="+st2);
     //     ekt=st1+'.'+st2;
//        
//          String sql = "INSERT INTO public.\"CatagoricalConcept\" (id,concept) "
//            + "values ("+x+",'"+ekt+"');"; 
//            System.out.println("ekt="+ekt);
//            System.out.println("id="+x);
       String ins= "insert into public.\"CatagoricalConcept\" values ("+idx+",'"+st+"');";
           System.out.println("id="+idx);
      //   String upd= "update public.\"category\" set \"cat_title\"='"+st1+"' where cat_id= "+x+";";
           String del= "DELETE from public.\"category\" where cat_id= "+x+";";
           stmt.executeUpdate(ins);
   
      
       }
       
           // System.out.println("id="+x2);
     
          
           //     }
      
          System.out.println("inserted="+idx);
      
        }
    }
     
   
     catch (SQLException e) {
      e.printStackTrace();
    } 
    
//    finally {
//      try {
//        // Step 5 Close connection
//        if (stmt != null) {
//          stmt.close();
//        }
//        if (rs != null) {
//          rs.close();
//        }
//        if (conn != null) {
//          conn.close();
//        }
//      }
//      
//      catch (Exception e) {
//        e.printStackTrace();
//      }
//    }

        }
}
