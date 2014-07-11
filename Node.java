
public class Node {
	public static final double FORCE = 0.0001, FRICTION = 0, MIN_LIMIT = 0.000001;
	public static final int NODE_RADIUS=10;
	
	public double x, y, vx, vy;
	
	public Node(){
		x = Math.random();
		y = Math.random();
		
		vx = vy = 0;
	}
	
	public void update(){
		vx *= FRICTION;
		vy *= FRICTION;
		
		if(Math.abs(vx) < MIN_LIMIT) vx = 0;
		if(Math.abs(vy) < MIN_LIMIT) vy = 0;
		
		x += vx;
		y += vy;
	}

}
