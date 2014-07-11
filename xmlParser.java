import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
public class xmlParser {

  mergeVM vm;	
  
  static int index = 0;
  
  ArrayList<String> checkDependIP = new ArrayList();
 
  xmlParser() {}
  
  xmlParser(String fileName){
  try {
   File file = new File(fileName);
   
   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
   DocumentBuilder db = dbf.newDocumentBuilder();
   Document doc = db.parse(file);
   
   doc.getDocumentElement().normalize();
   
   System.out.println("\n==== Parsed "+ doc.getDocumentElement().getNodeName() + " ===== "); 
   
   NodeList nodeLst = doc.getElementsByTagName("Local");
   System.out.println("\n** Local **");
   
   for (int s = 0; s < nodeLst.getLength(); s++) {
    Node fstNode = nodeLst.item(s);
    
    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
     Element fstElmnt = (Element) fstNode;
     NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("IP");
     Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
     NodeList fstNm = fstNmElmnt.getChildNodes();
     System.out.println("IP : " + ((Node) fstNm.item(0)).getNodeValue());
     vm.set_nodeIP(((Node) fstNm.item(0)).getNodeValue());
 /*    
     NodeList scdNmElmntLst = fstElmnt.getElementsByTagName("host");
     Element scdNmElmnt = (Element) scdNmElmntLst.item(0);
     NodeList scdNm = scdNmElmnt.getChildNodes();
     System.out.println("host : "+ ((Node) scdNm.item(0)).getNodeValue());
     vm.set_nodeHostName(((Node) scdNm.item(0)).getNodeValue());
     
     NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("type");
     Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
     NodeList lstNm = lstNmElmnt.getChildNodes();
     System.out.println("type : "+ ((Node) lstNm.item(0)).getNodeValue());
     vm.set_nodeType(((Node) lstNm.item(0)).getNodeValue());
*/
     }
 
   }
   
   NodeList neighborLst = doc.getElementsByTagName("Neighbour");
   System.out.println("\n** Neighbors **");
 
 for (int s = 0; s < neighborLst.getLength(); s++) {
  Node fstNeigh = neighborLst.item(s);
  
  if (fstNeigh.getNodeType() == Node.ELEMENT_NODE) {
   Element fstElmnt = (Element) fstNeigh;
   NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("IP");
   Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
   NodeList fstNm = fstNmElmnt.getChildNodes();
   System.out.println("\nIP : " + ((Node) fstNm.item(0)).getNodeValue());
   vm.set_neighborIP(index, ((Node) fstNm.item(0)).getNodeValue());
   
/*   NodeList scdNmElmntLst = fstElmnt.getElementsByTagName("host");
   Element scdNmElmnt = (Element) scdNmElmntLst.item(0);
   NodeList scdNm = scdNmElmnt.getChildNodes();
   System.out.println("host : "+ ((Node) scdNm.item(0)).getNodeValue());
   vm.set_neighborHost(index, ((Node) scdNm.item(0)).getNodeValue());
*/   
   NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("type");
   Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
   NodeList lstNm = lstNmElmnt.getChildNodes();
   System.out.println("type : "+ ((Node) lstNm.item(0)).getNodeValue());
   vm.set_neighborType(index, ((Node) lstNm.item(0)).getNodeValue());
  }
 }
 
	System.out.println("\n** Dependency **");
	
	NodeList depenDistLst = doc.getElementsByTagName("Distributed"); 
//	System.out.println("\n* Distributed *");
	
	for (int s = 0; s < depenDistLst.getLength(); s++) {
	Node fstDistri = depenDistLst.item(s);
	
	if (fstDistri.getNodeType() == Node.ELEMENT_NODE) {
	 Element fstElmnt = (Element) fstDistri;
	 NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("IP");
	 
	 for (int k = 0; k < fstNmElmntLst.getLength(); k++) {
		 Node fstDistIP  = fstNmElmntLst.item(k);
		 
		 if (fstDistIP.getNodeType() == Node.ELEMENT_NODE){
			 Element fstNmElmnt = (Element) fstDistIP;
			 NodeList fstNm = fstNmElmnt.getChildNodes();
			 
			 checkDependIP.add(((Node) fstNm.item(0)).getNodeValue());
			 System.out.println("\nDistributed - IP : " + ((Node) fstNm.item(0)).getNodeValue());
			 vm.set_dependDistIP(index, ((Node) fstNm.item(0)).getNodeValue());
		 }
	 }

/*	 
	 NodeList scdNmElmntLst = fstElmnt.getElementsByTagName("host");
	 Element scdNmElmnt = (Element) scdNmElmntLst.item(0);
	 NodeList scdNm = scdNmElmnt.getChildNodes();
	 System.out.println("host : "+ ((Node) scdNm.item(0)).getNodeValue());
	 vm.set_dependDistHost(index, ((Node) scdNm.item(0)).getNodeValue());
*/	}
	}
	 
	NodeList depenConcurLst = doc.getElementsByTagName("Concurrent"); 
//	System.out.println("\n* Concurrent *");
	
	for (int s = 0; s < depenConcurLst.getLength(); s++) {
	Node fstConcur = depenConcurLst.item(s);
	
	if (fstConcur.getNodeType() == Node.ELEMENT_NODE) {
	 Element fstElmnt = (Element) fstConcur;
	 NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("IP");

	 for (int k = 0; k < fstNmElmntLst.getLength(); k++) {
	 Node fstConIP  = fstNmElmntLst.item(k);
	 
	 if (fstConIP.getNodeType() == Node.ELEMENT_NODE){
	 Element fstNmElmnt = (Element) fstConIP;
	 NodeList fstNm = fstNmElmnt.getChildNodes();
	 
	 int flag = 0;
	 
	 for(int i = 0 ; i < checkDependIP.size() ; i++)
	 {	 
		 if(checkDependIP.get(i).equals(((Node) fstNm.item(0)).getNodeValue()))
			 flag = 1;
	 }
		 
	 if(flag ==0)
	 {
		 checkDependIP.add(((Node) fstNm.item(0)).getNodeValue());
		 System.out.println("\nConcurrent - IP : " + ((Node) fstNm.item(0)).getNodeValue());
		 vm.set_dependConcurIP(index, ((Node) fstNm.item(0)).getNodeValue());
	 }
	 }
	 }
	}
	}
	
	NodeList depenCompLst = doc.getElementsByTagName("Composite");  // D-1
//	System.out.println("\n* Composite *");
	
	for (int s = 0; s < depenCompLst.getLength(); s++) {
	Node fstComp = depenCompLst.item(s);
	
	if (fstComp.getNodeType() == Node.ELEMENT_NODE) {
	 Element fstElmnt = (Element) fstComp;
	 
	NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("IP"); // D-1-1

	for (int k = 0; k < fstNmElmntLst.getLength(); k++) {
		Node fstCompIP = fstNmElmntLst.item(k);	 
		
		if (fstCompIP.getNodeType() == Node.ELEMENT_NODE) {
			Element fstNmElmnt = (Element) fstCompIP;
			NodeList fstNm = fstNmElmnt.getChildNodes();
			
			 int flag = 0;
			 
			 for(int i = 0 ; i < checkDependIP.size() ; i++)
			 {	 
				 if(checkDependIP.get(i).equals(((Node) fstNm.item(0)).getNodeValue()))
					 flag = 1;
			 }
				 
			 if(flag ==0)
			 {
				 checkDependIP.add(((Node) fstNm.item(0)).getNodeValue());
				 System.out.println("\nComposite - IP : " + ((Node) fstNm.item(0)).getNodeValue());
				 vm.set_dependCompIP(index, ((Node) fstNm.item(0)).getNodeValue());
			 }			
	}
	}
	}
	}

  } catch (Exception e) {
   e.printStackTrace();
  }
 }
  
	public static void addIndex()
	{
		index++;
	}
	
	public static int getIndex()
	{
		return index;
	}
}


 