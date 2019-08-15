package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
/**
 * 
 *Cylinder representing Cylinder by ray, high and radius
 *
 */
public class Cylinder extends Tube {

	private double _high;
	// ***************** Constructors ********************** //

	/**
	 * Constructor of a Cylinder
	 * @param ray the direction of the Cylinder
	 * @param high the high of the Cylinder
	 * @param radius the radius of the Cylinder
	 */
	public Cylinder(Ray ray, double high, double radius,Color color,Material material) {
		super(ray,radius,color ,material);
		_high = high;
	}
	
	// ***************** Getters/Setters ********************** //

	/**
	 * @return the high
	 */
	public double getHigh() {
		return _high;
	}
	
	// ***************** Administration  ******************** //

	@Override
	public String toString() {
		return  super.toString() + " High: "+ getHigh();
	}
	
	// ***************** operations  ******************** //
	 
	 /**
	  * same as tube.just check if the point is on the bottom or up
	  */
	@Override
	public  Vector getNormal(Point3D point) {
		 Vector A = point.subtract(_ray.getPOO());
         double t = A.dotProduct(_ray.getDirection());
       //if orthogonal
         if(Util.isZero(t))
             return _ray.getDirection().scale(-1);
        //if orthogonal
        if( Util.isZero(_high - t))
            return _ray.getDirection();
        return super.getNormal(point);
    }
	
	
}
