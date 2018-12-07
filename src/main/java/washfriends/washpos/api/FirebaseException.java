package washfriends.washpos.api;

public class FirebaseException extends Throwable {
	public FirebaseException( String message ) {
		super( message );
	}
	
	public FirebaseException( String message, Throwable cause ) {
		super( message, cause );
	}
}
