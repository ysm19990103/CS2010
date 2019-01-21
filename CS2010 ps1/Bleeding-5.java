import java.util.*;

import java.io.*;



class Bleeding {
    private int V; // number of vertices in the graph (number of junctions in Singapore map)
    private int Q; // number of queries
    private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    // --------------------------------------------
    
    private static int inf=1000000000;
    private int[][] AdjMatrix;
    private int[][] record;   
    // --------------------------------------------

    public Bleeding() {
        // Write necessary code during construction
        //
        // write your answer here



    }

    void PreProcess() {
        // Write necessary code to preprocess the graph, if needed
        //
        // write your answer here
        //------------------------------------------------------------------------- 
        AdjMatrix=new int[V][V];
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                AdjMatrix[i][j]=inf;
            }
        }
        for(int i=0;i<V;i++){
            for(IntegerPair e:AdjList.get(i)){
                int v=e.first();
                int w=e.second();
                AdjMatrix[i][v]=w;
            }
            AdjMatrix[i][i]=0;
        }
        

        //------------------------------------------------------------------------- 
    }

    int Query(int s, int t, int k) {
        int ans = -1;

        // You have to report the shortest path from Ket Fah's current position s
        // to reach the chosen hospital t, output -1 if t is not reachable from s
        // with one catch: this path cannot use more than k vertices      
        //
        // write your answer here
        record=new int[V][Math.min(V,20)+1];
        for(int i=0;i<V;i++){
            for(int j=0;j<Math.min(V,20)+1;j++){
                record[i][j]=-1;
            }
        }
        int temp=ShortestPath(s,t,k);
        if(temp!=inf) ans=temp;
        //------------------------------------------------------------------------- 

        return ans;
    }

    // You can add extra function if needed
    // --------------------------------------------
    int ShortestPath(int s, int t, int k){
       
        if(record[s][k]!=-1) return record[s][k];
        if(s==t&&k>=1) return 0;
        if(k==2) return AdjMatrix[s][t];
        if(k<=1) return inf;
        int result=inf;
        for(int i=0;i<V;i++){
            if(AdjMatrix[s][i]!=inf&&s!=i){
                int temp=ShortestPath(i,t,k-1);
                if(temp!=inf) result=Math.min(result,AdjMatrix[s][i]+temp);
            }
        }
        record[s][k]=result;
        return result;
    }


    // --------------------------------------------

    void run() throws Exception {
        // you can alter this method if you need to do so
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            V = sc.nextInt();

            // clear the graph and read in a new graph as Adjacency List
            AdjList = new Vector < Vector < IntegerPair > >();
            for (int i = 0; i < V; i++) {
                AdjList.add(new Vector < IntegerPair >());

                int k = sc.nextInt();
                while (k-- > 0) {
                    int j = sc.nextInt(), w = sc.nextInt();
                    AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
                }
            }

            PreProcess(); // optional

            Q = sc.nextInt();
            while (Q-- > 0)
                pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

            if (TC > 0)
                pr.println();
        }

        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        Bleeding ps5 = new Bleeding();
        ps5.run();
    }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
    BufferedInputStream bis;
    IntegerScanner(InputStream is) {
        bis = new BufferedInputStream(is, 1000000);
    }

    public int nextInt() {
        int result = 0;
        try {
            int cur = bis.read();
            if (cur == -1)
                return -1;

            while ((cur < 48 || cur > 57) && cur != 45) {
                cur = bis.read();
            }

            boolean negate = false;
            if (cur == 45) {
                negate = true;
                cur = bis.read();
            }

            while (cur >= 48 && cur <= 57) {
                result = result * 10 + (cur - 48);
                cur = bis.read();
            }

            if (negate) {
                return -result;
            }
            return result;
        }
        catch (IOException ioe) {
            return -1;
        }
    }
}



class IntegerPair implements Comparable < IntegerPair > {
    Integer _first, _second;

    public IntegerPair(Integer f, Integer s) {
        _first = f;
        _second = s;
    }

    public int compareTo(IntegerPair o) {
        if (!this.first().equals(o.first()))
            return this.first() - o.first();
        else
            return this.second() - o.second();
    }

    Integer first() { return _first; }
    Integer second() { return _second; }
}
class IntegerTriple implements Comparable < IntegerTriple > {
    int _first, _second,_third;

    public IntegerTriple(int f, int s, int t) {
        _first = f;
        _second = s;
        _third = t;
    }

    public int compareTo(IntegerTriple o) {
        if (this.first()!=o.first())
            return this.first() - o.first();
        else if(this.second()!=o.second())
            return this.second() - o.second();
        else return this.third() - o.third();
    }

    int first() { return _first; }
    int second() { return _second; }
    int third() { return _third; }
}
