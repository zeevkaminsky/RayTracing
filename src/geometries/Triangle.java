package geometries;

import java.util.List;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

/**
 * Triangle represents triangle by 3 point3D
 * 
 *
 */
public class Triangle extends Plain{

	private Point3D _pointA;
	private Point3D _pointB;
	private Point3D _pointC;

	// ***************** Constructors ********************** //

	/**
	 * Constructor of Triangle from 3 points
	 * 
	 * @param a
	 *            Point A
	 * @param b
	 *            Point B
	 * @param c
	 *            Point C
	 */
	public Triangle(Point3D a, Point3D b, Point3D c ,Color color, Material material) {

		super(a, b, c, color, material);
		this._pointA = new Point3D(a);
		this._pointB = new Point3D(b);
		this._pointC = new Point3D(c);
		setMin();
		setMax();
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * @return pointA
	 */
	public Point3D getPointA() {
		return _pointA;
		
	}

	/**
	 * @return pointB
	 */
	public Point3D getPointB() {
		return _pointB;
	}

	/**
	 * @return pointC
	 */
	public Point3D getPointC() {
		return _pointC;
	}

	// ***************** Administration ******************** //

	@Override
	public String toString() {
		return "A: " + _pointA + " B: " + _pointB + " C: " + _pointC;
	}

	/**
	 * finds intersection of ray with Triangle
	 * 
	 * @param ray
	 *            find intersection with
	 * @return List<Point3d> that contains all the intersections
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> list = super.findIntersections(ray);// get the intersection with the plain contains the triangle
		// if no intersection with plain, of course no intersection with triangle
		if (list.isEmpty())
			return list;

		try {
			Point3D p = ray.getPOO();
			Vector v1 = _pointA.subtract(p);
			Vector v2 = _pointB.subtract(p);
			Vector v3 = _pointC.subtract(p);
			Vector n1 = v1.crossProduct(v2);
			Vector n2 = v2.crossProduct(v3);
			Vector n3 = v3.crossProduct(v1);


			// get the point of intersection with plain and subtract rayPoint
			Vector u = (list.get(0).point.subtract(p));
			double x1 = u.dotProduct(n1);
			double x2 = u.dotProduct(n2);
			double x3 = u.dotProduct(n3);

			// if one or more are 0.0 – no intersection
			if (Util.isZero((u.dotProduct(n1))) || (Util.isZero(u.dotProduct(n2))) || (Util.isZero(u.dotProduct(n3)))) {
				return EMPTY_LIST;
			}
			// if all + or -, the point intersects with triangle
			if (!(x1 > 0 && x2 > 0 && x3 > 0 || x1 < 0 && x2 < 0 && x3 < 0))
				return EMPTY_LIST;
			return list;
		} catch (Exception e) { 
			return EMPTY_LIST;
		}
	}
	
	@Override
	protected void setMax() {
		double X = Double.max(_pointA.getX().get(), Double.max(_pointB.getX().get(), _pointC.getX().get()));
		double Y = Double.max(_pointA.getY().get(), Double.max(_pointB.getY().get(), _pointC.getY().get()));
		double Z = Double.max(_pointA.getZ().get(), Double.max(_pointB.getZ().get(), _pointC.getZ().get()));
		max = new Point3D(X,Y,Z);
	}

	@Override
	protected void setMin() {
		double X = Double.min(_pointA.getX().get(), Double.min(_pointB.getX().get(), _pointC.getX().get()));
		double Y = Double.min(_pointA.getY().get(), Double.min(_pointB.getY().get(), _pointC.getY().get()));
		double Z = Double.min(_pointA.getZ().get(), Double.min(_pointB.getZ().get(), _pointC.getZ().get()));
		min = new Point3D(X,Y,Z);
	}

}
