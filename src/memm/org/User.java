package memm.org;

public class User {
	
	private Integer id;
	private String username;
	private boolean isAdmin;
	//private User user = null;
	
	//ask public
	
	public User(Integer id, String username, boolean isAdmin) {
		this.id = id;
		this.username = username;
		this.isAdmin = isAdmin;
	}

	public Integer getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}

}
