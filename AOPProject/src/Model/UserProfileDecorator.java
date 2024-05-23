package Model;


	public abstract class UserProfileDecorator extends Users implements UserProfile {
	    protected UserProfile decoratedProfile;
	    public UserProfileDecorator() {}

	    public UserProfileDecorator(String userName , String password, int id ,UserProfile decoratedProfile) {
	    	super(userName,password,id);
	        this.decoratedProfile = decoratedProfile;
	    }

	    public void addFriend(String message) {
	         decoratedProfile.myPurrToDtbs(message);
	    }
	}


