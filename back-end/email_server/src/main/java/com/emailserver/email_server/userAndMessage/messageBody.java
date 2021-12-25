package com.emailserver.email_server.userAndMessage;

public class messageBody {

    private String body;
	private int length;
	

	public messageBody(String body, int length) {
		this.body = body;
		this.length = length;
	}


    public void setBody(String body) {
		this.body=body;
	}

	public String getBody() {
		return this.body;
	}

	public void setLength(int l)
	{
		this.length = l;
	}
    
	public int getLength()
	{
		return this.length;
	}
}
