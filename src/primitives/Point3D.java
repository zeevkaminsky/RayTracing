package primitives;




/**
 * 
 * Point3D represents a 3DPoint by 3 coordinates
 *
 */
public class Point3D {

	public static final Point3D ZERO_P3D = new Point3D(0, 0, 0);

	private Coordinate x;
	private Coordinate y;
	private Coordinate z;

	/********** Constructors ***********/
	//constructor of three doubles
	public Point3D(double _x, double _y, double _z) {

		this.x = new Coordinate(_x);
		this.y = new Coordinate(_y);
		this.z = new Coordinate(_z);
	}
    //constructor of three coordinates
	public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
		this.x = new Coordinate(_x);
		this.y = new Coordinate(_y);
		this.z = new Coordinate(_z);
	}
    //copy constructor 
	public Point3D(Point3D other) {

		this.x = new Coordinate(other.getX());
		this.y = new Coordinate(other.getY());
		this.z = new Coordinate(other.getZ());

	}

	/************** Getters/Setters *******/
	//return coordinate x
	public Coordinate getX() {
		return new Coordinate(x.get());
	}
	//return coordinate y
	public Coordinate getY() {
		return new Coordinate(y.get());
	}
    //return coordinate z
	public Coordinate getZ() {
		return new Coordinate(z.get());
	}

	/*************** Admin *****************/

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true; 
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D point3D = (Point3D) obj;
		return x.equals(point3D.getX()) && y.equals(point3D.getY()) && z.equals(point3D.getZ());
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

	/************** Operations ***************/
	/**
	 * subtract point from vector
	 * 
	 * @param pt
	 *            the point to subtract
	 * @return the new vector
	 */
	public Vector subtract(Point3D pt) {
		return new Vector(pt, this);
	}

	/**
	 * 
	 * @param other
	 * @return the distance^2 from a giving point
	 */
	public double poewrDistance(Point3D other) {
		double x = Util.uscale((other.getX().get() - getX().get()), (other.getX().get() - getX().get()));
		double y = Util.uscale((other.getY().get() - getY().get()), (other.getY().get() - getY().get()));
		double z = Util.uscale((other.getZ().get() - getZ().get()), (other.getZ().get() - getZ().get()));

		// return (x+y+z);
		return (Util.uadd(x, Util.uadd(y, z)));
	}

	/**
	 * 
	 * @param other
	 * @return the distance
	 */
	public double distance(Point3D other) {
		return Math.sqrt(poewrDistance(other));
	}

	/**
	 * add vector
	 * 
	 * @param v
	 *            is the vector to add
	 * @return the new point
	 */
	public Point3D addVector(Vector v) {
		return new Point3D((x.add(v.getHead().getX())), (y.add(v.getHead().getY())), (z.add(v.getHead().getZ())));
	}
	

}
