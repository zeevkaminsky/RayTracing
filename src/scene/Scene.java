/**
 * 
 */
package scene;

import elements.LightSource;

import java.util.ArrayList;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;

import geometries.Intersectable;
import primitives.Color;

/**
 * Scene class that represents scene
 * 
 * @author Zeev & michael
 *
 */
public class Scene {

	public String _name;
	public int _numOfVectors = 100;
	public Color _background;
	public AmbientLight _ambientLight;
	public Geometries _3DModel;
	public Camera _camera;
	public double _distance;
	public List<LightSource> _lights;
	public static Boolean _multiThread = false;
	public static Boolean _checkInersectionWithBox = false;

	// *************************** Constructors ******************************//
	/**
	 * constructor
	 * 
	 * @param name of the scene
	 */
	public Scene(String name) {
		_name = name;
		_3DModel = new Geometries();
		_lights = new ArrayList<>();
	}

	// ******************** Getters/Setters *****************//

	/**
	 * @param _numOfVectors the _numOfVectors to set
	 */
	public void set_numOfVectors(int _numOfVectors) {
		this._numOfVectors = _numOfVectors;
	}

	/**
	 * get the name of the scene
	 * 
	 * @return name of scene
	 */
	public String getName() {
		return _name;
	}

	/**
	 * get the background of the scene
	 * 
	 * @return the background
	 */
	public Color getBackground() {
		return _background;
	}

	/**
	 * get the ambient light of the scene
	 * 
	 * @return the ambient light
	 */
	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	/**
	 * get all the geometries of the scene
	 * 
	 * @return list of geometries
	 */
	public Geometries getGeometries() {
		return _3DModel;
	}

	/**
	 * get the camera of the scene
	 * 
	 * @return the camera
	 */
	public Camera getCamera() {
		return _camera;
	}

	/**
	 * get the distance from camera
	 * 
	 * @return the distance
	 */
	public double getDistance() {
		return _distance;
	}

	/**
	 * set the Ambient light
	 * 
	 * @param al the ambient light
	 */
	public void setAmbientLight(AmbientLight al) {
		_ambientLight = al;
	}

	/**
	 * set the background color
	 * 
	 * @param c color
	 */
	public void setBackground(Color c) {
		_background = c;
	}

	/**
	 * set camera and distance
	 * 
	 * @param cam the camera
	 * @param d   the distance
	 */
	public void setcameraAndDistance(Camera cam, double d) {
		_camera = cam;
		_distance = d;
	}

	/**
	 * Add intersectable to 3DModel
	 * 
	 * @param is can get multiple or none intersectabls
	 */
	public void addGeometry(Intersectable... is) {
		_3DModel.add(is);
	}

	/**
	 * @return the _lights
	 */
	public List<LightSource> get_lights() {
		return _lights;
	}

	/**
	 * @param _lights the _lights to set
	 */
	public void set_lights(List<LightSource> _lights) {
		this._lights = _lights;
	}

	/**
	 * adding a light source to the lightSource list
	 * 
	 * @param light light to add to scene
	 */
	public void addLightSource(LightSource light) {
		_lights.add(light);
	}

}
