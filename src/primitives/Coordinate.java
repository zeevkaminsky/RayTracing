package primitives;

import static primitives.Util.*;

/**
 * coordinate is simply a double on the Number line
 * 
 * @author Zeev & Michael
 *
 */
public final class Coordinate {
	// private static final double EPSILON = 0.0000001;
	protected double _coord;

	public static Coordinate ZERO = new Coordinate(0.0);

	/********** Constructors ***********/
	/**
	 * constractor that set coord
	 * 
	 * @param coord
	 */
	public Coordinate(double coord) {
		// if it too close to zero make it zero
		_coord = alignZero(coord);
	}

	/**
	 * copy constructor
	 * 
	 * @param other
	 */
	public Coordinate(Coordinate other) {
		_coord = other._coord;
	}

	/************** Getters/Setters *******/
	/**
	 * 
	 * @return the coordinate
	 */
	public double get() {
		return _coord;
	}

	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Coordinate))
			return false;
		return usubtract(_coord, ((Coordinate) obj)._coord) == 0.0;
	}

	@Override
	public String toString() {
		return "" + _coord;
	}

	/************** Operations ***************/
	/**
	 * subtract two coordinates
	 * 
	 * @param other coordinate to subtract
	 * @return the new coordinate
	 */
	public Coordinate subtract(Coordinate other) {
		return new Coordinate(usubtract(_coord, other._coord));
	}

	/**
	 * add two coordinates
	 * 
	 * @param other coordinate to add
	 * @return the new coordinate
	 */
	public Coordinate add(Coordinate other) {
		return new Coordinate(uadd(_coord, other._coord));
	}

	/**
	 * 
	 * @param other coordinate to scale with
	 * @return the new coordinate
	 */
	public Coordinate scale(double num) {
		return new Coordinate(uscale(_coord, num));
	}

	/**
	 * multiple two coordinates
	 * 
	 * @param other coordinate to multiply wuth
	 * @return the new coordinate
	 */
	public Coordinate multiply(Coordinate other) {
		return new Coordinate(uscale(_coord, other._coord));
	}

}
