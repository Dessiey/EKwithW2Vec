package textRepresentations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * A set of document vectors are generated from records consisting of terms.
 * @author kz_kishida
 */
public class DocVectorFileGenerator {
    
    BufferedReader reader; 
    TreeMap<String, Dat> map;
    int opt;
    String delimit;
    int tcount;
    String inputfilename;
    int out_docs;
    int upper;
    PrintStream ps;
    int no_docs;
    int N;
    String docid;
    int[] tf_list;
    int[] df_list;
    double percent;
    int term_counter;
    
    class Dat {
        int v;
        int df;
    }

    public DocVectorFileGenerator(String filename, String delimit) throws UnsupportedEncodingException {
        File infile = new File(filename);
        FileInputStream fis;
        try {
            fis = new FileInputStream(infile);
            InputStreamReader isr = new InputStreamReader(fis,"UTF8");
            reader = new BufferedReader(isr);
            this.delimit = delimit;
            inputfilename = filename;
        } 
        catch (FileNotFoundException e) {
            System.out.printf("Fatal error: file not found - %s\n", filename);
            System.out.println("Stopped.");
            System.exit(0);
        }
    }
    
    public void runTf(String outfilename, String termfilename) {
        opt = 1;
        run(outfilename, termfilename);
    }
    
    public void runTf(double v, String outfilename, String termfilename) {
        opt = 2;
        percent = v;
        run(outfilename, termfilename);
    }
    
    public void runTfIdf(String outfilename, String termfilename) {
        opt = 3;
        run(outfilename, termfilename);
    }
    
    private void run(String outfilename, String termfilename) {
        PrintStream ps_t;
        try {
            ps = new PrintStream(outfilename);
            try {
                ps_t = new PrintStream(termfilename); 
                map = new TreeMap<>();
                runR(ps_t);
                System.out.printf("End of processing\n");
                System.out.printf("  -- Created document vector file = %s\n", outfilename);
                System.out.printf("  -- No. of documents = %d\n", out_docs); 
                System.out.printf("  -- Created term file = %s\n", termfilename);
                System.out.printf("  -- No. of original terms = %d\n", map.size()); 
                System.out.printf("  -- The terms with 1 < df < %d were adopted (df denotes 'document frequency').\n", upper);
                System.out.printf("  -- No. of the adopted terms = %d\n\n", term_counter); 
            }
            catch(IOException e) {
                System.out.printf("Fatal error: file not created - %s\n", termfilename);
                System.out.println("Stopped.");
                System.exit(0);
            }
        }
        catch(FileNotFoundException e) {
            System.out.printf("Fatal error: file not created - %s\n", outfilename);
            System.out.println("Stopped.");
            System.exit(0);
        }
    }
    
    private int runR(PrintStream ps_t) throws IOException {
        no_docs = 0;
        out_docs = 0;
        tcount = 0;
        while(get() != null) {
            no_docs++;
            if((no_docs % 1000) == 0) {
                System.out.println("Now processing...");
            }
        }
        if((opt == 1)||(opt == 3)) { upper = no_docs; }
        else if(opt == 2) {
            upper = (int)(no_docs * percent); 
        }
        System.out.printf("The input file includes %d documents\n\n", no_docs);
        N = no_docs;
        File infile = new File(inputfilename);
        FileInputStream fis;
        fis = new FileInputStream(infile);
        InputStreamReader isr = new InputStreamReader(fis,"UTF8");
        reader = new BufferedReader(isr);
        no_docs = 0;
        while(put() != null) {
            no_docs++;
            if((no_docs % 1000) == 0) {
                System.out.println("Now processing...");
            }
        }
        outTermList(ps_t);
        ps.close();
        return out_docs;
    }
    
    private String get() throws IOException {
        String str = reader.readLine();
        if(str == null) {
            reader.close();
            return null;
        }
        else {
            StringTokenizer token = new StringTokenizer(str, delimit);
            Dat dat;
            token.nextToken();
            TreeMap<String, Dat> map2 = new TreeMap<>();
            while (token.hasMoreTokens()) {
                String tk = token.nextToken();
                if((dat = map2.get(tk)) == null) {
                    dat = new Dat();
                    dat.v = 0;
                    dat.df = 0;
                    map2.put(tk, dat);
                    if((dat = map.get(tk)) == null) {
                        dat = new Dat();
                        dat.df = 1;
                        dat.v = ++tcount;
                        map.put(tk, dat);
                    }
                    else {
                        dat.df++;
                    }
                }
            }
            return str;
        }
    }
    
    private String put() throws IOException {
        String str = reader.readLine();
        if(str == null) {
            reader.close();
            return null;
        }
        else {
            StringTokenizer token = new StringTokenizer(str, delimit);
            Dat dat;
            docid = token.nextToken();
            int count = 0;
            TreeMap<Integer, Dat> map2 = new TreeMap<>();
            while (token.hasMoreTokens()) {
                dat = map.get(token.nextToken());
                if((dat.df > 1) && (dat.df < upper)) {
                    Dat dat2;
                    if((dat2 = map2.get(dat.v)) == null) {
                        dat2 = new Dat();
                        dat2.v = 1;
                        dat2.df = dat.df;
                        map2.put(dat.v, dat2);
                    }
                    else {
                        dat2.v++;
                    }
                    count++;
                }
            }
            if(count > 0) {
                tf_list = null;
                df_list = null;
                int[] terms = outArray(map2);
                Double v;
                ps.printf("%s,", docid);
                for(int j = 0; j < terms.length; j++) {
                    ps.printf("%d,", terms[j]);
                    if((opt == 1)||(opt == 2)) {
                        v = (double)tf_list[j];
                    }
                    else {
                        v = (double)tf_list[j] * Math.log((double)N / df_list[j]);
                    }
                    if(j == terms.length - 1) {
                        ps.printf("%f\n", v);
                    }
                    else {
                        ps.printf("%f,", v);
                    }
                }
                out_docs++;
            }
            else {
                System.out.println("Warning! - All terms are deleted");
                System.out.printf("  The sequential number of the document = %d, document ID = %s\n", no_docs + 1, docid);
                System.out.println("   --> The document is not included in the output file\n");
                System.out.println();
            }
            return str;
        }
    }
    
    private int[] outArray(TreeMap<Integer, Dat> tree) {
        int[] terms = new int[tree.size()];
        tf_list = new int[tree.size()];
        df_list = new int[tree.size()];
        Set set = tree.entrySet();
	Iterator it = set.iterator();
        int count = 0;
        while(it.hasNext()) {
            Map.Entry<Integer, Dat> entry = (Map.Entry)it.next();
            tf_list[count] = entry.getValue().v;
            df_list[count] = entry.getValue().df;  
            terms[count++] = entry.getKey();
        }
	return terms;
    }
    
    private void outTermList(PrintStream ps_t) {
        Set set = map.entrySet();
	Iterator it = set.iterator();
        term_counter = 0;
        while(it.hasNext()) {
            Map.Entry<String, Dat> entry = (Map.Entry)it.next();
            int df = entry.getValue().df;
            ps_t.printf("%s, %d, %d\n", entry.getKey(), entry.getValue().v, df);
            if((df > 1) && (df < upper)) {
                term_counter++;
            }
        }
        ps_t.close();
    }
      public static void main(String args[]) throws Exception{
 String directory = "F:/Encyclopidic Knowlage/Amharic Corpus/Mapped/";
String delimit = ":";
DocVectorFileGenerator TextVector = new DocVectorFileGenerator(directory+"arrdocfeatures enrich 6spout.txt", delimit);
TextVector.runTf(directory+"sample_docvec_file_1.csv", directory+"sample_term_file_1.csv");
TextVector = new DocVectorFileGenerator(directory+"arrdocfeatures enrich 6spout.txt", delimit);
double percent = 0.6;
TextVector.runTf(percent, directory+"sample_docvec_file_2.csv", directory+"sample_term_file_2.csv");
TextVector = new DocVectorFileGenerator(directory +"arrdocfeatures enrich 6spout.txt", delimit);
TextVector.runTfIdf(directory+"sample_docvec_file_3.csv", directory+"sample_term_file_3.csv");
      }
      }


   
