// Copy paste this Java Template and save it as "PatientNames.java"
import java.util.*;
import java.io.*;



class BSTVertex {
	  BSTVertex(String v) { key = v; parent = left = right = null; height = 0; size=1;}
	  // all these attributes remain public to slightly simplify the code
	  public BSTVertex parent, left, right;
	  public String key;
	  public int size;
	  public int height; // will be used in AVL lecture
	}
class BST {
	  protected BSTVertex root;

	  public BST() { root = null; }
	  // method called to search for a value v
	  public int height(BSTVertex T) {
	        if (T == null) return -1;
	        return T.height;
	  }
	  public int size(BSTVertex T){
		  if(T==null) return 0;
		  return T.size;
	  }
	  public BSTVertex search(String v) {
	    BSTVertex res = search(root, v);
	    return res == null ? null : res;
	  }
	  // overloaded recursive method to perform search
	  protected BSTVertex search(BSTVertex T, String v) {
	         if (T == null)  return null;                     // not found
	    else if (T.key.equals(v)) return T;                        // found
	    else if (T.key.compareTo(v)<0)  return search(T.right, v);       // search to the right
	    else                 return search(T.left, v);        // search to the left
	  }

	  // public method called to find Minimum key value in BST
	  public String findMin() { return findMin(root); }

	  // overloadded recursive method to perform findMin
	  protected String findMin(BSTVertex T) {
	         if (T == null)      throw new NoSuchElementException("BST is empty, no minimum");
	    else if (T.left == null) return T.key;                    // this is the min
	    else                     return findMin(T.left);           // go to the left
	  }


	  // public method called to find Maximum key value in BST
	  public String findMax() { return findMax(root); }

	  protected String findMax(BSTVertex T) {
	         if (T == null)       throw new NoSuchElementException("BST is empty, no maximum");
	    else if (T.right == null) return T.key;                   // this is the max
	    else                      return findMax(T.right);        // go to the right
	  }
	  public String successor(String v) { 
		    BSTVertex vPos = search(root, v);
		    return vPos == null ? null : successor(vPos);
		  }

	  protected String successor(BSTVertex T) {
		    if (T.right != null)                       // this subtree has right subtree
		      return findMin(T.right);  // the successor is the minimum of right subtree
		    else {
		      BSTVertex par = T.parent;
		      BSTVertex cur = T;
		      while ((par != null) && (cur == par.right)) {
		        cur = par;                                         // continue moving up
		        par = cur.parent;
		      }
		      return par == null ? null : par.key;           // this is the successor of T
		    }
		  }
	 
	  
	  public void inorder() { 
	    inorder(root);
	    System.out.println();
	  }

	  protected void inorder(BSTVertex T) {
	    if (T == null) return;
	    inorder(T.left);                               // recursively go to the left
	    System.out.printf(" %d", T.key);                      // visit this BST node
	    inorder(T.right);                             // recursively go to the right
	  }
	        
	  public int balanceFactor(BSTVertex T){
		  return height(T.left)-height(T.right);
	  }
	  public BSTVertex rotateLeft(BSTVertex T){
		  BSTVertex w=T.right;
		  w.parent=T.parent;
		  T.parent=w;
		  T.right=w.left;
		  if(w.left!=null) w.left.parent=T;
		  w.left=T;
		  w.size = T.size;
	      T.size = 1 + size(T.left) + size(T.right);
		  T.height=Math.max(height(T.left),height(T.right))+1;
		  w.height=Math.max(height(w.left),height(w.right))+1;
		  return w;		  
	  }
	  public BSTVertex rotateRight(BSTVertex T){
		  BSTVertex w=T.left;
		  w.parent=T.parent;
		  T.parent=w;
		  T.left=w.right;
		  if(w.right!=null) w.right.parent=T;
		  w.right=T;
		  w.size = T.size;
	      T.size = 1 + size(T.left) + size(T.right);
		  T.height=Math.max(height(T.left),height(T.right))+1;
		  w.height=Math.max(height(w.left),height(w.right))+1;
		  return w;		  
	  }

	  // method called to insert a new key with value v into BST
	  public void insert(String v) { root = insert(root, v); }

	  // overloaded recursive method to perform insertion of new vertex into BST
	  protected BSTVertex insert(BSTVertex T, String v) {
	    if (T == null) return new BSTVertex(v);          // insertion point is found

	    if (T.key.compareTo(v)<0) {                                      // search to the right
	      T.right = insert(T.right, v);
	      T.right.parent = T;
	    }
	    else {                                                 // search to the left
	      T.left = insert(T.left, v);
	      T.left.parent = T;
	    }
	    if(balanceFactor(T)==2){
	    	if(balanceFactor(T.left)==-1)
	    		T.left=rotateLeft(T.left);
	    	T=rotateRight(T);
	    	}
	    else if(balanceFactor(T)==-2){
	    	if(balanceFactor(T.right)==1)
	    		T.right=rotateRight(T.right);
	    	T=rotateLeft(T);	    		
	    }
	    T.size = 1 + size(T.left) + size(T.right);
	    T.height=Math.max(height(T.left), height(T.right))+1;

	    return T;                                          // return the updated BST
	  }  

	  // public method to delete a vertex containing key with value v from BST
	  public void delete(String v) { root = delete(root, v); }

	  // overloaded recursive method to perform deletion 
	  protected BSTVertex delete(BSTVertex T, String v) {
	    if (T == null)  return T;              // cannot find the item to be deleted

	    if (T.key.compareTo(v)<0)                                    // search to the right
	      T.right = delete(T.right, v);
	    else if (T.key.compareTo(v)>0)                               // search to the left
	      T.left = delete(T.left, v);
	    else {                                            // this is the node to be deleted
	      if (T.left == null && T.right == null)                   // this is a leaf
	        T = null;                                      // simply erase this node
	      else if (T.left == null && T.right != null) {   // only one child at right        
	        T.right.parent = T.parent;
	        T = T.right;                                                 // bypass T        
	      }
	      else if (T.left != null && T.right == null) {    // only one child at left        
	        T.left.parent = T.parent;
	        T = T.left;                                                  // bypass T        
	      }
	      else {                                 // has two children, find successor
	    	String successorV = successor(v);
	        T.key = successorV;         // replace this key with the successor's key
	        T.right = delete(T.right, successorV);      // delete the old successorV
	      }
	    }
	    if(T!=null){
	    	if(balanceFactor(T)==2){
		    	if(balanceFactor(T.left)==-1)
		    		T.left=rotateLeft(T.left);
		    	T=rotateRight(T);
		    	}
		    else if(balanceFactor(T)==-2){
		    	if(balanceFactor(T.right)==1)
		    		T.right=rotateRight(T.right);
		    	T=rotateLeft(T);	    		
		    }
	    	 T.size = 1 + size(T.left) + size(T.right);
	         T.height=Math.max(height(T.left), height(T.right))+1;
	    }
	    return T;                                          // return the updated BST
	  }

	  // will be used in AVL lecture
	  protected int getHeight(BSTVertex T) {
	    if (T == null) return -1;
	    else return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
	  }

	  public int getHeight() { return getHeight(root); }
	  public int rank(String v){
		  return rank(root,v);
	  }
	  protected int rank(BSTVertex T,String v){
		  if(T==null) return 0;
		  int k=v.compareTo(T.key);
		  if(k<0) return rank(T.left,v);
		  else if(k>0) return 1+size(T.left)+rank(T.right,v);
		  else return size(T.left);	  
	  }
	}


class PatientNames {
  private BST male;
  private BST female;

  public PatientNames() {
    male=new BST();
    female=new BST();
  }

  void AddPatient(String patientName, int gender) {
	  if(gender==1)
		  male.insert(patientName);
	  else
		  female.insert(patientName);
  }

  void RemovePatient(String patientName) {
		  male.delete(patientName);
		  female.delete(patientName);
    
  }

  int Query(String START, String END, int gender) {
    int ans = 0;
    String maxi="";
    if(gender==0||gender==1){
    	ans+=(male.rank(END)-male.rank(START));
    }
    if(gender==0||gender==2){
    	ans+=(female.rank(END)-female.rank(START));
    	
    }
    
    return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // AddPatient
        AddPatient(st.nextToken(), Integer.parseInt(st.nextToken()));
      else if (command == 2) // RemovePatient
        RemovePatient(st.nextToken());
      else // if (command == 3) // Query
        pr.println(Query(st.nextToken(), // START
                         st.nextToken(), // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    PatientNames ps2 = new PatientNames();
    ps2.run();
  }
}