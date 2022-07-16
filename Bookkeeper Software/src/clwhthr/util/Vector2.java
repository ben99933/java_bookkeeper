package clwhthr.util;

public class Vector2{
	
	private double x;
	private double y;
	
	public Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	//return unit vector of this
	public Vector2 normalized() {
		double len = length();
		return new Vector2(x / len, y/len);
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public static Vector2 add(Vector2 vector,Vector2 vector2) {
		return new Vector2(vector.x + vector2.x,vector.y + vector2.y);
	}
	public static Vector2 minus(Vector2 vector,Vector2 vector2) {
		return new Vector2(vector.x - vector2.x,vector.y - vector2.y);
	}
	public double dot(Vector2 vector2) {
		return x * vector2.x + y*vector2.y;
	}
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vector2)return this.hashCode() == obj.hashCode();
		return false;
	}
	public Vector2 toNegative() {
		return new Vector2(-x, -y);
	}
	
	
	
}
