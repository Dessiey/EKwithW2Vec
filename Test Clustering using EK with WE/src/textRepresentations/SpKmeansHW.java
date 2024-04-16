

package textRepresentations;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.TreeMap;


public class SpKmeansHW {
    
    static int MAX_LENGTH_ITEM_CSV = 1000;
    private ArrayList<DocumentVector> docfile;
    String inputfilename;
    TreeMap<Integer, String> docid_list;
    TreeMap<String, Integer> term_list;
    int no_terms;
    
    private int L, N;
    String docfilename;
    ClusterSet clset;
    int[] doc_clusterID1;
    int[] doc_clusterID2;
    int disp;
    int count;
    int no_clusters;
    int no_docs;
    
    private int[] ITRAN, LIVE, NCP;
    private int INDEX;
    private double D[];
    
    public SpKmeansHW(String filename) {
        // Constuctor ****
        // Reading an file of document vectors: file format is CSV
        // Example: 1,10.5,2,4.3,3,0.5[CR] -> a record (a document vector)
        //   where 1,2,3 - term ID (integer), 10.5, 4.3 0.5 - term weights (float)
        try {
            RandomAccessFile fin = new RandomAccessFile(filename, "r");
            fin.seek(0);
            System.out.println("Data loading...");
            inputDataR(fin); 
            System.out.printf("%d records in the file %s\n", docfile.size(), filename);
            System.out.printf("in which %d different terms are included.\n\n", term_list.size());
            inputfilename = filename;
        }
        catch(IOException e) {
            System.out.printf("Fatal error: file not found -> %s\n", filename); 
            System.out.println("Stopped");
            System.exit(0);
        }
    }
   
    public int run(int no_clusters, String outfilename) throws IOException {
        PrintStream ps;
        try {
            ps = new PrintStream(outfilename);
            this.no_clusters = no_clusters;
            file_pass();
            count = 2;
            HartiganWongSp();
            outputFile(ps);
        }
        catch(FileNotFoundException e) {
            System.out.printf("Fatal error: file not created -> %s\n", outfilename); 
            System.out.println("Stopped");
            System.exit(0);
        }
        return no_clusters;
    }
    
    private void outputFile(PrintStream ps) {
      //  ps.printf("Result of Text document Clustering EKB with neural Word embbading   \n");
      //  ps.printf("See: Experimentation using Spherical K-means Algorithim \n");
      //  ps.printf("..............................................\n\n");
       // ps.printf("input file =, %s\n", inputfilename);
        ps.printf("No. of Clusters =, %d\n", no_clusters);
        ps.printf("i-th document, Cluster ID (1 - %d)\n", no_clusters);
        for(int i = 0; i < no_docs; i++) {
            ps.printf("%d,%d\n", i + 1, doc_clusterID1[i] + 1);
        }
    }
    
    private int HartiganWongSp() throws IOException {
        int h, ite;
        int max_ite = 100000;
        int FLG;
        disp = 0;
        L = clset.no_clusters;
        N = clset.total_docs;
        LIVE = new int[L];      
        ITRAN = new int[L];       
        NCP = new int[L];    
        D = new double[N];      
        for(h = 0; h < L; h++) {
            ITRAN[h] = 1;
            NCP[h] = -1;
        }
        INDEX = 0;
        FLG = 1;
        ite = 0;
        while(FLG == 1) {
           optraSp();
           if(INDEX == N) { FLG = 0; }
           if(qtranSp(max_ite) == 0) {
               System.out.println("Fatal error: clustering algorithm stopped.");
               System.out.println("-- The input data may be inappropriate for clustering.");
               System.exit(0);

           }
           if(L == 2) { FLG = 0; }
           for(h = 0; h < L; h++) {
               NCP[h] = 0;
           }
           ite++;
           if(ite == max_ite) {
               System.out.println("Fatal error: clustering algorithm stopped.");
               System.out.println("-- The input data may be inappropriate for clustering.");
               System.exit(0);
           }
        }
        no_clusters = L;
        TopicLabelSwitch swt = new TopicLabelSwitch();
        swt.run(N, L, doc_clusterID1);
        return L;
   }
       
   private void optraSp() throws IOException {
       int i, h;
       int L1, L2, LL;
       double v, v2;
       int FLG;
       DocumentVector dvec;
       for(h = 0; h < L; h++) {
           if(ITRAN[h] == 1) {
               LIVE[h] = N + 1;
           }
       }
       count++;
       for(i = 0; i < N; i++) {
           dvec = docfile.get(i);
           INDEX++;
           L1 = doc_clusterID1[i];
           L2 = doc_clusterID2[i];
           LL = L2;
           if(clset.getNoDocs(L1) > 1) { 
               if(NCP[L1] != 0) {
                   D[i] = clset.getDecrement(L1, dvec);
               }
               v = clset.getIncrement(L2, dvec);
               v2 = -1.0;
               for(h = 0; h < L; h++) {
                   FLG = 1;
                   if(((i + 1) >= LIVE[L1])&&((i + 1) >= LIVE[h])) { FLG = 0; }
                   if((h == L1)||(h == LL)) { FLG = 0; }
                   if(FLG == 1) {
                       v2 = clset.getIncrement(h, dvec);
                       if(v2 > v) {
                           v = v2;
                           L2 = h;
                       }
                   }
               } 
               if(v2 > D[i]) {
                   INDEX = 0;
                   LIVE[L1] = N + (i + 1);
                   LIVE[L2] = N + (i + 1);
                   NCP[L1] = i + 1;
                   NCP[L2] = i + 1;
                   clset.removeDocumentSp(L1, dvec.no_terms, dvec.tm, dvec.wt);
                   clset.putDocumentSp(L2, dvec.no_terms, dvec.tm, dvec.wt);
                   doc_clusterID1[i] = L2;
                   doc_clusterID2[i] = L1;
               }
               else {
                   doc_clusterID2[i] = L2;
               }
           }
           if(INDEX == N) { return; }
       }
       for(h = 0; h < L; h++) {
           ITRAN[h] = 0;
           LIVE[h] -= N;
       }
   }
   
   private int qtranSp(int max_ite) throws IOException {
       int i;
       int L1, L2;
       double v;
       int ICOUN = 0;
       int ISTEP = 0;
       int FLG;
       int ite;
       DocumentVector dvec;
       for(ite = 0; ite < max_ite; ite++) {
           count++;
           for(i = 0; i < N; i++) {
               dvec = docfile.get(i);
               ICOUN++;
               ISTEP++;
               L1 = doc_clusterID1[i];
               L2 = doc_clusterID2[i];
               if(clset.getNoDocs(L1) != 1) {
                  if(ISTEP <= NCP[L1]) {
                      D[i] = clset.getDecrement(L1, dvec);
                  }
                  FLG = 1;
                  if((ISTEP >= NCP[L1])&&(ISTEP >= NCP[L2])) { FLG = 0; }
                  if(FLG == 1) {
                      v = clset.getIncrement(L2, dvec); 
                     if(v > D[i]) {
                        ICOUN = 0;
                        INDEX = 0;
                        ITRAN[L1] = 1;
                        ITRAN[L2] = 1;
                        NCP[L1] = ISTEP + N;
                        NCP[L2] = ISTEP + N;
                        clset.removeDocumentSp(L1, dvec.no_terms, dvec.tm, dvec.wt);
                        clset.putDocumentSp(L2, dvec.no_terms, dvec.tm, dvec.wt);
                        doc_clusterID1[i] = L2;
                        doc_clusterID2[i] = L1;
                     }     
                  }
               }
               if(ICOUN == N) { return 1; } 
           }
       }
       return 0;
   }
   
   private int file_pass() throws IOException {
       ArrayList allocation, allocation2;
       double max_cos, max_cos2;
       int max_clust, max_clust2;
       double cos;
       DocumentVector cvec;
       DocumentVector dvec;
       clset = null;
       clset = new ClusterSet();
       int k;
       allocation = new ArrayList();
       allocation2 = new ArrayList();
       for(k = 0; k < no_clusters; k++) {
           dvec = docfile.get(k);
           if(dvec == null) {
               System.out.println("Fatal error: The number of clusters is over the number of documents.");
               System.out.println("Stopped");
               System.exit(0);
           }
           else {
               clset.putDocumentSp(k, dvec.no_terms, dvec.tm, dvec.wt);
           }
       }
       no_docs = 0;
       int FLG;
       for(int i = 0; i < docfile.size(); i++) {
           dvec = docfile.get(i);
           max_cos = max_cos2 = -1;
           max_clust = max_clust2 = -1;
           for(k = 0; k < no_clusters; k++) {
               cvec = clset.getVec(k);
               cos = cvec.compCosineUnit(dvec.no_terms, dvec.tm, dvec.wt);
               FLG = 0;
               if(max_cos < cos) {
                   max_clust2 = max_clust;
                   max_cos2 = max_cos;
                   max_clust = k;
                   max_cos = cos;
                   FLG = 1;
               }
               if(FLG == 0) {
                   if(max_cos2 < cos) {
                       max_clust2 = k;
                       max_cos2 = cos;
                   }
               }
           }
           if(no_docs < no_clusters) {
               allocation.add(Integer.valueOf(no_docs));
               if(max_clust == no_docs) {
                   allocation2.add(Integer.valueOf(max_clust2));
               }
               else {
                   allocation2.add(Integer.valueOf(max_clust));
               }
           }
           else {
                allocation.add(Integer.valueOf(max_clust));
                allocation2.add(Integer.valueOf(max_clust2));
           }
           no_docs++;
        }
        clset = null;
        clset = new ClusterSet();
        doc_clusterID1 = new int[no_docs];
        doc_clusterID2 = new int[no_docs];
        Integer iv;
        for(int i = 0; i < no_docs; i++) {
            iv = (Integer)allocation.get(i);
            doc_clusterID1[i] = iv.intValue();
            dvec = docfile.get(i);
            clset.putDocumentSp(doc_clusterID1[i], dvec.no_terms, dvec.tm, dvec.wt);
            iv = (Integer)allocation2.get(i);
            doc_clusterID2[i] = iv.intValue();
        }
        return no_docs; 
    }
   
   private class ClusterSet {
        
       public int total_docs;
       public int no_clusters;
       ArrayList clust;
    
       class Cluster {
           int no_docs;
           DocumentVector vec;
           double jk;
       }
     
        public ClusterSet() {
            total_docs = 0;
            clust = new ArrayList();
            no_clusters = 0;
        }
        
        public int getNoDocs(int k) {
            if(k < no_clusters) {
                Cluster cl = (Cluster)clust.get(k);
                return cl.no_docs;
            }
            else { return -1; }
        }
        
        public double getDecrement(int clustID, DocumentVector dvec) {
            if(clustID < no_clusters) {
                Cluster cl = (Cluster)clust.get(clustID);
                double Jk = cl.jk;
                double n1 = Math.sqrt(cl.vec.norm_sq);
                double innv = cl.vec.compInnerProd(dvec.no_terms, dvec.tm, dvec.wt);
                double n2 = cl.vec.norm_sq - 2.0 * innv + 1.0;
                n2 = Math.sqrt(n2);
                double val = (1.0 - n1 / n2) * (Jk / n1);
                val += (2.0 * innv - 1.0) / n2;
                return val;
            }
            return -1.0;
        }
            
        public double getIncrement(int clustID, DocumentVector dvec) {
            if(clustID < no_clusters) {
                Cluster cl = (Cluster)clust.get(clustID);
                double Jk = cl.jk;
                double n1 = Math.sqrt(cl.vec.norm_sq);
                double innv = cl.vec.compInnerProd(dvec.no_terms, dvec.tm, dvec.wt);
                double n2 = cl.vec.norm_sq + 2.0 * innv + 1.0;
                n2 = Math.sqrt(n2);
                double val = (n1 / n2 - 1.0) * (Jk / n1);
                val += (2.0 * innv + 1.0) / n2;
                return val;
            }
            return -1.0;
        }    
        
        public void removeDocumentSp(int k, int no_terms, int[] tm, double wt[]) {
            Cluster cl = (Cluster)clust.get(k);
            double da = cl.vec.compInnerProd(no_terms, tm, wt);
            for(int j = 0; j < no_terms; j++) {
                if(cl.vec.removeTerm(tm[j], wt[j]) == 0) {
                    System.out.println("Fatal error\n");
                    System.out.println("The input data may be not appropriate to clustering.");
                    System.out.println("Stopped");
                    System.exit(0);
                }
            }
            cl.no_docs--;
            total_docs--;
            cl.jk -= (2.0 * da - 1.0);
            if(cl.no_docs == 0) {
                System.out.println("Fatal error\n");
                System.out.println("The input data may be not appropriate to clustering.");
                System.out.println("Stopped");
                System.exit(0);
            }
        }
            
        public int putDocumentSp(int k, int no_terms, int[] tm, double wt[]) {
            double da;
            if(k < no_clusters) {
                Cluster cl = (Cluster)clust.get(k);
                da = cl.vec.compInnerProd(no_terms, tm, wt);
                cl.no_docs++;
                total_docs++;
                for(int j = 0; j < no_terms; j++) {
                    cl.vec.addTerm(tm[j], wt[j]);
                }
                cl.jk += (2.0 * da + 1.0);
            }
            else {
                Cluster cl = new Cluster();
                cl.no_docs = 1;
                total_docs++;
                cl.vec = new DocumentVector();
                cl.vec.setID(no_clusters);
                cl.vec.genTree();
                for(int j = 0; j < no_terms; j++) {
                    cl.vec.putTerm(tm[j], wt[j]);
                }
                cl.jk = 1.0;
                clust.add(cl);
                no_clusters++;
            }
            return no_clusters;
        }
                 
        public DocumentVector getVec(int k) {
            if(k < no_clusters) {
                Cluster cl = (Cluster)clust.get(k);
                return cl.vec;
            }
            return null;
        }

    }
   
    private class TopicLabelSwitch {
    
        int[] rec_topicid;
        int N;
    
        public int run(int no_docs, int no_topics, int[] topic_id) {
            int no_clusters;
            int[] chk_topic = new int[no_topics];
            int[] new_label = new int[no_topics];
            int i, k;
            for(k = 0; k < no_topics; k++) {
                chk_topic[k] = 0;
                new_label[k] = -1;
            }
            for(no_clusters = 0, i = 0; i < no_docs; i++) {
                if(chk_topic[topic_id[i]] == 0) {
                    new_label[topic_id[i]] = no_clusters;
                    chk_topic[topic_id[i]] = 1;
                    no_clusters++;
                }          
            }
            rec_topicid = new int[no_docs];
            for(i = 0; i < no_docs; i++) {
                topic_id[i] = new_label[topic_id[i]];
                rec_topicid[i] = topic_id[i];
            }
            N = no_docs;
            return no_clusters;
        }
   }
    
   private class DocumentVector {
       
        public int id;
        public int no_terms;
        public double norm_sq;
        public int tm[];
        public double wt[];
              
        public DocumentVector() {
            no_terms = 0;
            norm_sq = 0.0;
        }
        
        TreeMap tree;
        
        class Attrb {
            double weight;
        }
        
        public void genTree() {
            tree = new TreeMap();
        }
    
        public void setID(int id) {
            this.id = id;
        }
    
        public double compInnerProd(int m, int tm[], double wt[]) {
            double inp = 0.0;
            for(int j = 0; j < m; j++) {
                Attrb ta = (Attrb)tree.get(Integer.valueOf(tm[j]));
                if(ta != null) { inp += wt[j] * ta.weight; }
            }
            return inp;
        }
    
        public void putTerm(int termid, double weight) {
            Integer tid = new Integer(termid);
            Attrb tat = new Attrb();
            tat.weight = weight;
            tree.put(tid, tat);
            no_terms++;
            norm_sq += (weight * weight);
        }
    
        public void addTerm(int termid, double weight) {
            Integer tid = new Integer(termid);
            Attrb tat = (Attrb)tree.get(tid);
            if(tat == null) {
                tat = new Attrb();
                tat.weight = weight;
                tree.put(tid, tat);
                no_terms++;
                norm_sq += (weight * weight);
            }
            else {
                norm_sq -= (tat.weight * tat.weight);
                tat.weight += weight;
                norm_sq += (tat.weight * tat.weight);
            }
        }
    
        public int removeTerm(int termid, double weight) {
            Attrb tat = (Attrb)tree.get(Integer.valueOf(termid));
            norm_sq -= weight * weight;
            tat.weight -= weight;
            if(tat.weight == 0.0) { no_terms--; }
            else if(tat.weight < -0.0001) {
                return 0;
            }
            return 1;
        }
    
        public double compCosineUnit(int m, int tm[], double wt[]) {
            double inp = 0.0;
            for(int j = 0; j < m; j++) {
                Attrb ta = (Attrb)tree.get(Integer.valueOf(tm[j]));
                if(ta != null) { inp += wt[j] * ta.weight; }
            }
            inp /= Math.sqrt(norm_sq);
            return inp;
        }
    }
   
    private int inputDataR(RandomAccessFile fin) throws IOException {
        docid_list = new TreeMap<>();
        term_list = new TreeMap<>();
        no_terms = 0;
        char ch;
        char[] buf = new char[MAX_LENGTH_ITEM_CSV];
        ArrayList<Integer> id_list = new ArrayList();
        ArrayList<Double> wt_list = new ArrayList();
        int FLG = 0;
        int k = 0;
        Integer docid = null;
        docfile = new ArrayList();
        count = 0;
        while((ch = getChar(fin)) > 0) {
            if(ch == '\n') {
                if(k > 0) {
                    try { wt_list.add(new Double(new String(buf, 0, k))); }
                    catch(NumberFormatException e) { dataError(e); }
                }
                putDocumentVector(docid, id_list, wt_list);
                count++;
                id_list = new ArrayList();
                wt_list = new ArrayList();
                FLG = k = 0;
            }
            else if(ch == ',') {
                if(FLG == 0) {
                    docid_list.put(new Integer(count), new String(buf, 0, k));
                    docid = count;
                    k = 0;
                    FLG = 1;
                }
                else if(FLG == 1) {
                    String term = new String(buf, 0, k);
                    Integer no = term_list.get(term);
                    if(no == null) {
                        no = new Integer(no_terms);
                        term_list.put(term, no);
                        no_terms++;
                    }
                    id_list.add(no);
                    k = 0;
                    FLG = 2;
                }
                else {
                    try{
                        wt_list.add(new Double(new String(buf, 0, k)));
                        k = 0;
                        FLG = 1;
                    }
                    catch(NumberFormatException e) { dataError(e); }
                }
            }         
            else {
                buf[k++] = ch;
                if(k == MAX_LENGTH_ITEM_CSV) {
                    System.out.printf("Fatal error: over MAX_LENGTH_ITEM_CSV = %d\n", MAX_LENGTH_ITEM_CSV);
                    System.out.println("The input data may be wrong."); 
                    System.out.println("Stopped");
                    System.exit(0);
                }
            }
        }
        if(id_list.size() > 0) {
            putDocumentVector(docid, id_list, wt_list);
            count++;
        }
        return docfile.size();
    }
    
    private char getChar(RandomAccessFile fin) throws IOException {
        try {
            return (char)fin.readUnsignedByte();
	}
	catch (EOFException e) {
            fin.close();
            return 0;
	}
    }
    
    private void putDocumentVector(int docid, ArrayList<Integer>id_list, ArrayList<Double> wt_list) {
        DocumentVector dvec = new DocumentVector();
        dvec.id = docid;
        dvec.no_terms = id_list.size();
        dvec.tm = new int[dvec.no_terms];
        dvec.wt = new double[dvec.no_terms];
        dvec.norm_sq = 0.0;
        for(int j = 0; j < dvec.no_terms; j++) {
            dvec.tm[j] = id_list.get(j);
            dvec.norm_sq += Math.pow(wt_list.get(j), 2);
        }
        for(int j = 0; j < wt_list.size(); j++) {
            dvec.wt[j] = wt_list.get(j) / Math.sqrt(dvec.norm_sq);
        }          
        docfile.add(dvec);
    }
    
    private void dataError(NumberFormatException e) {
        System.out.println("Fatal error: illegal record format -> " + e);
        System.out.println("The input file may be not written with CSV format");
        System.out.println("Stopped");
        System.exit(0);
    }
 public static void main(String args[]) throws Exception{
 String directory = "F:/Encyclopidic Knowlage/Amharic Corpus/Mapped/";
String delimit = ":";
SpKmeansHW spkm = new SpKmeansHW (directory+"sample_docvec_file_3.csv");
int no_of_clusters = 6;
spkm.run(no_of_clusters,directory+"clustering Result enrich 6spoutnew.csv");
      }

}
