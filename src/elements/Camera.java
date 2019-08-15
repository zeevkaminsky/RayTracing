/**
 * 
 */
package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Camera represent the camera in the scene
 * @author Zeev & Michael
 *
 */
public class Camera {
	private Point3D _centerPoint;
	private Vector _upVector;
	private Vector _toVector;
	private Vector _rightVector;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param centerPoint center point of the camera
	 * @param upVector    up direction
	 * @param toVector    to direction
	 */
	public Camera(Point3D centerPoint, Vector upVector, Vector toVector) {
		double scalar = upVector.dotProduct(toVector);
		if (!Util.isZero(scalar)) { // check if they are orthogonal
			throw new IllegalArgumentException("The Vectors are not orthogonal to each other");
		}
		_centerPoint = new Point3D(centerPoint);
		_upVector = new Vector(upVector).normalize();
		_toVector = new Vector(toVector).normalize();
		_rightVector = _toVector.crossProduct(_upVector).normalize(); 
	}
	// ***************** Getters/Setters ********************** //

	/**
	 *
	 * @return the center point
	 */
	public Point3D getCenterPoint() {
		return _centerPoint;
	}

	/**
	 *
	 * @return the up direction vector
	 */
	public Vector getUpVector() {
		return _upVector;
	}

	/**
	 *
	 * @return the towards direction vector
	 */
	public Vector getToVector() {
		return _toVector;
	}

	/**
	 *
	 * @return the right direction vector
	 */
	public Vector getRightVector() {
		return _rightVector;
	}

	// ***************** Operations ******************** //
	/**
	 * Construct Ray from the Camera to the place in the view plain
	 * 
	 * @param Nx             num of rows
	 * @param Ny             num of columns
	 * @param i              index i
	 * @param j              index j
	 * @param screenDistance Distance between the camera and the view plain
	 * @param screenWidth    real width of the view plain
	 * @param screenHeight   real height of the view plain
	 * @return Ray from the Camera to the view plain
	 */
	public Ray constructRay(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth,
			double screenHeight) {

		if (Nx <= 0 || Ny <= 0 || screenDistance <= 0 || screenWidth <= 0 || screenHeight <= 0)
			throw new IllegalArgumentException("Can not be less than Zero");

		Point3D PC = _centerPoint.addVector(_toVector.scale(screenDistance)); // image center
		double Rx = screenWidth / Nx; // width
		double Ry = screenHeight / Ny; // height

		double iScale = (Nx - 1) / 2.0d;
		double jScale = (Ny - 1) / 2.0d;

		double dx = i - iScale; // the distance between Pij and Pc on the X line
		double dy = j - jScale; // the distance between Pij and Pc on the Y line

		Point3D Pij = PC;
		if (dx != 0) // move right/left
			Pij = Pij.addVector(_rightVector.scale(dx * Rx));

		if (dy != 0) // move up/down
			Pij = Pij.addVector(_upVector.scale(-dy * Ry));

		Vector Vij = Pij.subtract(_centerPoint); // vector i,j
		return new Ray(_centerPoint, Vij);
	}

}
