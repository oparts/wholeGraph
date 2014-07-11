import java.util.ArrayList;

public class mergeVM {
  xmlParser xml;
  static int numVM=0; // change
  
  static ArrayList<String> nodeIP = new ArrayList<String>();
  static ArrayList<String> nodeHostName = new ArrayList<String>();
  static ArrayList<String> nodeType = new ArrayList<String>();
  
  static ArrayList<String>[] neighborIP;
  static ArrayList<String>[] neighborHost;
  static ArrayList<String>[] neighborType;

  static ArrayList<String>[] dependCompIP;
  static ArrayList<String>[] dependCompHost;
  
  static ArrayList<String>[] dependConcurIP;
  static ArrayList<String>[] dependConcurHost;
  
  static ArrayList<String>[] dependDistIP;
  static ArrayList<String>[] dependDistHost;
  
  mergeVM() {}
  
public mergeVM(int num)
  {
	  numVM = num;
	  
	  neighborIP = new ArrayList[numVM]; 
	  neighborHost = new ArrayList[numVM];	
	  neighborType = new ArrayList[numVM];
	    
	  dependCompIP = new ArrayList[numVM]; 
	  dependCompHost = new ArrayList[numVM];
	  
	  dependConcurIP = new ArrayList[numVM]; 
	  dependConcurHost = new ArrayList[numVM];
	  
	  dependDistIP = new ArrayList[numVM];
	  dependDistHost = new ArrayList[numVM];
	  
	  initiate();	  
  }

  
  public static void initiate()
  {
	  System.out.println("11 : " + numVM);
	  
	  for(int i = 0; i < numVM; i++)
	  {
		  neighborIP[i] = new ArrayList<String>();
		  neighborHost[i] = new ArrayList<String>();
		  neighborType[i] = new ArrayList<String>();
		  
		  dependCompIP[i] = new ArrayList<String>();
		  dependCompHost[i] = new ArrayList<String>();
		  dependConcurIP[i] = new ArrayList<String>();
		  dependConcurHost[i] = new ArrayList<String>();
		  dependDistIP[i] = new ArrayList<String>();
		  dependDistHost[i] = new ArrayList<String>();
	  }
  }
  
  public static void set_nodeIP(String IP)
  {
	  nodeIP.add(IP);
  }
  
  public static String get_nodeIP(int index)
  {
	  return nodeIP.get(index);
  }
  
  public static void set_nodeHostName(String host)
  {
	  nodeHostName.add(host);
  }
  
// Node
  public static String get_nodeHostName(int index)
  {
	  return nodeHostName.get(index);
  }
  
  public static void set_nodeType(String type)
  {
	  nodeType.add(type);
  }
  
  public static String get_nodeType(int index)
  {
	  return nodeType.get(index);
  }
  
  public static int get_nodeSize()
  {
	  return nodeIP.size();
  }
  
// Neighbor
  public static void set_neighborIP(int index, String IP)
  {
	  neighborIP[index].add(IP);	  
  }
  
  public static void set_neighborHost(int index, String host)
  {
	  neighborHost[index].add(host);
  }
  
  public static void set_neighborType(int index, String type)
  {
	  neighborType[index].add(type);
  }
  
  public static String get_neighborIP(int node, int target)
  {
	  return neighborIP[node].get(target);	  
  }
  
  public static String get_neighborHost(int node, int target)
  {
	  return neighborHost[node].get(target);	
  }  
  
  public static String get_neighborType(int node, int target)
  {
	  return neighborType[node].get(target);	
  }
  
// Dependency  
  public static void set_dependCompIP(int index, String IP)
  {
	  dependCompIP[index].add(IP);	  
  }
  
  public static void set_dependCompHost(int index, String host)
  {
	  dependCompHost[index].add(host);
  }
  
  public static void set_dependConcurIP(int index, String IP)
  {
	  dependConcurIP[index].add(IP);
  }
  
  public static void set_dependConcurHost(int index, String host)
  {
	  dependConcurHost[index].add(host);
  }
  
  public static void set_dependDistIP(int index, String IP)
  {
	  dependDistIP[index].add(IP);
  }
  
  public static void set_dependDistHost(int index, String host)
  {
	  dependDistHost[index].add(host);
  }
  
  public static String get_dependCompIP(int node, int target)
  {
	  return dependCompIP[node].get(target);	  
  }
  
  public static String get_dependCompHost(int node, int target)
  {
	  return dependCompHost[node].get(target);	
  }
  
  public static String get_dependConcurIP(int node, int target)
  {
	  return dependConcurIP[node].get(target);	
  }
  
  public static String get_dependConcurHost(int node, int target)
  {
	  return dependConcurHost[node].get(target);	
  }
  
  public static String get_dependDistIP(int node, int target)
  {
	  return dependDistIP[node].get(target);	
  }
  
  public static String get_dependDistHost(int node, int target)
  {
	  return dependDistHost[node].get(target);	
  }
  
// Return array size  
  public static int get_neighborSize(int index)
  {
	  return neighborIP[index].size();
  }
  
  public static int get_totalDependSize()
  {
	  int totalDepend = 0;
	  
	  for(int i=0; i < numVM; i++)
		  totalDepend = dependCompIP[i].size() + dependConcurIP[i].size() + dependDistIP[i].size();
	 
	  return totalDepend;
  }

  public static int get_dependComposSize(int index)
  {
	  return dependCompIP[index].size();
  }
  
  public static int get_dependConcurSize(int index)
  {
	  return dependConcurIP[index].size();
  }
  
  public static int get_dependDistSize(int index)
  {
	  return dependDistIP[index].size();
  }
  
  public static void printAllInfo()
  {
	  System.out.println("\n\n======= Merged Info. ========");
	  
	  for(int i=0; i < nodeIP.size(); i++)
		  System.out.println("Node IP : " + nodeIP.get(i));
	  
	  for(int i=0; i < nodeHostName.size(); i++)
		  System.out.println("Node host : " + nodeHostName.get(i));

	  for(int i=0; i < nodeType.size(); i++)
		  System.out.println("Node type : " + nodeType.get(i));
	  
	  for(int i=0; i < get_nodeSize(); i++)
	  {
//		  if(get_nodeType(i).equals("client"))
//		  {			  
//			  for(int j=0; j < neighborIP[i].size(); j++)
//				  System.out.println(nodeHostName.get(i) + "(" + nodeIP.get(i) + ") <-> Client/Server : " + neighborHost[i].get(j) + "(" + neighborIP[i].get(j) + ")");		  
//				  System.out.println(nodeIP.get(i) + " <-> Client/Server : " + neighborIP[i].get(j));		  
//		  }
		  
//		  else
//		  {
			  for(int j=0; j < dependCompIP[i].size(); j++)
//				  System.out.println(nodeHostName.get(i) + "(" + nodeIP.get(i) + ") <-> Composite : " + dependCompHost[i].get(j) + "(" + dependCompIP[i].get(j) + ")");
				  System.out.println(nodeIP.get(i) + " <-> Composite : " + dependCompIP[i].get(j));
			  
			  for(int j=0; j < dependConcurIP[i].size(); j++)
//				  System.out.println(nodeHostName.get(i) + "(" + nodeIP.get(i) + ") <-> Concurrent : " + dependConcurHost[i].get(j) + "(" + dependConcurIP[i].get(j) + ")");
				  System.out.println(nodeIP.get(i) + " <-> Concurrent : " + dependConcurIP[i].get(j));

			  for(int j=0; j < dependDistIP[i].size(); j++)
//				  System.out.println(nodeHostName.get(i) + "(" + nodeIP.get(i) + ") <-> Distribute : " + dependDistHost[i].get(j) + "(" + dependDistIP[i].get(j) + ")");
				  System.out.println(nodeIP.get(i) + " <-> Distribute : " + dependDistIP[i].get(j));
	  
//		  }
	  }
  }
 }
