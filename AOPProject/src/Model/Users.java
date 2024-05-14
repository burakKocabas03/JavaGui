package Model;

public class Users {
	
	public Users(String userName, String passWord , int id) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
	}
	public Users() {}
	private int id;
	private String userName;
	private String passWord;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
