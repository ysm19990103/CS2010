// Copy paste this Java Template and save it as "GettingFromHereToThere.java"
import java.util.*;
import java.io.*;



class GettingFromHereToThere {
  private int V; // number of vertices in the graph (number of rooms in the building)
  private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
  private ArrayList < Boolean > taken;
  private PriorityQueue < IntegerTriple > pq;
  private ArrayList < ArrayList < IntegerPair > > mst;
  private int[] visited;
  private int[][] result;
  public GettingFromHereToThere() {
	  pq=new PriorityQueue<IntegerTriple>();
	  mst=new ArrayList < ArrayList < IntegerPair > >();
	  
  }
  private void process(int vtx){
	  taken.set(vtx, true);
	  for(int i=0;i<AdjList.get(vtx).size();i++){
		  IntegerPair v=AdjList.get(vtx).get(i);
		  if(!taken.get(v.first()))
			  pq.offer(new IntegerTriple(v.second(),vtx,v.first()));
	  }
  }
  void PreProcess() {
	  taken = new ArrayList < Boolean >(); 
	  taken.addAll(Collections.nCopies(V, false));
	  mst.clear();
	  for(int i=0;i<V;i++)
		  mst.add(new ArrayList<IntegerPair>());
	  pq.clear();
	  process(0);
	  int k=1;
	  while(k<V){
		  IntegerTriple front=pq.poll();
		  int w=front.first();
		  int i=front.second();
		  int j=front.third();
		  if(!taken.get(j)){
			  k++;
			  mst.get(j).add(new IntegerPair(i,w));
			  mst.get(i).add(new IntegerPair(j,w));
			  process(j);
		  }
	  }
	  int rows=Math.min(10, V);
	  result=new int[rows][V];
	  for(int i=0;i<rows;i++)
		  bfs(i);
  }
  void bfs(int i){
	  Queue<Integer> queue=new LinkedList<Integer>();
	  visited=new int[V];
	  queue.offer(i);
	  visited[i]=1;
	  while(!queue.isEmpty()){
		  int u=queue.poll();
		  for(IntegerPair k:mst.get(u)){
			  int j=k.first();
			  int w=k.second();
			  if(visited[j]==0){
				  visited[j]=1;
				  queue.offer(j);
				  if(w>result[i][u])
					  result[i][j]=w;
				  else
					  result[i][j]=result[i][u];
				  }
			  }
		  }
  }

  int Query(int source, int destination) {
	  return result[source][destination];

  }

  // You can add extra function if needed
  // --------------------------------------------



  // --------------------------------------------

  void run() throws Exception {
    // do not alter this method
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
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
        }
      }

      PreProcess(); // you may want to use this function or leave it empty if you do not need it

      int Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt()));
      pr.println(); // separate the answer between two different graphs
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    GettingFromHereToThere ps4 = new GettingFromHereToThere();
    ps4.run();
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
  Integer _first, _second, _third;

  public IntegerTriple(Integer f, Integer s, Integer t) {
    _first = f;
    _second = s;
    _third = t;
  }

  public int compareTo(IntegerTriple o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else if (!this.second().equals(o.second()))
      return this.second() - o.second();
    else
      return this.third() - o.third();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
  Integer third() { return _third; }
}