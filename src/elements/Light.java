/**
 * 
 */
package elements;

import primitives.Color;


/**
 * the class Light represents all source of light
 * @author Zeev & Michael
 *
 */
public abstract class Light {

	private Color _color;
	//private double _radius;
	
	
	// ***************** Constructors ********************** //
	/**
	 * Constructor with light intensity
	 * @param _color intensity
	 */
	public Light(Color color) {
		_color = color;
		//_radius = radius;
	}
	//*****************Getters/Setters************************//

//	public double get_radius() {
//		return _radius;
//	}

	/**
	 * 
	 * @return the Color
	 */
	public Color getIntensity()
	{
		return _color;
	}



	



	
}
