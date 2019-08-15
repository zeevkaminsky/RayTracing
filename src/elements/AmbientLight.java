/**
 * 
 */
package elements;

import primitives.Color;

/**
 * AmbientLight represents the ambient light by color
 *         scale by number.
 * @author Zeev & Michael 
 *
 */
public class AmbientLight extends Light{

	double _kA;

	/************************ Constructors ***********************/
	/**
	 * constructor by color and double.
	 * @param color the color
	 * @param ka the scalar
	 */
	public AmbientLight(Color color, double ka) {
		super( color.scale(ka));
		_kA = ka;
	}
	
}
