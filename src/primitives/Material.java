/**
 * 
 */
package primitives;

/**
 * Material holds information about the color of geometry
 * @author Zeev & Michael
 *
 */
public class Material {

	private double _Kd;
	private double _Ks;
	private int _nShininess;
	private double _kR;
	private double _kT;

	// ***************** Constructors ********************** //
	/**
	 * Constructor
	 * @param  Kd Diffuse factor
	 * @param Ks Secular factor
	 * @param kr reflection factor 
	 * @param kt transparency factor
	 * @param nShininess object Shininess
	 */
	public Material(double Kd, double Ks,double kr, double kt, int nShininess) {
		_Kd = Kd;
		_Ks = Ks;
		_nShininess = nShininess;
		_kR = kr;
		_kT = kt;
	}

	/**
	 * @return the _kR
	 */
	public double get_kR() {
		return _kR;
	}

	/**
	 * @return the _kT
	 */
	public double get_kT() {
		return _kT;
	}

	/**
	 * Copy Constructor
	 * @param other Material
	 */
	public Material(Material other) {
		_Kd = other.getKd();
		_Ks = other.getKs();
		_nShininess = other.getnShininess();
	}


	// ***************** Getters/Setters ********************** //

	/**
	 * @return the Diffuse factor
	 */
	public double getKd() {
		return _Kd; 
	}

	/**
	 * @return the Secular factor
	 */
	public double getKs() {
		return _Ks;
	}

	/**
	 * @return the object Shininess
	 */
	public int getnShininess() {
		return _nShininess;
	}

	/**
	 * @param _nShininess the _nShininess to set
	 */
	public void set_nShininess(int _nShininess) {
		this._nShininess = _nShininess;
	}

	

	
}
