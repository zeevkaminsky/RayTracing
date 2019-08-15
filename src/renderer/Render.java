/**
 * 
 */
package renderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import elements.Light;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;

import static geometries.Intersectable.GeoPoint;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

/**
 * Render renders the image from scene
 * 
 * @author Zeev & michael
 */
public class Render {

	ImageWriter _imageWriter;
	Scene _scene;

	private static final int MAX_CALC_COLOR_LEVEL = 2;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double EPS = 0.1;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param imageWriter ImageWriter object
	 * @param scene       the Scene
	 */
	public Render(ImageWriter imageWriter, Scene scene) {
		_imageWriter = imageWriter;
		_scene = scene;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * @return the imageWriter
	 */
	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	/**
	 * @return the scene
	 */
	public Scene getScene() {
		return _scene;
	}

	// ***************** operations ********************** //
	/**
	 * renders image to the screen
	 */
	public void renderImage() {
		int nx = _imageWriter.getNx();
		int ny = _imageWriter.getNy();
		double distance = _scene.getDistance();
		double width = _imageWriter.getWidth();
		double height = _imageWriter.getHeight();

		ThreadPoolExecutor executor = null;
		if (Scene._multiThread) {
			int cores = Runtime.getRuntime().availableProcessors();
			executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(cores);
		}
		for (int i = 0; i < nx; i++) {
			final int i1 = i;
			Runnable worker = () -> {
				for (int j = 0; j < ny; j++) {
					Ray ray = _scene.getCamera().constructRay(nx, ny, i1, j, distance, width, height);
					List<GeoPoint> intersectionPoints = _scene.getGeometries().findTheIntersections(ray);
					if (intersectionPoints.isEmpty())
						_imageWriter.writePixel(i1, j, _scene.getBackground().getColor());
					else {
						GeoPoint closestPoint = getClosestPoint(intersectionPoints);
						_imageWriter.writePixel(i1, j, calcColor(closestPoint, ray).getColor());
					}
				}
			};
			if (Scene._multiThread)
				executor.execute(worker);
			else
				worker.run();
		}
		if (Scene._multiThread) {
			executor.shutdown();
			try {
				executor.awaitTermination(550, TimeUnit.MINUTES);
			} catch (Exception e) {

			}
		}
	}

	/**
	 * calculate the color of specific point
	 * 
	 * @param geoPoint get point on specific geometry
	 * @param inRay    ray to help us trace
	 * @return the color to a specific point
	 */
	private Color calcColor(GeoPoint geoPoint, Ray inRay) {
		return calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(_scene.getAmbientLight().getIntensity());
	}

	/**
	 * 
	 * @param geopoint get point on specific geometry
	 * @param inRay    ray to help us trace
	 * @param level
	 * @param k
	 * @return
	 */
	private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k) {
		if (level == 0 || k < MIN_CALC_COLOR_K)
			return Color.BLACK;
		Color color = geopoint.geometry.get_emission();
		Vector v = geopoint.point.subtract(_scene.getCamera().getCenterPoint()).normalize();
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		int nShininess = geopoint.geometry.getMaterial().getnShininess();
		double kd = geopoint.geometry.getMaterial().getKd();
		double ks = geopoint.geometry.getMaterial().getKs();
		for (LightSource lightSource : _scene.get_lights()) {
			Vector l = lightSource.getL(geopoint.point);
			if (n.dotProduct(l) * n.dotProduct(v) > 0) {

				// ktr will hold the result of average shadow
				double ktr = 0;
				List<Vector> vectorsFromPointToLight = createVectorsfromPointToLight(lightSource, n, geopoint, l);
				vectorsFromPointToLight.add(l);
				for (Vector vec : vectorsFromPointToLight) {
					ktr += transparency(lightSource, vec, n, geopoint);
				}
				ktr /= vectorsFromPointToLight.size();
				if (!Util.isZero(ktr * k)) {
					Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}

		// Recursive call for a reflected ray
		double kr = geopoint.geometry.get_material().get_kR();
		double kk = kr * k;
		if (kk > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null) {
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kk).scale(kr));
			}
		}

		// Recursive call for a refracted ray
		double kt = geopoint.geometry.get_material().get_kT();
		kk = k * kt;
		if (kk > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, geopoint.point, inRay);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null) {
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kk).scale(kt));
			}
		}
		return color;
	}

	/**
	 * find intersections of new ray
	 * 
	 * @param refractedRay refracted ray
	 * @return the closest point of intersection
	 */
	private GeoPoint findClosestIntersection(Ray refractedRay) {
		List<GeoPoint> points = new ArrayList<GeoPoint>();
		points = _scene.getGeometries().findTheIntersections(refractedRay);
		if (points.isEmpty()) {
			return null;
		}
		return getClosestPoint(points, refractedRay);
	}

	/**
	 * Construct Reflected Ray
	 * 
	 * @param n     normal
	 * @param point point on the geometry
	 * @param inRay direction of the original ray
	 * @return the Reflected Ray
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.getDirection();
		Vector r = inRay.getDirection().subtract(n.scale(2 * v.dotProduct(n)));// the direction of the reflection
		Vector epsV = n.scale(n.dotProduct(r) > 0 ? EPS : -EPS);
		return new Ray(point.addVector(epsV), r);
	}

	/**
	 * Construct Refracted Ray
	 * 
	 * @param n         normal
	 * @param point     point on the geometry
	 * @param direction of the original ray
	 * @return the Refracted Ray
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.getDirection();
		Vector epsV = n.scale(n.dotProduct(v) > 0 ? EPS : -EPS);
		return new Ray(point.addVector(epsV), v);
	}

	/**
	 * check if a giving point need to be shadowed
	 * 
	 * @param l        direction of light
	 * @param geopoint the point to check if shadowed
	 * @return true if the point does not need to be shadowed
	 */

	private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geopoint) {
		// from point to light source
		Vector lightDirection = l.scale(-1);
		Point3D point = moveEpsilon(n, lightDirection, geopoint);

		// create ray from point of geometry, and vector from point to light
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = _scene.getGeometries().findTheIntersections(lightRay);

		double ktr = 1;
		for (GeoPoint gp : intersections)
			ktr *= gp.geometry.get_material().get_kT();
		return ktr;

	}

	/**
	 * 
	 * @param n              normal
	 * @param lightDirection direction of light
	 * @param geopoint       point to move
	 * @return the new point
	 */
	private Point3D moveEpsilon(Vector n, Vector lightDirection, GeoPoint geopoint) {
		Vector epsV = n.scale(n.dotProduct(lightDirection) > 0 ? EPS : -EPS);
		Point3D point = geopoint.point.addVector(epsV);
		return point;

	}

	/**
	 * 
	 * @param lightSource light
	 * @param n           normal
	 * @param geopoint    the point to check
	 * @param l           light direction
	 * @return vectors from light to point
	 */
	private List<Vector> createVectorsfromPointToLight(LightSource lightSource, Vector n, GeoPoint geopoint, Vector l) {
		// create disc in the sphere of light
		Vector ortho1;
		try {
			ortho1 = l.crossProduct(new Vector(1, 0, 0));
		} catch (Exception e) {
			ortho1 = l.crossProduct(new Vector(0, 1, 0));
		}
		Vector ortho2 = l.crossProduct(ortho1);

		double max = lightSource.getRadius();
		double min = -max;
		List<Vector> vectors = new ArrayList<>();

		for (int i = 0; i < _scene._numOfVectors; i++) {
			// Random r = new Random();
			double S1 = Math.random() * (max - min) + min;
			double S2 = Math.random() * (max - min) + min;
			Point3D p = lightSource.getPosition().addVector(ortho1.scale(S1)).addVector(ortho2.scale(S2));
			Vector V = geopoint.point.subtract(p);
			vectors.add(new Vector(V));
		}

		return vectors;

	}

	/**
	 * help function - calculate the specular of the light
	 * 
	 * @param ks             Secular factor
	 * @param l              direction of light
	 * @param n              normal
	 * @param v              direction of camera
	 * @param nShininess     the shininess
	 * @param lightIntensity the intensity
	 * @return Specular color
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		double lnDot = l.dotProduct(n);
		Vector r = l.subtract(n.scale(2 * lnDot));
		double vrDot = -Util.alignZero(v.dotProduct(r));
		if (vrDot <= 0)
			return Color.BLACK;
		double vrn = Math.pow(vrDot, nShininess);
		return lightIntensity.scale(ks * vrn);
	}

	/**
	 * help function - calculate the diffusive of the light
	 * 
	 * @param kd             Diffusive factor
	 * @param l              direction of light
	 * @param n              the normal
	 * @param lightIntensity the intensity
	 * @return diffusive color
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		double lnDot = l.dotProduct(n);
		double k = kd * (lnDot > 0 ? lnDot : -lnDot);
		return lightIntensity.scale(k);
	}

	/**
	 * get the closest point to the center of camera
	 * 
	 * @param intersectionPoints
	 * @return GeoPoint
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
		Point3D POO = _scene.getCamera().getCenterPoint();
		double min = intersectionPoints.get(0).point.poewrDistance(POO);
		GeoPoint result = intersectionPoints.get(0);

		for (GeoPoint geoPoint : intersectionPoints) {
			if (geoPoint.point.poewrDistance(POO) < min) {
				min = geoPoint.point.poewrDistance(POO);
				result = geoPoint;
			}
		}
		return result;
	}

	/**
	 * get the closest point to the to the ray
	 * 
	 * @param intersectionPoints list of intersections
	 * @param ray                exit point
	 * @return
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints, Ray ray) {
		GeoPoint result = intersectionPoints.get(0);
		for (GeoPoint geoPoint : intersectionPoints) {
			if (ray.getPOO().poewrDistance(geoPoint.point) < ray.getPOO().poewrDistance(result.point)) {
				result = geoPoint;
			}
		}
		return result;
	}

	/**
	 * print grid on the image
	 * 
	 * @param interval rows & columns
	 */
	public void printGrid(int interval) {
		for (int i = 1; i < _imageWriter.getNx(); i++) {
			for (int j = 1; j < _imageWriter.getNy(); j++) {
				if (i % interval == 0 || j % interval == 0) {
					_imageWriter.writePixel(j, i, new Color(0, 255, 255).getColor());
				}
			}
		}
		_imageWriter.writeToimage();
	}
}
