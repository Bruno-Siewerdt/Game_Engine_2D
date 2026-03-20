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
	
	public void sum(Vector3 v2) {
		this.x += v2.x;
		this.y += v2.y;
		this.z += v2.z;
	}
	
	public void sum(double value, Axis axis) {
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
	
	public static double mod(Vector3 v) {
		return Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
	}
	
	public static double mod(Vector3 v, Axis axis) {
		switch (axis) {
		case XY:
			return Math.sqrt(v.x*v.x + v.y*v.y);
		case YZ:
			return Math.sqrt(v.z*v.z + v.y*v.y);
		case XZ:
			return Math.sqrt(v.x*v.x + v.z*v.z);
		case X:
			return Math.sqrt(v.x*v.x);
		case Y:
			return Math.sqrt(v.y*v.y);
		case Z:
			return Math.sqrt(v.z*v.z);
		default:
			return Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
		}
	}
	
	public static double phase(Vector3 v) {
		return Math.atan2(v.y, v.x);
	}
	
	public void addSpeed(Vector3 speed, double Ts) {
		this.sum(new Vector3(speed.x*Ts, speed.y*Ts, speed.z*Ts));
	}
	
	public static double distance(Vector3 v1, Vector3 v2) {
		return Math.sqrt((v1.x-v2.x)*(v1.x-v2.x) + (v1.y-v2.y)*(v1.y-v2.y) + (v1.z-v2.z)*(v1.z-v2.z));
	}
	
	public static Vector3 normalize(Vector3 vector) {
		double length = mod(vector);
	    if (length != 0.0) {
	        float s = 1.0f / (float)length;
	        vector.x = vector.x*s;
	        vector.y = vector.y*s;
	        vector.z = vector.z*s;
	    }
	    return vector;
	}
	
	public static void print(Vector3 v) {
		System.out.println("x: " + v.x + "     " + "y: " + v.y + "     " + "z: " + v.z);
	}
	
}
