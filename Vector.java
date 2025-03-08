public class Vector {
	private double theta; //theta from horizontal in degrees
	private double vx, vy;
	private double mag; //magnitude of each vector
	
	public Vector(double m, double t, boolean type) { //can take in either magnitude and theta (note that theta is
													  //from the x axis and goes counterclockwise) or components in form (i, j)
		if(type) {
			mag = m;
			theta = t;
			vx = m*Math.cos(Math.toRadians(t));
			vy = m*Math.sin(Math.toRadians(t));
		}
		else {
			vx = m;
			vy = t;
			mag = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
			theta = Math.toDegrees(Math.atan(vy/vx));
			
			//account for proper quadrant
			if(vx<0) {
				theta += 180; 
			}
			if(vx==0) {
				if(vy > 0) {
					theta = 90;
				}
				else if (vy < 0) {
					theta = -90;
				}
			}
		}
	}
	
	public Vector(double m) { //if no theta is given, assume horizontal vector along +x-axis
		mag = m;
		theta = 0;
		vx = mag;
		vy = 0;
	}
	
	
	
	public static Vector add(Vector a, Vector b) {
		double rX = a.vx + b.vx;
		double rY = a.vy + b.vy;
		
		double newMag = Math.sqrt(Math.pow(rX, 2) + Math.pow(rY, 2)); //use vector addition by components
		
		double newTheta = Math.toDegrees(Math.atan(rY/rX));
		
		//account for proper quadrant
		if(rX<0) {
			newTheta += 180; 
		}
		if(rX==0) {
			if(rY > 0) {
				newTheta = 90;
			}
			else if (rY < 0) {
				newTheta = -90;
			}
		}
		
		return new Vector(newMag, newTheta, true);
	}
	
	public static Vector subtract(Vector a, Vector b) { //note that this is a - b, not b - a
		return Vector.add(a, new Vector(-b.mag, b.theta, true));
		//use the concept that we can rewrite a - b as a + (-b), so by flipping the direction we can make this easier
	}
	
	public static double dotProduct(Vector a, Vector b) {
		return a.vx*b.vx + a.vy*b.vy; //don't need to worry about z axis because only in two dimensions
	}
	
	public static double crossProduct(Vector a, Vector b) {
		return a.vx*b.vy - a.vy*b.vx; //note that sign determines direction in z-axis, positive is +z-axis and negative is -z-axis
	}
	
	public static double angleBetween(Vector a, Vector b) {
		double dp = Vector.dotProduct(a, b);
		return Math.toDegrees(Math.acos(dp/(a.mag*b.mag)));
	}
	
	public static Vector projection(Vector a, Vector b) {
		double newMag = ((Vector.dotProduct(a, b)) / Math.pow(b.mag, 2)) * b.mag;
		return new Vector(newMag, b.theta, true); //note that we use b.theta bc we are projecting a onto b
	}
	
	
	
	public void scalarMult(double scalar) {
		this.mag *= scalar;
	}
	
	public void flipXAxis() {
		this.vy *= -1;
	}
	
	public void flipYAxis() {
		this.vx *= -1;
	}
	
	
	
	@Override
	public String toString() {
		return "Vector Magnitude: " + this.mag + "\nVector Angle (above horizontal): " + this.theta;
	}
}
	
