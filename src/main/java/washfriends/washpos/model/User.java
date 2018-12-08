package washfriends.washpos.model;

import washfriends.washpos.App;

/**
 * In-memory representation of an user.
 */
public class User {
	
	private String pid;			// user PID
	private String pin;			// user PIN
	private int point;      	// user point            
	private int cash; 			// user cash
	private String createdAt; 	// created time
	private String lastLoginAt; // lastLogin time
	
	/**
	 * Construct a new user object.
	 *
	 * @param pid           user PID
	 * @param pin 			user PIN
	 * @param point			user Point
	 */
	 public User(String pid, String pin, int point, int cash) {
		 this.pid = pid;
		 this.pin = pin;
	     this.point = point;
	     this.cash = cash;
	 }
	 public User(String pin, int point, int cash) {
		 this.pin = pin;
		 this.point = point;
		 this.cash = cash;
	 }
	 // Getters
	 // Returns true if pid is assigned
	 public String getPid() {return this.pid;} 
	 public boolean hasPid() {return getPid() != null;}
	 // Returns point
	 public int  getPoint() {return this.point;}
	 // Returns true if point are non-zero
	 public boolean hasPoint() {return getPoint() > 0;}
	 // Returns Cash
	 public int getCash() { return this.cash; }
	 // Returns true if cash are non-zero
	 public boolean hasCash() { return getCash() > 0; }
	 // Returns true is cash and point are non-zero
	 public boolean hasCashAndPoint() { return (getCash() + getPoint()) > 0; }
	 // Returns Cash + point
	 public int getPointAndCash() { 
		 return hasCashAndPoint() ? getCash() + getPoint() : 0; 
	 } 
	 // Returns encrypt pid 
	 public String getEncryptPid() {
		 String temp = this.getPid();
		 return temp.substring(0, 3) + "-xxxx-" + temp.substring(7, 11);
	 }
	 
	 public boolean logout() {
		 return true;
	 }
}
