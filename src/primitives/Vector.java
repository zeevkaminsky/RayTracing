package primitives;

/**
 * 
 * class representing vector by point3D;
 * 
 *
 */
public class Vector {

	private Point3D head;

	// ***************** Constructors ********************** //
	// constructor from point
	public Vector(Point3D _head) {
		if (_head.equals(Point3D.ZERO_P3D))
			throw new IllegalArgumentException("Are you crazy?! You can't create Vector 0");
		this.head = _head;
	}
    // constructor from doubles
	public Vector(double x, double y, double z) {
		Point3D tmp = new Point3D(x, y, z);
		if (tmp.equals(Point3D.ZERO_P3D))
			throw new IllegalArgumentException("Are you crazy?! You can't create Vector 0");
		this.head = tmp;
	}
    // constructor from two points
	public Vector(Point3D pt1, Point3D pt2) {
		Point3D tmp = (new Point3D(pt2.getX().subtract(pt1.getX()), pt2.getY().subtract(pt1.getY()),
				pt2.getZ().subtract(pt1.getZ())));
		if (tmp.equals(Point3D.ZERO_P3D))
			throw new IllegalArgumentException("Are you crazy?! You can't create Vector 0");
		this.head = tmp;
	}
    // copy constructor
	public Vector(Vector direction) {
		head = (direction.getHead());
	}

	// ***************** Getters/Setters ********************** //
	/**
	 * Vector head getter
	 * 
	 * @return the head
	 */
	public Point3D getHead() {
		return head;
	}

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Vector))
			return false;
		Vector vector = (Vector) o;
		return getHead().equals(vector.getHead());
	}

	@Override
	public String toString() {
		return head.toString();
	}

	// ***************** Operations ******************** //
	/**
	 * subtract two Vectors
	 * 
	 * @param other
	 *            Vector
	 * @return new Vector
	 */
	public Vector subtract(Vector other) {
		return head.subtract(other.getHead());

	}

	/**
	 * add two Vectors
	 * 
	 * @param other
	 *            Vector
	 * @return new Vector
	 */
	public Vector add(Vector other) {
		return new Vector((head.getX().add(other.head.getX()).get()), (head.getY().add(other.head.getY()).get()),
				(head.getZ().add(other.head.getZ()).get()));
	}

	/**
	 * 
	 * @param scalingFactor
	 * @return new vector multiple by scalar
	 */
	public Vector scale(double scalingFactor) {

		return new Vector((this.head.getX().scale(scalingFactor).get()), (this.head.getY().scale(scalingFactor).get()),
				(this.head.getZ().scale(scalingFactor).get()));

	}

	/**
	 * 
	 * @param other
	 * @return (a,b,c)(x,y,z) that gives a number
	 */
	public double dotProduct(Vector other) {
		return head.getX().get() * other.getHead().getX().get() + head.getY().get() * other.getHead().getY().get()
				+ head.getZ().get() * other.getHead().getZ().get();
	}

	/**
	 * 
	 * @param vector
	 * @return orthogonal
	 */
	public Vector crossProduct(Vector vector) {

		double x1 = this.head.getX().get();
		double y1 = this.head.getY().get();
		double z1 = this.head.getZ().get();

		double x2 = vector.getHead().getX().get();
		double y2 = vector.getHead().getY().get();
		double z2 = vector.getHead().getZ().get();

		return new Vector(new Point3D(Util.usubtract(Util.uscale(y1, z2), Util.uscale(z1, y2)),
				Util.usubtract(Util.uscale(z1, x2), Util.uscale(x1, z2)),
				Util.usubtract(Util.uscale(x1, y2), Util.uscale(y1, x2))));
	}

	public Vector normalize() {
		double x = this.getHead().getX().get();
		double y = this.getHead().getY().get();
		double z = this.getHead().getZ().get();

		double length = this.length();

		return new Vector(x / length, y / length, z / length);
	}

	/**
	 * @return the length of the vector
	 */
	public double length() {
		return head.distance(Point3D.ZERO_P3D);// calculate the distance from the origin
	}
}
