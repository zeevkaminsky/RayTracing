/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight is the class that represents the Directional Light like the light from the sun
 * @author Zeev & Michael
 *
 */
public class DirectionalLight extends Light implements LightSource{

	Vector _direction;
	Point3D _position = new Point3D(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
	double _radius = 500;

	/**
	 * constructor
	 * @param _direction the direction of light
	 * @param color of light
	 */
	public DirectionalLight(Vector _direction,Color color) {
		super(color);
		_direction = _direction.normalize();
	}
	
	

	


	/* (non-Javadoc)
	 * @see elements.Light#getIntensity()
	 */
	public Color getIntensity(Point3D point) {
		return super.getIntensity();
	}



	@Override
	public Vector getL(Point3D point) {
		return getD(point);
		
		
	}



	@Override
	public Vector getD(Point3D point) {
		return _direction;
		
	}






	@Override
	public double getRadius() {
		return _radius;
	}






	@Override
	public Point3D getPosition() {
		return _position;
	}






	





	
	
	
}
