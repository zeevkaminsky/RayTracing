/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * LightSource - interface of all sources of light
 * @author Zeev & Michael
 *
 */
public interface LightSource {
	
	/**
	 * 
	 * @return the radius
	 */
	double getRadius();
	/**
	 * 
	 * @return the position of the light
	 */
	Point3D getPosition();

	/**
	 * get intensity the light in a given point 
	 * @param point
	 * @return Intensity
	 */
	Color getIntensity(Point3D point);

	/**
	 * get Vector from the light to the point
	 * @param point to find
	 * @return Vector L
	 */
	Vector getL(Point3D point);

	

	/**
	 * get the distance of the light
	 * @param point
	 * @return
	 */
	Vector getD(Point3D point);
}
