package washfriends.washpos.api;

import com.google.api.client.util.Data;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseReference.CompletionListener;

import washfriends.washpos.model.*;
import washfriends.washpos.App;

public class DBManager {
	
	private boolean isExists = false;
	public static final DBManager DB = new DBManager();
	private DatabaseReference userRet;
    
	/**
     * Gets the default FirebaseDatabase instance.
     *
     * @return A FirebaseDatabase instance.
     */
	private DBManager (){
		try {
    		if(FirebaseApp.getInstance() != null && userRet == null) {
    			userRet = FirebaseDatabase.getInstance().getReference().child("user");
    			App.LOGGER.info("DBManager userRet initialized");
    		}
    		if (userRet == null) {
  		      throw new DatabaseException("You must call FirebaseApp.initialize() first.");
    		}
        } catch (Exception e) {
        	App.LOGGER.warning("DBManager failed init " + e.getMessage());
        }
		App.LOGGER.info("DBManager initialized");
	}
    
    // QUERIES
    /**
     * Query if the user exists in the database.
     *
     * @param id        the PID to check
     * @return          true if the user exists, otherwise false.
     */
    public void userExists(final String pid) throws FirebaseException{
        try {
        	userRet.addListenerForSingleValueEvent(new ValueEventListener() {
				public void onDataChange(DataSnapshot snapshot) {
					if(snapshot.exists()) {
						for(DataSnapshot data : snapshot.getChildren()) {
							String uid = data.child("pid").getValue(String.class);
							if (pid.equals(uid)){
								isExists = true;
								App.LOGGER.info("DBManager userExists");
							}
						}
					}
				}
				public void onCancelled(DatabaseError error) {
					new FirebaseException("DBManager user userExists" + error.getDetails());
				}
        	});
        } catch (Exception e) {
        	new FirebaseException("DBManager failed userExists " + e.getMessage());
        }
    }
    
    /**
    * Query if the user exists in the database.
    * @return          true if the user exists, otherwise false.
    */
    public boolean pidExists() {
    	return this.isExists;
    }
    
    /**
     * Query user updates in the database.
     */
    public void updateUser(String name, String pin) throws FirebaseException {
		try {
			User user = new User(name, pin, 0, 0);
			App.USER = user;
			userRet.child(name).setValue(new User(pin, 0, 0), new CompletionListener() {
				public void onComplete(DatabaseError error, DatabaseReference ref) {
					App.LOGGER.info("DBManager successed updateUser " + ref.getKey());
				}
			});
		}catch(Exception e) {
			new FirebaseException("DBManager failed updateUser " + e.getMessage());
		}
   	}
    
    /**
     * Retrieves the user data from the database given a valid
     * pairing of user ID and PIN. Returns null, if invalid.
     *
     * @param id        the student ID
     * @param pin       the PIN given by the user (to be validate)
     * @return          the user data or null if ID and PIN pair invalid.
     */
    public void getUser(final String pid, final String pin) throws FirebaseException {
        try {
        	userRet.addListenerForSingleValueEvent(new ValueEventListener() {
				public void onDataChange(DataSnapshot snapshot) {
					if(snapshot.exists()) {
						for(DataSnapshot data : snapshot.getChildren()) {
							String userPid = data.getValue(String.class);
							if (pid.equals(userPid)){
								String userPin = data.child("pin").getValue(String.class);
								if(pin.equals(userPin)) {
									int point =  data.child("point").getValue(Integer.class);
									int cash =  data.child("cash").getValue(Integer.class);
									App.USER = new User(userPid, userPin, point, cash);
									App.LOGGER.info("DBManager getUser");
								}
							}
						}
					}
				}
				public void onCancelled(DatabaseError error) {
					new FirebaseException("DBManager user getUser" + error.getDetails());
				}
        		
        	});
        	App.LOGGER.info("DBManager successed getUser.");
        } catch (Exception e) {
        	App.LOGGER.warning("DBManager failed getUser " + e.getMessage());
        }
    }
    
    public enum FirebaseRestMethod {	
		GET,
		PATCH,
		PUT,
		POST,
		DELETE;
	}

}
