package primitives;
/**
 * Ray representing ray by point and vector
 *
 *
 */
public class Ray {
	  // Point of origin
    private Point3D _pOO;

    // Ray direction
    private Vector _direction;

    // ***************** Constructors ********************** //
    //copy constructor
    public Ray(Ray ray){
        this._pOO = ray.getPOO();
        this._direction = ray.getDirection();
    }
    //constructor from point and vector
    public Ray(Point3D poo, Vector direction){
        this._pOO = new Point3D(poo);
        this._direction = direction.normalize();
    }

    // ***************** Getters/Setters ********************** //

    //return direction
    public Vector  getDirection() { return new Vector(_direction); }
    //return point
    public Point3D getPOO()       { return new Point3D(_pOO);	   }
    
 // ***************** Administration  ******************** //

 	@Override
 	public String toString() {
 		return    " { direction: "+ getDirection() + " POO: "+ getPOO() + "}";
 	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) 
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray ray = (Ray) obj;
		return _direction.equals(ray._direction) && _pOO.equals(ray._pOO);
	}
 	

}
