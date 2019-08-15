package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Util;

/**
 * 
 * all shapes with radius derived from RadialGeometry
 *
 */
public abstract class RadialGeometry extends Geometry {

	protected double radius;

	public RadialGeometry(double radius,Color color,Material material) {
		super(color,material);
		if (radius < 0 || Util.isZero(radius)) {
			throw new ArithmeticException("radius cannot be 0 ot less.");
		}
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "R: " + getRadius();
	}

}
