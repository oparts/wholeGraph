import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.*;

class DrawPanel extends JPanel implements ActionListener, MouseListener {
	mergeVM vm;
	public static int FPS = 0;
	
	private int n, m, w, h, dragging;
	private ArrayList<Node> nodeList;
	private ArrayList<Edge> edgeList;
	static ArrayList<String> clientNode = new ArrayList<String>();
	
	static ArrayList<String> group1 = new ArrayList<String>();
	private static File group1File, group2File;
    private static FileWriter group1FileWriter, group2FileWriter;
    private static BufferedWriter group1BufWriter, group2BufWriter;
    private static PrintWriter group1PrintWriter, group2PrintWriter;
    static int group1cnt=0;
    static int group2cnt=0;
	
	private Timer timer;
	
	private double scale, minX, maxX, minY, maxY;
	private int baseX, baseY;
	
	private File nodeFile, edgeFile, wholeFile;
	
    private FileWriter nodeFileWriter, edgeFileWriter, wholeFileWriter;

    // Put a buffered wrappter around it
    private BufferedWriter nodeBufWriter, edgeBufWriter, wholeBufWriter;

    // In turn, wrap the PrintWriter stream around this output stream
    // and turn on the autoflush.
    private PrintWriter nodePrintWriter, edgePrintWriter, wholePrintWriter;
	static int edgeCount=0;
	static int nodeCount=0; 
	

	public DrawPanel(String nodeFileName, String edgeFileName, String wholeFileName) throws IOException {
		
		String targetIP, targetHost, targetType;
		String nodeIP;
	    writeFile(nodeFileName, edgeFileName, wholeFileName);
	    
	    group1.add("10.8.8.1");
	    group1.add("10.8.8.2");
	    group1.add("10.8.8.3");
	    group1.add("10.8.8.4");	    	    
	    group1.add("10.8.8.5");
	    group1.add("10.8.8.6");
	    group1.add("10.8.8.7");
	    group1.add("10.8.8.8");		
	    group1.add("10.8.8.9");
	    group1.add("10.8.8.10");
	    group1.add("10.8.8.11");
	    group1.add("10.8.8.12");	
	   group1.add("192.168.3.14");
 
	    group1BufWriter.write("{\"links\":[");
	    group2BufWriter.write("{\"links\":[");

			n = vm.get_nodeSize(); // the number of nodes
			
			nodeList = new ArrayList<Node>();
			
		    nodeBufWriter.write("{\"nodes\":[");
		    edgeBufWriter.write("{\"links\":[");
			
			for(int i=0; i < n ; i++){
				nodeList.add(new Node());
				
				if(i==0)
				{
					nodeBufWriter.write("{\"name\":\""+ vm.get_nodeIP(i)+ "\",\"group\":0}");
					nodeCount++;
				}				
				else
					nodeBufWriter.write(",{\"name\":\""+ vm.get_nodeIP(i)+ "\",\"group\":0}");
			}

			edgeList = new ArrayList<Edge>();

			for(int i = 0; i < vm.get_nodeSize(); i++ )
			{
				int nodeFlag = 0;
				int clientFlag = 0;
				
					for(int j=0; j< vm.get_dependComposSize(i); j++)
					{
						targetIP = vm.get_dependCompIP(i, j);
//						targetHost = vm.get_dependCompHost(i, j);
						
						for(int k=0; k < vm.get_nodeSize(); k++) // Search target Node
						{
							if(vm.get_nodeIP(k).equals(targetIP))
							{
								for(int l=0; l< vm.get_neighborSize(i); l++)
								{
									if(vm.get_neighborIP(i, l).equals(targetIP))
									{
										targetType = vm.get_neighborType(i, l);
								
										if(targetType.equals("client"))
										{
											edgeList.add(new Edge(i, k, "Composite", "inArrow"));
											if(edgeCount==0)	
											{
												edgeBufWriter.write("{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Composite\"}");
												edgeCount++;
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Composite");
											}
											
											else
											{
												edgeBufWriter.write(",{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Composite\"}");
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Composite");
											}
											System.out.println("VM " + vm.get_nodeIP(i) + " <-- " + vm.get_nodeIP(k) + " Composite" );
										}
										
										else
										{
											edgeList.add(new Edge(i, k, "Composite", "outArrow"));
											if(edgeCount==0)	
											{
												edgeBufWriter.write("{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Composite\"}");
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Composite");
												edgeCount++;
											}
											
											else
											{
												edgeBufWriter.write(",{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Composite\"}");
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Composite");
											}
											System.out.println("VM " + vm.get_nodeIP(i) + " --> " + vm.get_nodeIP(k) + " Composite" );
										}
									}
								}
							}
						}
					}
					
					for(int j=0; j< vm.get_dependConcurSize(i); j++)
					{
						targetIP = vm.get_dependConcurIP(i, j);
//						targetHost = vm.get_dependConcurHost(i, j);
						
						for(int k=0; k < vm.get_nodeSize(); k++) // Search target Node
						{
							if(vm.get_nodeIP(k).equals(targetIP))
							{
								for(int l=0; l< vm.get_neighborSize(i); l++)
								{
									if(vm.get_neighborIP(i, l).equals(targetIP))
									{
										targetType = vm.get_neighborType(i, l);
								
										if(targetType.equals("client"))
										{
											edgeList.add(new Edge(i, k, "Concurrent", "inArrow"));
											if(edgeCount==0)	
											{
												edgeBufWriter.write("{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Concurrent\"}");
												edgeCount++;
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Concurrent");
											}
											
											else
											{
												edgeBufWriter.write(",{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Concurrent\"}");
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Concurrent");
											}
											System.out.println("VM " + vm.get_nodeIP(i) + " <-- " + vm.get_nodeIP(k) + " Concurrent" );
										}
										
										else
										{
											edgeList.add(new Edge(i, k, "Concurrent", "outArrow"));
											if(edgeCount==0)	
											{
												edgeBufWriter.write("{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Concurrent\"}");
												edgeCount++;
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Concurrent");
											}
											
											else
											{
												edgeBufWriter.write(",{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Concurrent\"}");
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Concurrent");
											}
											System.out.println("VM " + vm.get_nodeIP(i) + " --> " + vm.get_nodeIP(k) + " Concurrent" );
										}
									}
								}
							}
						}
					}
					
					for(int j=0; j< vm.get_dependDistSize(i); j++)
					{
						targetIP = vm.get_dependDistIP(i, j);
//						targetHost = vm.get_dependDistHost(i, j);
						
						for(int k=0; k < vm.get_nodeSize(); k++) // Search target Node
						{
							if(vm.get_nodeIP(k).equals(targetIP))
							{
								for(int l=0; l< vm.get_neighborSize(i); l++)
								{
									if(vm.get_neighborIP(i, l).equals(targetIP))
									{
										targetType = vm.get_neighborType(i, l);
								
										if(targetType.equals("client"))
										{
											edgeList.add(new Edge(i, k, "Distributed", "inArrow"));
											if(edgeCount==0)	
											{
												edgeBufWriter.write("{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Distributed\"}");
												edgeCount++;
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Distributed");
											}
											
											else
											{
												edgeBufWriter.write(",{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Distributed\"}");
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Distributed");
											}System.out.println("VM " + vm.get_nodeIP(i) + " <--" + vm.get_nodeIP(k) + " Distributed" );
										}
										
										else
										{
											edgeList.add(new Edge(i, k, "Distributed", "outArrow"));
											if(edgeCount==0)	
											{
												edgeBufWriter.write("{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Distributed\"}");
												edgeCount++;
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Distributed");
											}
											
											else
											{
												edgeBufWriter.write(",{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ vm.get_nodeIP(k) + "\",\"type\":\"Distributed\"}");
												writeGroupFile(vm.get_nodeIP(i),vm.get_nodeIP(k),"Distributed");
											}
											System.out.println("VM " + vm.get_nodeIP(i) + " --> " + vm.get_nodeIP(k) + " Distributed" );
										}
									}
								}
							}
						}
					}
					
					for(int j=0; j < vm.get_neighborSize(i); j++)
					{
						clientFlag = 0;
						targetIP = vm.get_neighborIP(i, j);						
						
						for(int k=0; k < vm.get_nodeSize(); k++) // Search target Node
						{
							if(vm.get_nodeIP(k).equals(targetIP))
								nodeFlag = 1;
						}
					
						if(nodeFlag == 0)
						{							
							if(clientNode.size()==0)
							{
								nodeList.add(new Node());
								if(i==0)
								{
									nodeBufWriter.write("{\"name\":\""+ targetIP+ "\",\"group\":0}");
									nodeCount++;
								}				
								else
									nodeBufWriter.write(",{\"name\":\""+ targetIP+ "\",\"group\":0}");
								clientNode.add(targetIP);
							}
							
							else
							{							
								for(int l=0; l<clientNode.size(); l++)
								{
									if(clientNode.get(l).equals(targetIP))
									{
										clientFlag = 1;
										break;
									}
								}
								
								if(clientFlag == 0)
								{
									nodeList.add(new Node());
									if(i==0)
									{
		//								nodeBufWriter.write("{\"name\":\""+ targetIP+ "\",\"group\":0}");
										nodeCount++;
									}				
									else
		//								nodeBufWriter.write(",{\"name\":\""+ targetIP+ "\",\"group\":0}");
									clientNode.add(targetIP);
	//								vm.set_nodeIP(targetIP);
								}
							}
							
							n = nodeList.size()-1;
							edgeList.add(new Edge(n, i, "request", "inArrow"));
							if(edgeCount==0)	
							{
								edgeBufWriter.write("{\"source\":\""+ targetIP+ "\",\"target\":\""+ vm.get_nodeIP(i) + "\",\"type\":\"Request\"}");
								edgeCount++;
								writeGroupFile(targetIP,vm.get_nodeIP(i),"Request");
							}
							
							else
							{
								edgeBufWriter.write(",{\"source\":\""+ targetIP+ "\",\"target\":\""+ vm.get_nodeIP(i) + "\",\"type\":\"Request\"}");
								writeGroupFile(targetIP,vm.get_nodeIP(i),"Request");
							}	
							System.out.println("VM " + targetIP + " --> " + vm.get_nodeIP(i) + " Request");
						}
					}
					
					for(int j=0; j < vm.get_neighborSize(i); j++)
					{
						clientFlag = 0;
						targetIP = vm.get_neighborIP(i, j);						
					
//						targetType = vm.get_neighborType(i, j);
//						targetHost = vm.get_neighborHost(i, j);
						
						if(vm.get_neighborType(i, j).equals("server"))
						{
								edgeList.add(new Edge(i, j, "request", "outArrow"));
							if(edgeCount==0)	
							{
								edgeBufWriter.write("{\"source\":\""+ vm.get_nodeIP(i) + "\",\"target\":\""+ targetIP + "\",\"type\":\"Request\"}");
								edgeCount++;
								writeGroupFile(vm.get_nodeIP(i), targetIP,"Request");
							}
							
							else
							{
								edgeBufWriter.write(",{\"source\":\""+ vm.get_nodeIP(i)+ "\",\"target\":\""+ targetIP + "\",\"type\":\"Request\"}");
								writeGroupFile(vm.get_nodeIP(i), targetIP,"Request");
							}
							System.out.println("VM " + vm.get_nodeIP(i) + " --> " + targetIP + " Request");
						}
					}
//				}
			}
			nodeBufWriter.write("],");
			edgeBufWriter.write("]}");
			nodePrintWriter.println ();			
			edgePrintWriter.println ();
			
			group1BufWriter.write("]}");
			group2BufWriter.write("]}");
			group1PrintWriter.println ();			
			group2PrintWriter.println ();
			
			String str = "";
			
			BufferedReader reader1 = null;
			BufferedReader reader2 = null;
			BufferedWriter writer = null;
			
			try{
				reader1 = new BufferedReader(new FileReader(nodeFileName));
				reader2 = new BufferedReader(new FileReader(edgeFileName));
			
				writer = new BufferedWriter(new FileWriter(wholeFileName)); 
			
				while((str = reader2.readLine()) != null)
				{
					writer.write(str);
					writer.newLine();
				}
				
				reader1.close();
				reader2.close();
				writer.close();
			}
			catch(Exception e){
                System. out.println(e.getMessage());
			}
	 
		timer = new Timer(FPS, this);
		timer.setInitialDelay(FPS);
		timer.start();
		
		addMouseListener(this);
		
		dragging = -1;
	}
	public static void writeGroupFile(String source, String target, String depend) throws IOException
	{
		int checkSource = 0, checkTarget =0;
		
		  for(int i = 0; i < group1.size(); i++)
		  {
			  if(group1.get(i).equals(source))
			  {	  
				  checkSource = 1;
			  }
			  
			  if(group1.get(i).equals(target))
			  {	  
				  checkTarget = 1;
			  }		  
		  }
		  
		  if( (checkSource == 1) && (checkTarget == 1))
		  {
			  if(group1cnt == 0)
			  {
				  group1BufWriter.write("{\"source\":\""+ source + "\",\"target\":\""+ target + "\",\"type\":\"" + depend + "\"}");
				  group1cnt++;
			  }
			  
			  else
				  group1BufWriter.write(",{\"source\":\""+ source + "\",\"target\":\""+ target + "\",\"type\":\"" + depend + "\"}");
		  }
			  
			  
		  else if ( (checkSource == 0) && (checkTarget == 0))
		  {
			  if(group2cnt == 0)
			  {
				  group2BufWriter.write("{\"source\":\""+ source + "\",\"target\":\""+ target + "\",\"type\":\"" + depend + "\"}");
				  group2cnt++;
			  }
			  
			  else
				  group2BufWriter.write(",{\"source\":\""+ source + "\",\"target\":\""+ target + "\",\"type\":\"" + depend + "\"}");
		  }			  
	}  
	
	public void actionPerformed(ActionEvent e){
		this.repaint();
		timer.restart();
	}

	public void writeFile(String nodeFileName, String edgeFileName, String wholeFileName) {


		group1File = new File("/usr/share/tomcat/webapps/ROOT/jy/json/group1.json");
		group2File = new File("/usr/share/tomcat/webapps/ROOT/jy/json/group2.json");
		
	    nodeFile = new File(nodeFileName );
	    edgeFile = new File(edgeFileName );
	    wholeFile = new File(wholeFileName );

	    // Create a FileWriter stream to a file and then wrap
	    // a PrintWriter object around the FileWriter to print
	    // primitive values as text to the file.
	    try {
	        // Generate FileWriter stream
	    	group1FileWriter = new FileWriter(group1File);
	    	group2FileWriter = new FileWriter(group2File);
	    	
	        nodeFileWriter = new FileWriter(nodeFile);
	        edgeFileWriter = new FileWriter(edgeFile);
	        wholeFileWriter = new FileWriter(wholeFile);

	        // Put a buffered wrappter around it
	        group1BufWriter = new BufferedWriter (group1FileWriter);
	        group2BufWriter = new BufferedWriter (group2FileWriter);
	        
	        nodeBufWriter = new BufferedWriter (nodeFileWriter);
	        edgeBufWriter = new BufferedWriter (edgeFileWriter);
	        wholeBufWriter = new BufferedWriter (wholeFileWriter);

	        // In turn, wrap the PrintWriter stream around this output stream
	        // and turn on the autoflush.
	        group1PrintWriter = new PrintWriter(group1BufWriter,true);
	        group2PrintWriter = new PrintWriter(group2BufWriter,true);
	        
	        nodePrintWriter = new PrintWriter(nodeBufWriter,true);
	        edgePrintWriter = new PrintWriter(edgeBufWriter,true);
	        wholePrintWriter = new PrintWriter(wholeBufWriter,true);
	    }
	    catch (IOException ioe){
	        System.out.println("IO Exception");
	        ioe.printStackTrace();
	    }
	  }
/*	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		Dimension size = getSize();
		Insets insets = getInsets();

		w = size.width - insets.left - insets.right;
		h = size.height - insets.top - insets.bottom;

//		minX = maxX = nodeList.get(0).x;
//		minY = maxY = nodeList.get(0).y;

		for(int i=0; i<nodeList.size(); i++){
			Node f = nodeList.get(i);
			for(int j=0; j<nodeList.size(); j++){
				if(i != j){
					Node s = nodeList.get(j);
					double dist = (f.x-s.x)*(f.x-s.x)+(f.y-s.y)*(f.y-s.y);
					f.vx += (f.x-s.x)/dist*Node.FORCE;
					f.vy += (f.y-s.y)/dist*Node.FORCE;
				}
			}

			minX = Math.min(minX, f.x);
			maxX = Math.max(maxX, f.x);
			minY = Math.min(minY, f.y);
			maxY = Math.max(maxY, f.y);
		}
		
		for(int i=0; i<edgeList.size(); i++){
			Edge edge = edgeList.get(i);
			Node f = nodeList.get(edge.first), s = nodeList.get(edge.second);

			double dist = Math.sqrt((f.x-s.x)*(f.x-s.x)+(f.y-s.y)*(f.y-s.y));
			f.vx -= (f.x-s.x)*dist*Edge.FORCE;
			f.vy -= (f.y-s.y)*dist*Edge.FORCE;
			s.vx += (f.x-s.x)*dist*Edge.FORCE;
			s.vy += (f.y-s.y)*dist*Edge.FORCE;
		}

		double mulW = (w-20)/(maxX-minX), mulH = (h-20)/(maxY-minY);
		if(mulW < mulH){
			scale = mulW;
			baseX = 10;
			baseY = 10+(h-20-(int)Math.round((maxY-minY)*scale))/2;
		} else {
			scale = mulH;
			baseX = 10+(w-20-(int)Math.round((maxX-minX)*scale))/2;
			baseY = 10;
		}
		
		for(int i=0; i< nodeList.size() ; i++)
			nodeList.get(i).update();
		
		if(dragging != -1){
			try {
				Point mouse = getMousePosition();
				double rx, ry;
				rx = minX+(mouse.x-baseX)/scale;
				ry = minY+(mouse.y-baseY)/scale;
				
				Node node = nodeList.get(dragging);
				node.x = rx;
				node.y = ry;
				node.vx = 0;
				node.vy = 0;
			} catch(NullPointerException e){
			}
		}

		for(int i = 0; i < nodeList.size(); i++)
		{
//			if(vm.get_nodeType(i).equals("server"))
				g2d.setColor(Color.black);
			
//			else
//				g2d.setColor(Color.cyan);			
			
			g2d.fillRoundRect(
					getX(i)-Node.NODE_RADIUS,
					getY(i)-Node.NODE_RADIUS,

					Node.NODE_RADIUS*2, Node.NODE_RADIUS*2,
					Node.NODE_RADIUS*2, Node.NODE_RADIUS*2
					);
		}	

		for(int i=0; i< edgeList.size(); i++){
			Edge edge = edgeList.get(i);
			
			if(edge.third.equals("Composite"))
			{
				g2d.setColor(Color.red);
			}
			
			else if(edge.third.equals("Concurrent"))
			{
				g2d.setColor(Color.orange);
				
			}
			
			else if(edge.third.equals("Distributed"))
			{
				g2d.setColor(Color.blue);
			}
			
			else
				g2d.setColor(Color.black);
			
			g2d.drawLine(
					getX(edge.first),
					getY(edge.first),
					getX(edge.second),
					getY(edge.second)
					);
	
			int x1 = getX(edge.first);
			int x2 = getX(edge.second);
			int y1 = getY(edge.first);
			int y2 = getY(edge.second);

			double length = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)); // compute the line's length
//			g2d.translate(x1, y1);
//			double roate = Math.atan((y2-y1)/(x2-x1)); // compute the angle between line and X axis.

			
			// draw arrow
			GeneralPath path = new GeneralPath();
			path.moveTo((float)length, 0);
			path.lineTo((float)length - 10, -5);
			path.lineTo((float)length - 7, 0);
			path.lineTo((float)length - 10, 5);
			path.lineTo((float)length, 0);
			path.closePath();

			Area area = new Area(path);
			g2d.fill(area);


		//	g2d.fillPolygon(getX(edge.first), getY(edge.first), getX(edge.second));
		}		
	}
*/	
	private int getX(int index){
		return baseX+(int)Math.round((nodeList.get(index).x-minX)*scale);
	}
	
	private int getY(int index){
		return baseY+(int)Math.round((nodeList.get(index).y-minY)*scale);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point mouse = getMousePosition();
		for(int i=0; i<n; i++){
			if(Point.distance(mouse.x, mouse.y, getX(i), getY(i)) < Node.NODE_RADIUS*5){
				dragging = i;
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragging = -1;
	}
	
}

public class wholeGraph{
	public wholeGraph(String nodeFileName, String edgeFileName, String wholeFileName) throws IOException {
		initUI(nodeFileName, edgeFileName, wholeFileName);
	}

	public final void initUI(String nodeFileName, String edgeFileName, String wholeFileName) throws IOException {
		DrawPanel dpnl = new DrawPanel(nodeFileName, edgeFileName, wholeFileName);
/*		add(dpnl);

		setSize(1024, 768);
		setTitle("VM Cluster");
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
*/		
	}

	public static void main(String[] args) throws IOException {
	    File[] listReadFiles;
	    int fileNum;
		
	    File readFile = new File("/usr/share/tomcat/webapps/ROOT/jy/xml2/"); 
		listReadFiles = readFile.listFiles(); 
		fileNum = listReadFiles.length;
		
		String nodeFileName = "/usr/share/tomcat/webapps/ROOT/jy/json/node.json";
		String edgeFileName = "/usr/share/tomcat/webapps/ROOT/jy/json/edge.json";
		String wholeFileName = "/usr/share/tomcat/webapps/ROOT/jy/json/wholeGraph.json";
		
		mergeVM vm = new mergeVM(fileNum);
		
		for(File f : listReadFiles) { 
			
			if(f.exists())
			{	
				xmlParser parser = new xmlParser("/usr/share/tomcat/webapps/ROOT/jy/xml2/"+f.getName());
				
				if(fileNum > 1)
				{
					parser.addIndex();					
					fileNum--;
				}		 					
			}
		}
		vm.printAllInfo();
		wholeGraph ex = new wholeGraph(nodeFileName, edgeFileName, wholeFileName);
//		ex.setVisible(true);
	} 
}
