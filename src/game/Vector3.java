package game;

public class Vector3 {
	
	private double x;
	private double y;
	private double z;
	
	public enum Axis {
		XYZ(0), XY(1), YZ(2), XZ(3), X(4), Y(5), Z(6);
		
		public int axis;
		Axis(int valor) {
			axis = valor;
		}
	}
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	public int getZ() {
		return (int)z;
	}
	
	public double getXD() {
		return x;
	}
	public double getYD() {
		return y;
	}
	public double getZD() {
		return z;
	}
	
	public void add(Vector3 v2) {
		this.x += v2.x;
		this.y += v2.y;
		this.z += v2.z;
	}
	
	public void add(double value, Axis axis) {
		switch (axis) {
		case X:
			this.x += value;
			break;
		case Y:
			this.y += value;
			break;
		case Z:
			this.z += value;
			break;
		default:
			break;
		}
	}
	
	public void sub(Vector3 v2) {
		this.x -= v2.x;
		this.y -= v2.y;
		this.z -= v2.z;
	}
	
	public Vector3 mult(double e) {
		return new Vector3(this.x*e, this.y*e, this.z*e);
	}
	
	public Vector3 mult(Vector3 v2) {
		return new Vector3(this.x*v2.x, this.y*v2.y, this.z*v2.z);
	}
	
	public double mod() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public double mod(Axis axis) {
		switch (axis) {
		case XY:
			return Math.sqrt(x*x + y*y);
		case YZ:
			return Math.sqrt(z*z + y*y);
		case XZ:
			return Math.sqrt(x*x + z*z);
		case X:
			return Math.sqrt(x*x);
		case Y:
			return Math.sqrt(y*y);
		case Z:
			return Math.sqrt(z*z);
		default:
			return Math.sqrt(x*x + y*y + z*z);
		}
	}
	
	public double phase() {
		return Math.atan2(y, x);
	}
	
	public void addSpeed(Vector3 speed, double Ts) {
		this.add(new Vector3(speed.x*Ts, speed.y*Ts, speed.z*Ts));
	}
	
	public static double distance(Vector3 v1, Vector3 v2) {
		return Math.sqrt((v1.x-v2.x)*(v1.x-v2.x) + (v1.y-v2.y)*(v1.y-v2.y) + (v1.z-v2.z)*(v1.z-v2.z));
	}
	
	public static Vector3 normalize(Vector3 vector) {
		double length = vector.mod();
	    if (length != 0.0) {
	        float s = 1.0f / (float)length;
	        vector.x = vector.x*s;
	        vector.y = vector.y*s;
	        vector.z = vector.z*s;
	    }
	    return vector;
	}
	
	public void print() {
		System.out.println("x: " + x + "     " + "y: " + y + "     " + "z: " + z);
	}
	
}
