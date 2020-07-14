package test01.DataObject;

public class MessageDataObject {
	private String messageBody;
	private String timeStamp;
	private int messageId;

	public String getMessage() {
		return messageBody;
	}

	public void setMessage(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public int getMessageId() {
		return messageId;
	}
	
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
}
