package washfriends.washpos.model;

/**
 * In-memory representation of an user.
 */
public class User {
	
	private String pid;		// user PID
	private int pin;		// user PIN
	private int point;      // user point
	private boolean ftime; 	// first time using the application              
	
	/**
	 * Construct a new user object.
	 *
	 * @param pid           user PID
	 * @param pin 			user PIN
	 * @param point			user Point
	 * @param ftime         first time using the application
	 */
	 public User(String pid, int pin, int point, boolean ftime) {
		 this.pid = pid;
		 this.pin = pin;
	     this.point = point;
	     this.ftime = ftime;
	 }
	 // Getters
	
	 // Returns point
	 public double  getPoint() {return point;}
	 // Returns true if pid is assigned
	 public String getPid() {return this.pid;} 
	 public boolean hasPid() {return getPid() != null;}    
	 // Returns true if point are non-zero
	 public boolean hasPoint() {return getPoint() > 0;}        
	 // First time using the application
	 public boolean isFirstTime() {return ftime;}
	 
	 public boolean logout() {
		 return false;
	 }
}
