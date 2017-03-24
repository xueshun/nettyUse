package bhz.netty.serial;

import java.io.Serializable;

public class Req implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String requestMessage;
	private byte[] attchment;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public byte[] getAttchment() {
		return attchment;
	}
	public void setAttchment(byte[] attchment) {
		this.attchment = attchment;
	}
	
	
}
