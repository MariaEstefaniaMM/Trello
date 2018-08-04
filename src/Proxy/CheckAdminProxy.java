package Proxy;

import memm.org.DataBase;

public class CheckAdminProxy implements CheckAdmin {
	private DataBase db = new DataBase();
	
	@Override
	public boolean checkAdmin(String email) {
		System.out.println("Proxy");
		
		if (db.checkAdmin(email)) {
			return true;
		} else {
			return false;
		}
	}

}
