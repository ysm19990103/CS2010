// Copy paste this Java Template and save it as "Supermarket.java"
import java.util.*;
import java.io.*;



class Supermarket {
  private int N; // number of items in the supermarket. V = N+1 
  private int K; // the number of items that Ketfah has to buy
  private int[] shoppingList; // indices of items that Ketfah has to buy
  private int[][] T; // the complete weighted graph that measures the direct wheeling time to go from one point to another point in seconds
  private int[][] shortest;
  private static int INF = Integer.MAX_VALUE;
  private static boolean[] visited;
  private static int minCost;
  private static ArrayList < Integer > path=new ArrayList<Integer>();
  
  private void backtracking(int u) {
	    path.add(u); // we append vertex u to current path
	    visited[u] = true; // to avoid cycle

	    // check if all vertices are visited
	    boolean all_visited = true;
	    for (int v = 0; v < K; v++)
	      if (!visited[shoppingList[v]]){
	        all_visited = false;
	        break;
	      }
	 
	    // all V vertices have been visited, compute tour cost
	    if (all_visited) { 

	      int cost=0; // cost of tour
	      // Write you code to compute tour cost here !
	      for(int v=0;v<path.size()-1;v++)
	    	  cost+=shortest[path.get(v)][path.get(v+1)];
	      cost+=shortest[path.get(path.size()-1)][0];
	      minCost = Math.min(minCost, cost); // keep the minimum
	    }
	    else {
	      for (int v = 0; v < K; v++)
	        if (!visited[shoppingList[v]])
	          backtracking(shoppingList[v]);
	    }
	    
	    visited[u] = false; // this is the only change to turn DFSrec to backtracking :)
	    path.remove(path.size()-1); // remove last item
	  }
  


  int Query() {
    int ans = 0;
    shortest = new int[N+1][N+1];

    for(int i=0; i<N+1; i++)
    	for(int j =0; j<N+1; j++)
    		shortest[i][j] = T[i][j];
    for(int k=0; k<N+1; k++)
    	for(int i=0; i<N+1; i++)
    		for(int j=0; j<N+1; j++)
    			shortest[i][j]=Math.min(shortest[i][j], shortest[i][k]+shortest[k][j]);
    visited = new boolean[N+1];
    Arrays.fill(visited, false);
    minCost = INF;
    backtracking(0); // start from vertex 0
    ans=minCost;
    return ans;
  }

  void run() throws Exception {
    // do not alter this method to standardize the I/O speed (this is already very fast)
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      // read the information of the complete graph with N+1 vertices
      N = sc.nextInt(); K = sc.nextInt(); // K is the number of items to be bought

      shoppingList = new int[K];
      for (int i = 0; i < K; i++)
        shoppingList[i] = sc.nextInt();

      T = new int[N+1][N+1];
      for (int i = 0; i <= N; i++)
        for (int j = 0; j <= N; j++)
          T[i][j] = sc.nextInt();

      pw.println(Query());
    }

    pw.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Supermarket ps6 = new Supermarket();
    ps6.run();
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
