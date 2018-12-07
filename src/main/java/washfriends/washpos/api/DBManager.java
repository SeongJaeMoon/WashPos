package washfriends.washpos.api;

import com.google.api.client.util.Data;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

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
     * @param id        the student ID to check
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
    
    public boolean pidExists() {
    	return this.isExists;
    }
    
    /**
     * Retrieves the user data from the database given a valid
     * pairing of user ID and PIN. Returns null, if invalid.
     *
     * @param id        the student ID
     * @param pin       the PIN given by the user (to be validate)
     * @return          the user data or null if ID and PIN pair invalid.
     */
    
//    if(pin == data.child("pin").getValue(Integer.class)) {
//		isExists = true;
//	}else {
//		isExists = false;
//	}
    public User getUser(final String pid, final int pin) {
        try {
        	
        	App.LOGGER.info("DBManager getUser.");
        } catch (Exception e) {
        	App.LOGGER.warning("DBManager failed getUser " + e.getMessage());
        }
        return null;
    }
    
    public enum FirebaseRestMethod {	
		GET,
		PATCH,
		PUT,
		POST,
		DELETE;
	}
}
