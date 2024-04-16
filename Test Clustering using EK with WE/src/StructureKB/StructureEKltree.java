package StructureKB;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Dess
 */
public class StructureEKltree {
    public String checkDB(String term){
          String val = "";
       String jdbcUrl = "jdbc:postgresql://localhost:5432/EncyclopidicKnowledge";
    String username = "postgres";
    String password = "abushay";
    String st12="";
    Connection conn = null;
    Statement st = null;
     Statement stm = null;
    ResultSet rsul = null;
     ResultSet rs1 = null;
            try {
      // Step 1 - Load driver
     // Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0
      // Step 2 - Open connection
      conn = DriverManager.getConnection(jdbcUrl, username, password);
      // Step 3 - Execute statement
         String selste= "SELECT * FROM public.\"category\";";
       //  String selst= "SELECT * FROM public.\"category\";";
      st = conn.createStatement();
     // stm = conn.createStatement();
      rsul = st.executeQuery(selste);
       // System.out.println(d.Stem("ሰዎች"));
       while(rsul.next()) {
           
      //  System.out.println(rs.getString(2));
      
         Integer x2=rsul.getInt(1);
          //  System.out.println("="+x);
//        String x2=x.toString()+".";
//           // System.out.println(x2);
         st12=rsul.getString(2);
//     
           System.out.println("checking");
         if(st12.equalsIgnoreCase(term)){
             val="true";
             break;
         }
         
       }
            }
        catch (SQLException e) {
      e.printStackTrace();
    } 
    
    finally {
      try {
        // Step 5 Close connection
        if (st != null) {
          st.close();
        }
        if (rsul != null) {
          rsul.close();
        }
        if (conn != null) {
          conn.close();
        }
      }
      
      catch (Exception e) {
        e.printStackTrace();
      }
    }
       
          return val;
      }

    public static void main(String[] args) throws Exception
	{
  //  StemData d= new StemData();
        //d.displayaffixes();
    String jdbcUrl = "jdbc:postgresql://localhost:5432/EncyclopidicKnowledge";
    String username = "postgres";
    String password = "abushay";
    
    Connection conn = null;
    Statement stmt = null;
     Statement stm = null;
    ResultSet rs = null;
     ResultSet rs1 = null;
  
    try {
      // Step 1 - Load driver
     // Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0
      // Step 2 - Open connection
      conn = DriverManager.getConnection(jdbcUrl, username, password);
      // Step 3 - Execute statement
         String selste= "SELECT * FROM public.\"RelatedLink\";";
      stmt = conn.createStatement();
      stm = conn.createStatement();
      rs = stmt.executeQuery(selste);
       // System.out.println(d.Stem("ሰዎች"));
         
          int cou=5;
         // Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
    while(rs.next()) {
      
      //  System.out.println(rs.getString(2));
        String[] arr=null;
      //   Matcher matcher = pattern.matcher(rs.getString(3));
        if(rs.getString(2)!=null){
           
           
            String ekt="";
            Integer x=rs.getInt(6);
            String x2=x.toString()+".";
           // System.out.println(x2);
            String st1=rs.getString(2);
          
            st1=st1.replace("(ወረዳ)","_");
            st1=st1.replaceAll("-","_"); 
            String st2=rs.getString(3);
              st2=st2.replaceAll(" ","_");  
              st1=st1.replaceAll(" ","_"); 
               st2=st2.replaceAll("፡","_"); 
               st2=st2.replaceAll("-","_"); 
              st2=st2.replace("(","__");
              st1=st1.replace("(","__"); 
              st2=st2.replaceAll("·","");
                 st2=st2.replaceAll("፩","_1");  
                 st2=st2.replaceAll("«","_1");
                 st2=st2.replaceAll("»","_1"); 
                  st2=st2.replaceAll(",","_"); 
                   st2=st2.replaceAll("፥","_");
                   st2=st2.replaceAll("!","");
                    st2=st2.replaceAll("/","");
                  st2=st2.replace("?","");
                    st2=st2.replaceAll("፤","_");
                   // st2=st2.replaceAll(".","");
                    st2=st2.replaceAll("፣","_");
                    st2=st2.replaceAll("።","_"); 
                     st2=st2.replaceAll("፡","_");
                   st1=st1.replaceAll("፥","_"); 
                   st1=st1.replaceAll("፣","_"); 
                   st1=st1.replaceAll("፡","_");
                   st1=st1.replaceAll("-","_");
                   st1=st1.replaceAll(",","_"); 
              //System.out.println(stemres);
              st2=st2.replace(")","__"); 
              st1=st1.replace(")","__"); 
              System.out.println("st2="+st2);
          ekt=st1+'.'+st2;
        
          String sql = "INSERT INTO public.\"Ekitree\" (ekid,conceptlink) "
            + "values ("+x+",'"+ekt+"');"; 
            System.out.println("ekt="+ekt);
            System.out.println("id="+x);
       //String ins= "insert into public.\"Ekitree\" values ("+x2+",'"+x+"');"; 
       // String upd= "update public.\"RelatedLink\" set \"Concept2\"='"+ekt+"' where rid= '"+x2+"';";
          String del= "DELETE from public.\"Ekitree\" where ekid= "+2+";";
           stm.executeUpdate(del);
            System.out.println("id="+x2);
     
          
                }
        else 
            continue;
           
        }
    }
     
   
     catch (SQLException e) {
      e.printStackTrace();
    } 
    
    finally {
      try {
        // Step 5 Close connection
        if (stmt != null) {
          stmt.close();
        }
        if (rs != null) {
          rs.close();
        }
        if (conn != null) {
          conn.close();
        }
      }
      
      catch (Exception e) {
        e.printStackTrace();
      }
    }

        }
}
	
