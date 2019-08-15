package geometries;

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
 * Tube representing tube by ray and radius
 *
 */
public class Tube extends RadialGeometry {

	protected Ray _ray;

	/**
	 * Constructor of a Tube
	 * 
	 * @param ray
	 *            the direction of the Tube
	 * @param radius
	 *            the radius of the tube
	 */
	public Tube(Ray ray, double radius, Color color, Material material) {
		super(radius,color ,material);
		_ray = new Ray(ray);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * @return the ray
	 */
	public Ray getRay() {
		return _ray;
	}

	// ***************** Administration ******************** //

	@Override
	public String toString() {
		return super.toString() + " Ray: " + getRay();
	}

	/**
	 * get normal to the Tube 
	 *
	 * @param point on the Tube
	 * @return The normal Vector
	 */
	public Vector getNormal(Point3D point) {
		// t = (point-POO)*rayDirection
		Vector A = point.subtract(_ray.getPOO());
		double t = A.dotProduct(_ray.getDirection());
		// POO + t * rayDirection
		Point3D O = _ray.getPOO().addVector(_ray.getDirection().scale(t));
		// point - O = n
		return point.subtract(O).normalize();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setMax() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setMin() {
		// TODO Auto-generated method stub
		
	}

}
