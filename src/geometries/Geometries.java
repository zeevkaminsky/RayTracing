/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

/**
 * @create to connect between different geometries
 *
 */
public class Geometries extends Intersectable {

	List<Intersectable> _geometries = new ArrayList<Intersectable>();

	// *******************************constructors************************************//
	/*
	 * constructor add shape to the list
	 * 
	 * @param Intersectable shape
	 */
	public Geometries(Intersectable... shapes) {
		for (Intersectable g : shapes) {
			_geometries.add(g);
		}
		setMin();
		setMax();
	}

	// *******************operations***********************//

	/**
	 * runs of all the shapes , and return list of all the Intersection points
	 * 
	 */
	@Override
	protected List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> pointsList = new ArrayList<GeoPoint>();
		for (Intersectable g : _geometries) {
			pointsList.addAll(g.findIntersections(ray));
		}
		return pointsList;
	}

	/*
	 * add intersectable to Geometries
	 */
	public void add(Intersectable... is) {
		for (Intersectable i : is)
			_geometries.add(i);
		setMin();
		setMax();
	}
	
	@Override
	protected void setMin() {

		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		for (Intersectable is : _geometries) {
			Point3D minP = is.min;
			minX = minP.getX().get() < minX ? minP.getX().get() : minX;
			minY = minP.getY().get() < minY ? minP.getY().get() : minY;
			minZ = minP.getZ().get() < minZ ? minP.getZ().get() : minZ;
		}
		min = new Point3D(minX, minY, minZ);

	}

	@Override
	protected void setMax() {
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		double maxZ = Double.MIN_VALUE;

		for (Intersectable is : _geometries) {
			Point3D maxP = is.max;
			maxX = maxP.getX().get() > maxX ? maxP.getX().get() : maxX;
			maxY = maxP.getY().get() > maxY ? maxP.getY().get() : maxY;
			maxZ = maxP.getZ().get() > maxZ ? maxP.getZ().get() : maxZ;
		}
		max = new Point3D(maxX, maxY, maxZ);
	}
}

