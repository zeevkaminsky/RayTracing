package geometries;

import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Coordinate;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

/**
 * 
 * Sphere representing sphere by centerPoint and radius
 *
 */
public class Sphere extends RadialGeometry {
	private Point3D centerPoint;
	

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param point  center of the Sphere
	 * @param radius the radius of the Sphere
	 */
	public Sphere(Point3D point, double radius, Color color, Material material) {
		super(radius, color, material);
		centerPoint = new Point3D(point);
		setMin();
		setMax();
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * @return the center point
	 */
	public Point3D getPoint() {
		return centerPoint;
	}

	// ***************** Administration ******************** //

	@Override
	public String toString() {
		return centerPoint + " " + super.toString();
	}

	// ***************** Operations ********************** //
	/**
	 * return the normal
	 */
	public Vector getNormal(Point3D point) {
		return point.subtract(centerPoint).normalize();
	}

	/**
	 * finds intersection of ray and Sphere
	 * 
	 * @param ray to find intersection with
	 * @return List<Point3d> that will contain all the intersections
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> list = new ArrayList<GeoPoint>();
		Vector rayDirection = ray.getDirection();
		Point3D rayPoint = ray.getPOO();

		// case centerPoint same as the RayPoint
		if (centerPoint.equals(rayPoint)) {
			list.add(new GeoPoint(this, rayPoint.addVector(rayDirection.scale(radius))));
			return list;
		}

		// u = centerPoint - rayPoint
		Vector u = centerPoint.subtract(rayPoint);
		// tm = u * rayDirection
		double tm = rayDirection.dotProduct(u);
		// distance = sqrt(|u|^2 - tm^2)
		double d = Math.sqrt(u.dotProduct(u) - tm * tm);
		// case the distance is bigger than radius no intersections
		if (d > radius)
			return list;

		// th = sqrt(R^2 - d^2)
		double th = Math.sqrt(radius * radius - d * d);

		double t1 = tm - th;
		double t2 = tm + th;

		if (Util.isZero(t1) || Util.isZero(t2)) {
			list.add(new GeoPoint(this, rayPoint));
		}
		if (Util.isZero(th)) {
			list.add(new GeoPoint(this, rayPoint.addVector(rayDirection.scale(tm))));
		} else {
			if (t1 > 0 && !Util.isZero(t1))// one
				list.add(new GeoPoint(this, rayPoint.addVector(rayDirection.scale(t1))));
			if (t2 > 0 && !Util.isZero(t2)) {// two
				list.add(new GeoPoint(this, rayPoint.addVector(rayDirection.scale(t2))));
			}
		}
		return list;

	}

	@Override
	protected void setMin() {

		double X = centerPoint.getX().get() - radius;
		double Y = centerPoint.getY().get() - radius;
		double Z = centerPoint.getZ().get() - radius;
		min = new Point3D(X,Y,Z);
	}

	@Override
	protected void setMax() {
		double X = centerPoint.getX().get() + radius;
		double Y = centerPoint.getY().get() + radius;
		double Z = centerPoint.getZ().get() + radius;
		max = new Point3D(X,Y,Z);
	}
}
