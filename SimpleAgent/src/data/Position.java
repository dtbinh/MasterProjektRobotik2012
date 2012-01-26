package data;

/**
 * Represents a 2D position in the simulator
 * @author sebastian
 */
public class Position
{
	double x;
	double y;
	double yaw;
	
	/**
	 * Constructor creates a Position
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @param yaw Orientation in radians.
	 */
	public Position(double x, double y, double yaw) {
	    setPosition(x, y, yaw);
	}
	/**
	 * Creates a Position with coordinates (0., 0., 0.).
	 */
	public Position() {
	    setPosition(0., 0., 0.);
	}
	/**
	 * Creates a copy position of the given one. 
	 * @param position The position to copy.
	 */
	public Position(Position position){
        setPosition(position);
	}
	/**
	 * Sets this position's coordinates to the ones from the given position.
	 * @param position The position to take the coordinates from.
	 */
	public void setPosition (Position position) {
		if (position != null)
			setPosition(position.getX(), position.getY(), position.getYaw());
	}
	/**
	 * Sets this position coordinates to the primitives given.
	 * @param xv The X coordinate.
	 * @param yv The Y coordinate.
	 * @param av The Angle coordinate.
	 */
	public void setPosition (double xv,double yv,double av){
		this.x = xv;
		this.y = yv;
		this.yaw = av;
	}
	/**
	 * @return The X coordinate.
	 */
	public double getX() { return x; }
	/**
	 * @param x The X value to be set.
	 */
	public void setX(double x) { this.x = x; }
	/**
	 * @return The Y coordinate.
	 */
	public double getY() { return y; }
	/**
	 * @param y The Y value to be set.
	 */
	public void setY(double y) { this.y = y; }
	/**
	 * @return The Angle coordinate.
	 */
	public double getYaw() { return yaw; }
	/**
	 * @param yaw The Angle coordinate to be set.
	 */
	public void setYaw(double yaw) { this.yaw = yaw; }
	/**
	 * 
	 */
	public String toString() {
		return String.format("(%5.2f|%5.2f|%3.0f)",x, y, Math.toDegrees(yaw));
	}
	/**
	 * Calculates if the position is near to the given one considering
	 * the given epsilons.
	 * 
	 * @param pose The distant position.
	 * @param poseEpsilon The positive planar epsilon in meters.
	 * @param angleEpsilon The positive angle epsilon in radians.
	 * @return true if positions are near, false else.
	 */
	public boolean isNearTo(Position pose, double poseEpsilon, double angleEpsilon)
	{
	    if (pose != null)
	    {
	        if (
	            (Math.abs(x - pose.getX()) < poseEpsilon) &&
	            (Math.abs(y - pose.getY()) < poseEpsilon) &&
	            (Math.abs(yaw - pose.getYaw()) < angleEpsilon)
	        )
	        {
	            return true;
	        }
	    }
	    return false;
	}
	/**
	 * Compares this position to the given one.
	 * @param pose @see Position to compare.
	 * @return true if @see Position matches exactly, false otherwise.
	 */
	public boolean equals (Position pose) {
		if (pose == null)
			return false;
		
		if (x == pose.getX() &&
			y == pose.getY() &&
			yaw == pose.getYaw())
		{
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Calculates the planar distance of this position to the given one.
	 * @param position The position to compare to.
	 * @return The planar distance.
	 */
	public double distanceTo(Position position) {
		if (position != null) {
			// Euclidean distance, Pythagoras
			return Math.sqrt(
					Math.pow(
						Math.abs(y-position.getY()),2)
					+ Math.pow(
						Math.abs(x-position.getX()),2));
		} else {
			return Double.MAX_VALUE;
		}
	}
	/**
	 * Performs a homogeneous matrix multiplication to transform this position coordinates
	 * into a global coordinate system referenced by the position given.
	 * 
	 * @param globalRefPose The coordinates (global reference system) to refer to.
	 * @return The global coordinates of this position in reference to the given position's global frame.
	 */
	public Position getGlobalCoordinates(Position globalRefPose)
    {
        if (globalRefPose != null)
        {
            Position globalPose = new Position();

            globalPose.setX(Math.cos(globalRefPose.getYaw())*x - Math.sin(globalRefPose.getYaw())*y + globalRefPose.getX());
            globalPose.setY(Math.sin(globalRefPose.getYaw())*x + Math.cos(globalRefPose.getYaw())*y + globalRefPose.getY());               
            globalPose.setYaw( getRelativeAngle(globalRefPose.getYaw()+yaw) );
            
            return globalPose;
        } else { 
            return null;
        }
    }
	/**
	 * Normalizes an angle to a relative one.
	 * The angle will then be in the range from -PI to PI, where PI is excluded.
	 * 
	 * @param absoluteAngle The angle to be normalized
	 * @return The normalized relative angle.
	 */
	static public double getRelativeAngle(double absoluteAngle)
	{
	    double angle = absoluteAngle % (2*Math.PI);
	   
	    if (angle >= Math.PI)
	        angle -= 2*Math.PI;
	    else
	        if (angle < -Math.PI)
	            angle += 2*Math.PI;

	    return angle;
	}
	/**
	 * Considers this position in polor coordinates.
	 * Take x value as range and yaw as angle.
	 * @return This position's cartesian coordinates.
	 */
	public Position getCartesianCoordinates()
	{
	    return new Position(
    	    x * Math.cos(yaw),
    	    x * Math.sin(yaw),
    	    0.0);
	    
	}
}
