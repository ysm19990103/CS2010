// Copy paste this Java Template and save it as "HospitalRenovation.java"
import java.util.*;
import java.io.*;



class HospitalRenovation {
  private int V; // number of vertices in the graph (number of rooms in the hospital)
  private int[][] AdjMatrix; // the graph (the hospital)
  private int[] RatingScore; // the weight of each vertex (rating score of each room)
  private ArrayList<ArrayList<Integer>> AdjList;
  private int[] visited;
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  public HospitalRenovation() {
  }
  void BFS(int i, int j){
	  if(i!=j){
	  Queue<Integer> queue=new LinkedList<Integer>();
	  queue.offer(i);
	  visited[i]=1;
	  while(!queue.isEmpty()){
		  int u=queue.poll();
		  for(Integer k:AdjList.get(u)){
			  if(k!=j){
				  if(visited[k]==0){
					  visited[k]=1;
					  queue.offer(k);
				  }
			  }
		  }
	  }
	  }
	  else
		  visited[i]=1;
	  
  }
  int critical(int n){
	  int counter=0;
	  for(int i=0;i<V;i++)
		  visited[i]=0;
	  for(int i=0;i<V;i++){
		  if(visited[i]==0){
			  counter+=1;
			  BFS(i,n);
		  }
	  }
	  if(counter>2) return 1;
	  else return 0;
  }

  int Query() {
    int ans = 0;
    int minimum=0;
    int find=0;
    for(int i=0;i<V;i++){
    	if(critical(i)==1){
    		find=1;
    		if(minimum==0) minimum=RatingScore[i];
    		else minimum=Math.min(minimum, RatingScore[i]);  		
    	}
  }
  if(find==0) ans=-1;
  else ans=minimum;
    
    


    return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------



  // --------------------------------------------

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {
      br.readLine(); // ignore dummy blank line
      V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new int[V];
      for (int i = 0; i < V; i++)
        RatingScore[i] = Integer.parseInt(st.nextToken());

      // clear the graph and read in a new graph as Adjacency Matrix
      visited=new int[V];
      AdjList=new ArrayList<ArrayList<Integer>>();
      for(int i=0;i<V;i++)
    	  AdjList.add(new ArrayList<Integer>());
      AdjMatrix = new int[V][V];
      for (int i = 0; i < V; i++) {
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
          int j = Integer.parseInt(st.nextToken());
          AdjMatrix[i][j] = 1;
          AdjList.get(i).add(j);// edge weight is always 1 (the weight is on vertices now)
        }
      }

      pr.println(Query());
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalRenovation ps3 = new HospitalRenovation();
    ps3.run();
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
