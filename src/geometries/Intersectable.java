package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public abstract class Intersectable {

	protected Point3D min = new Point3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
	protected Point3D max = new Point3D(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
	/**
	 * empty list to return if the list empty to save run time...
	 */
	public static final List<GeoPoint> EMPTY_LIST = new ArrayList<GeoPoint>();

	/**
	 * A function that will receive a ray and return points of cutting with geometry
	 * 
	 * @param ray
	 * @return List<GeoPoint> of the all intersections
	 */
	protected abstract List<GeoPoint> findIntersections(Ray ray);

	public List<GeoPoint> findTheIntersections(Ray ray) {
		return Scene._checkInersectionWithBox && !isIntersectedBox(ray) ? EMPTY_LIST :
			findIntersections(ray);
	}
	
	/**
	 * check intersection with box that holds the geometry
	 * 
	 * @param ray ray to intersection with
	 * @return true if bounding box is intersected by the ray else return false
	 */
	protected boolean isIntersectedBox(Ray ray) {

		Point3D rayPoint = ray.getPOO();
		Point3D rayDirection = ray.getDirection().getHead();

		double rayPointX = rayPoint.getX().get();
		double rayPointY = rayPoint.getY().get();
		double rayPointZ = rayPoint.getZ().get();

		double rayDirX = rayDirection.getX().get();
		double rayDirY = rayDirection.getY().get();
		double rayDirZ = rayDirection.getZ().get();

		double tMin = (min.getX().get() - rayPointX) / rayDirX;
		double tMax = (max.getX().get() - rayPointX) / rayDirX;

		if (tMin > tMax) {
			double temp = tMin;
			tMin = tMax;
			tMax = temp;
		}

		double tyMin = (min.getY().get() - rayPointY) / rayDirY;
		double tyMax = (max.getY().get() - rayPointY) / rayDirY;

		if (tyMin > tyMax) {
			double temp = tyMin;
			tyMin = tyMax;
			tyMax = temp;
		}

		if (tMin > tyMax || tMax < tyMin)
			return false;

		if (tyMin > tMin)
			tMin = tyMin;

		if (tyMax < tMax)
			tMax = tyMax;

		double tzMin = (min.getZ().get() - rayPointZ) / rayDirZ;
		double tzMax = (max.getZ().get() - rayPointZ) / rayDirZ;

		if (tzMin > tzMax) {
			double temp = tzMin;
			tzMin = tzMax;
			tzMax = temp;
		}

		return tMin <= tzMax && tMax >= tzMin;
	}

	/**
	 * set the min and max X, Y, Z for each Geometry
	 */
	protected abstract void setMin();

	protected abstract void setMax();

	/**
	 * static class represent point and the geometry holds the point
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		GeoPoint(Geometry g, Point3D p) {
			geometry = g;
			point = p;
		}
	}

}
