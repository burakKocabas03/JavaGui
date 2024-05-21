package Model;

public class Groups {
	private String groupName;
	int groupId;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public Groups() {}
	public Groups(String groupName, int groupId) {
		
		this.groupName = groupName;
		this.groupId = groupId;
	}
	

}
