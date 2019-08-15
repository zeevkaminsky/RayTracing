/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * SpotLight represents the light that is source is from a spot
 * 
 * @author Zeev & Michael
 *
 */
public class SpotLight extends PointLight {

	Vector _direction;
	int _power;

	// ***************** Constructors ********************** //
	/**
	 * @param color    the color
	 * @param position the position
	 * @param kL       linear scalar
	 * @param kQ       power scalar
	 */
	public SpotLight(Color color, Point3D position, double kL, double kQ, Vector direction,int power,double radius) {
		super(color, position, kL, kQ,radius);
		_direction = direction.normalize();
		_power = power;
	}

	//************* getters/setters *******************//

	
	
	@Override
	public Color getIntensity(Point3D point) {
		double scale = _direction.dotProduct(getL(point));
		return scale > 0 ? super.getIntensity(point).scale(Math.pow(scale,_power)) : Color.BLACK;

	}

	
	/* (non-Javadoc)
	 * @see elements.PointLight#getD(primitives.Point3D)
	 */
	@Override
	public Vector getD(Point3D point) {
		return _direction;
	}

	

}
