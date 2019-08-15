package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Color;
import primitives.Coordinate;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * 
 * Plain representing plain by normal and point on the plain
 *
 */
public abstract class Plain extends Geometry {

	private Point3D _point;
	private Vector _normal;
	// ***************** Constructors ********************** //

	

	/**
	 * Constructor from three points
	 *
	 * @param A
	 *            point A
	 * @param B
	 *            point B
	 * @param C
	 *            point C
	 */
	public Plain(Point3D A, Point3D B, Point3D C,Color color ,Material material) {

		//
		super(color,material);
		Vector vectorA = B.subtract(A);
		Vector vectorB = C.subtract(A);
		//
		_normal = vectorA.crossProduct(vectorB).normalize();
		_point = new Point3D(A);
	}

	/**
	 * @param color
	 * @param 
	 * @param point3d 
	 * @param vector
	 */
	public Plain(Point3D point3d, Vector vector , Color color , Material material) {
		super(color, material );
		this._point = point3d ;
		this._normal = vector;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * @return the point
	 */
	public Point3D getPoint() {
		return _point;
	}

	// ***************** Administration ******************** //

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "point:" + _point + " " + "normal:" + _normal;
	}

	// ***************** Operations ******************** //

	/**
	 * get the normal to the plain
	 *
	 * @param point
	 *            is a point on the plain
	 * @return Vector that is normal to the plain
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return _normal;
	}

	/**
	 * finds intersection of ray and plain
	 * 
	 * @param ray
	 *            to find intersection with plain
	 * @return List<Point3d> list which contain all intersections.can be empty.
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> list = new ArrayList<GeoPoint>();
		Vector rayDirection = ray.getDirection();
		Point3D rayPoint = ray.getPOO();

		// the formula is t = (N * (_point - rayPoint))/(N * rayDirection)
		try {
			// (N * (_point - rayPoint))
			if (Util.isZero(rayDirection.dotProduct(_normal))) {
				return list;

			}
			double Numerator = _normal.dotProduct(_point.subtract(rayPoint));
			if (Util.isZero(Numerator)) {
				list.add(new GeoPoint(this,rayPoint));
				return list;
			}
			// (N * rayDirection)
			double denominator = _normal.dotProduct(rayDirection); 
			if (Util.isZero(denominator))
				return list;
			// t = (N * (_point - rayPoint))/(N * rayDirection)
			double t = Numerator / denominator; 

			if (!(Util.isZero(t)) && t > 0) {
				list.add(new GeoPoint(this,rayPoint.addVector(rayDirection.scale(t))));

			}
			return list;
		} catch (Exception e) {// if the vector is 0 no intersections
			return list;
		}
	}

}
