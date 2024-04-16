/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpreprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dess

*/

public class RemoveStopWord {
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

    RemoveStopWord() {
        this.Ammharic_stopword_list.addAll(Arrays.asList(AMHARIC_STOPWORDS));       
    }
      String []result;
        String checkresult=null;
       int countWord=0;
        String location = "F:/Encyclopidic Knowlage/Amharic Corpus/cleaned Final Stwords.txt";
       String input = readTextFile(location);
       result=input.split(" ");
       for (int i=0;i<result.length;i+=7){ 
           System.out.println('"'+result[i]+'"'+','+'"'+result[i+1]+'"'+','+'"'+result[i+2]+'"'+','+'"'+result[i+3]+'"'+','+'"'+result[i+4]+'"'+','+'"'+result[i+5]+'"'+','+'"'+result[i+6]+'"'+',');
           
       }
     public List removeStopList(List tokens) {
        String [] resultAfter=null;
        int k=0;
        List tokensWithOutStopWord = new ArrayList<>();
        Set<String> stopwords = Ammharic_stopword_list;
        String stopword=Arrays.toString(AMHARIC_STOPWORDS);
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
}
