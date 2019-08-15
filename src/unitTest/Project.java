/**
 * 
 */
package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * final project
 * 
 * @author michael & Zeev
 *
 */
class Project {

	@Test
	void test() {
		Scene scene = new Scene("project");
		scene.setcameraAndDistance(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1)), 100);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30), 0.8));
		Scene._checkInersectionWithBox = true;
		Scene._multiThread = true;
		Geometries geometries = new Geometries();
		scene.addGeometry(geometries);
		Material m = new Material(0.2, 0.2,0.3,0.3, 100);
		
		List<Intersectable> I = new ArrayList<>();
		List<Intersectable> I1 = new ArrayList<>();
		List<Intersectable> I2 = new ArrayList<>();
		List<Intersectable> I3 = new ArrayList<>();
		Color color = new Color (0, 0, 255);
		int counter = 0;
		for (int j = 0; j < 120; j+=15) {
			for (int i = 0; i < 120; i+= 15) {	
				geometries.add( new Sphere(new Point3D(50 - i, 50 - j, 45),
					 10,color,m));
				counter ++;
			}
		}
		
		
		
		scene.addLightSource(new SpotLight(new Color(5000, 1000, 1000), new Point3D(-20,-20,0), 
				   0.000005, 0.00001,new Vector(1,1,10), 10, 10));
		scene.addLightSource(new SpotLight(new Color(5000, 1000, 1000), new Point3D(20,20,0), 
				   0.000005, 0.00001,new Vector(-1,-1,10), 10, 10));
		ImageWriter imageWriter = new ImageWriter("project2", 500, 500, 500,500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}

}
