package StructureKB;

import textpreprocessing.*;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

/**
 *
 * @author user
 */
public class StopwordRemoval {
    
    
//    private static final String AMHARIC_STOPWORDS
//            = " ነው ነበር ነበረ አወ  እና  ወይም እዚህ ሌላ  እባክሸ እዚያ ሌሎች እባክህ ከ ሁሉ ተጨማሪ ናቸው እያንዳንዱ ውጪ ትናንት እያንዳንዳችው ናት ጥቂት "
//            + "እንዲሁም ነበሩ በርካታ እንደገና ነበረች ብቻ ማንም ያ ሁሉም እባክዎ ነገሮች ከፊት ከላይ ታች ከታች በታች የታች ገልፀዋል "
//            + "ከውስጥ በውስጥ የውስጥ ኋላ ከኋላ መካከል ከመካከል ሰሞኑን ከሰሞኑ በሰሞኑ የሰሞኑ ጋራ የጋራ ከጋራ ተለያዩ ተለያየ ተብሏል። "
//            + "ድረስ እስከ በጣም ግን ሲሆን ሲል ውስጥ ላይ ነይ ነው ጋር ናቸው ይህ ወደ ወዘተ እና ወይም እንደ ፊት ወደፊት ያለው። ። "
//            + "ነገር በኋላ በኩል ስለ ደግሞ እንጂ በዚህ ሆኖ ልዩ ጊዜ ዜና";
private static final String [] AMHARIC_STOPWORDS={"ትናንት", "ነው","ጥቂት", "በርካታ", "ብቻ", "ሁሉም", "ሌላ", "ሌሎች", "ሁሉ", "እያንዳንዱ", "እያንዳንዳችው", "እያንዳንዷ", "እንደገና", "ማንም", "እባኩዎ",
"እባክ", "ተጨማሪ", "ሰአት", "ውጪ", "ነበሩ", "ነበረች","ወይዘሮ", "ወይዘሪት", "ነገሮች", "ከፊት", "ከላይ", "ከታች"
,"በታች", "የታች", "ውስጥ", "ሆላ" ,"መካከል", "ሰሞኑን", "የጋራ", "ከጋራ", "ተለያዩ", "ተለያየ", "ድረስ", "እስከ", "በጣም", "ግን", "ሲሆን",
"ሲል", "ውስጥ", "ላይ", "ነው", "ጋር", "ናቸው", "ይህ", "በላይ", "ወደ", "ወዘተ", "እና", "ወይም", "እንደ", "አቶ", "ፊት", "ወደፊት" ,"ነገር", "በፊት",
"በሆላ", "በኩል", "ስለ", "ደግሞ", "እንጂ", "እንዲሁም", "ነው", "ነበር", "ነበረ",  
"ወይም", "እዚህ", "ሌላ", "እባክሸ", "እዚያ", "ሌሎች", "እባክህ", "ሁሉ", "ተጨማሪ", "ናቸው", "እያንዳንዱ", "ውጪ", "እያንዳንዳችው", "እንዲሁም", "ነበሩ", "ነበረች",
"ማንም", "ሁሉም", "እባክዎ", "ነገሮች", "ከፊት", "ከላይ", "ከታች", "የታች", "ገልፀዋል","ተብሏል",
"ውስጥ", "ኋላ", "ከኋላ", "መካከል", "ሰሞኑ", "ጋራ", "ተለያዩ", "ተለያየ", "ተብሏል", "ድረስ", "እስከ", "በጣም", "ግን", "ሲሆን","ሲል", "ውስጥ", "ላይ",
"ነው", "ጋር", "ይህ", "ወደ", "ወዘተ", "እና", "ወይም", "ወደፊት", "ያለው", "ነገር", "በኋላ", "በኩል", "ደግሞ", "እንጂ", "በዚህ", "ሆኖ", "ልዩ", "ጊዜ", "ዜና"};
    public final Set<String> Ammharic_stopword_list = new HashSet<String>();

    StopwordRemoval() {
        this.Ammharic_stopword_list.addAll(Arrays.asList(AMHARIC_STOPWORDS));       
    }
     
    public List removeStopList(List tokens) {
        String [] resultAfter=null;
        int k=0;
        List tokensWithOutStopWord = new ArrayList<>();
        Set<String> stopwords = Ammharic_stopword_list;
        String stopword=Arrays.toString(AMHARIC_STOPWORDS);
      //  System.out.println("size="+stopwords.size());
//       for(String x:stopwords){
//        System.out.println("-"+x);
//    }
//         for (int i =0; i < tokens.size(); i++){
//             for (int j =0; j < AMHARIC_STOPWORDS.length; i++){
//            String res = (String) tokensWithOutStopWord.get(i); 
//             String res2 =AMHARIC_STOPWORDS[j] ; 
//             if (res.contains(res2))
//                 continue;
//             else {
//            resultAfter[k]=res;
//                 System.out.println("res"+k+"="+ resultAfter[k]);
//            k++;
//           //   System.out.println("t="+t);
//            
//                 }  
//        }
//         }
        for (Object t : tokens) {
        //    System.out.println("t="+t);
            String h=t.toString().trim();
            if (stopword.contains(h)) {
             //  System.out.println("t="+t);
                continue;
            }
            else{
             // System.out.println("t="+t);   
            tokensWithOutStopWord.add(t);
           //   System.out.println("t="+t);
            }
        }
        return tokensWithOutStopWord;
    }
// public static void main(String[] args) throws Exception
//    {
//      StopwordRemoval st=new StopwordRemoval();
//  st.removeStopList(null);
//    }   

}
