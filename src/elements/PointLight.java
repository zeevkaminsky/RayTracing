/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight represents the light of a point
 * @author Zeev & Michael
 *
 */
public class PointLight extends Light implements LightSource{

	
	Point3D _position;
	double _kC = 1, _kL, _kQ;
	double _radius;
	
	/**
	 * @param _color
	 * @param _position
	 * @param _kL
	 * @param _kQ
	 */
	public PointLight(Color color, Point3D position,  double kL, double kQ,double radius) {
		super(color);
		_radius = radius;
		_position = new Point3D(position);
		_kL = kL;
		_kQ = kQ;
	}

	
	

	/** (non-Javadoc)
	 * @see elements.LightSource#getIntensity(primitives.Point3D)
	 */
	@Override
	public Color getIntensity(Point3D point) {
		//il = (_color)  divided by  (_Kc + (_Kl * d) + (_Kq * (d)^2))
		double d = _position.distance(point);
		double divisionFactor = _kC + (_kL * d) + (_kQ * (d * d));
		return getIntensity().reduce(divisionFactor);
	}




	/** (non-Javadoc)
	 * @see elements.LightSource#getL(primitives.Point3D)
	 */
	@Override
	public Vector getL(Point3D point) {
		return point.subtract(_position).normalize();
	}




	/** (non-Javadoc)
	 * @see elements.LightSource#getD(primitives.Point3D)
	 */
	@Override
	public Vector getD(Point3D point) {
		return getL(point);
	}




	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return _radius;
	}




	@Override
	public Point3D getPosition() {
		// TODO Auto-generated method stub
		return _position;
	}




	




	

}
