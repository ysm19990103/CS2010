import java.util.*;
import java.io.*;


class Patient implements Comparable<Patient>{
	private String name;
	private int level;
	private int order;
	public  Patient(String name, int level,int order){
		this.name=name;
		this.level=level;
		this.order=order;
	}
	public String getName(){
		return name;
	}
	public int getLevel(){
		return level;
	}
	public int getOrder(){
		return order;
	}
	public void incLevel(int inc){
		level+=inc;
	}
	public int compareTo(Patient p){
	    if(this.level>p.getLevel()) return 1;
	    else if(this.level==p.getLevel()&&this.order<p.getOrder()) return 1;
	    else return -1;
	  }
}
class EmergencyRoom {
	private ArrayList<Patient> patients;
	private HashMap<String,Integer> queueMap;
	private int order=0;
 
  public EmergencyRoom() {
	  patients=new ArrayList<Patient>();
      patients.add(new Patient(null,0,0));
      queueMap=new HashMap<String,Integer>();
  }
  int parent(int i) { return i>>1; } // shortcut for i/2, round down
  
  int left(int i) { return i<<1; } // shortcut for 2*i
  
  int right(int i) { return (i<<1) + 1; } // shortcut for 2*i + 1
  
  void shiftUp(int i) {
	  while(i>1&&patients.get(parent(i)).compareTo(patients.get(i))<0){
		  Patient p=patients.get(i);
		  patients.set(i,patients.get(parent(i)));
		  queueMap.put(patients.get(parent(i)).getName(), i);
		  patients.set(parent(i),p);
		  queueMap.put(p.getName(), parent(i));
		  i=parent(i);
	  }
  }
  void ArriveAtHospital(String patientName, int emergencyLvl) {
	  order++;
	  Patient curr=new Patient(patientName,emergencyLvl,order);
      patients.add(curr);
      queueMap.put(patientName, patients.size()-1);
	  shiftUp(patients.size()-1);
  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
	  Patient curr=patients.get(queueMap.get(patientName));
	  curr.incLevel(incEmergencyLvl);
	  shiftUp(queueMap.get(patientName));
	  
  }
  void shiftDown(int i) {
	    while (i <= patients.size()-1) {
	      Patient maxV = patients.get(i);
	      int max_id = i;
	      if (left(i) <= patients.size()-1 && maxV.compareTo(patients.get(left(i)))<0) { // compare value of this node with its left subtree, if possible
	        maxV = patients.get(left(i));
	        max_id = left(i);
	      }
	      if (right(i) <= patients.size()-1 && maxV.compareTo(patients.get(right(i)))<0) { // now compare with its right subtree, if possible
	        maxV = patients.get(right(i));
	        max_id = right(i);
	      }
	  
	      if (max_id != i) {
	        Patient p = patients.get(i);
	        patients.set(i, patients.get(max_id));
	        queueMap.put(patients.get(max_id).getName(), i);
	        patients.set(max_id, p);
	        queueMap.put(p.getName(), max_id);
	        i = max_id;
	      }
	      else
	        break;
	    }
	  }
	  
  void Treat(String patientName) {
	  int num=queueMap.get(patientName);
	  if(num<patients.size()-1){
      patients.set(num, patients.get(patients.size()-1));
      queueMap.put(patients.get(patients.size()-1).getName(), num);
      patients.remove(patients.size()-1);
      queueMap.remove(patientName);
      shiftDown(num);
      shiftUp(num);
	  }
	  else{
		  queueMap.remove(patientName);
		  patients.remove(patients.size()-1);
		  
	  }
  }

  String Query() {
    String ans = "The emergency suite is empty";
    if(patients.size()>=2)
    	ans=patients.get(1).getName();  
    return ans;
  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: Treat(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    EmergencyRoom ps1 = new EmergencyRoom();
    ps1.run();
  }
}
