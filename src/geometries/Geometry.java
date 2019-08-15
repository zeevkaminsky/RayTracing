package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
/**
 * 
 * 
 *all classes derived from Geometry must execute getNormal function
 */
public abstract class Geometry extends Intersectable {

	protected Color _emission;
	protected Material _material;
	
	
	/**
	 * @param _emmission
	 * @param _material
	 */
	public Geometry(Color _emission, Material _material) {
		this._emission = _emission;
		this._material = _material;
	}
	
	
	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return _material;
	}
	/**
     * @param material of the Geometry
     */
    public void setMaterial(Material material) {
        _material = new Material(material);
    }
	/**
	 * find the normal to point on geometry
	 * @param point is on the surface of the body
	 * @return the normal
	 */
	public abstract Vector getNormal(Point3D point);
	/**
	 * @return the _emmission
	 */
	public Color get_emission() {
		return this._emission;
	}


	/**
	 * @return the _material
	 */
	public Material get_material() {
		return _material;
	}


	/**
	 * @param _emission the _emission to set
	 */
	public void set_emission(Color _emission) {
		this._emission = _emission;
	}


	
}
