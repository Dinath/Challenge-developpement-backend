package models;

public class User {

	public static final String TABLE_NAME = "entityuser";

	private int id;
	private String entityEmail;
	private String entityPassword;

	private short entityGroupId;

	public String getEntityPassword() {
		return entityPassword;
	}

	public void setEntityPassword(String entityPassword) {
		this.entityPassword = entityPassword;
	}

	public short getEntityGroup() {
		return entityGroupId;
	}

	public void setEntityGroup(short entityGroupId) {
		this.entityGroupId = entityGroupId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntityEmail() {
		return entityEmail;
	}

	public void setEntityEmail(String entityEmail) {
		this.entityEmail = entityEmail;
	}

}
