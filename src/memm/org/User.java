package memm.org;

public class User {
	
	private Integer id;
	private String username;
	//private User user = null;
	
	//ask public
	
	public User(Integer id, String username) {
		this.id = id;
		this.username = username;
	}

	public Integer getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

}
