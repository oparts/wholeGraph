
public class Edge {
	public static final double FORCE = 0.01;
	
	public int first, second;
	public String third, fourth;

	public Edge(int source, int target, String dependType, String direction) {
		this.first = source;
		this.second = target;
		this.third = dependType;
		this.fourth = direction;
	}
/*	
	public static String get_edgeType()
	{
		return third;
	}
*/	
}
