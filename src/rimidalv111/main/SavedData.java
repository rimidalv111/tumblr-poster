package rimidalv111.main;

public class SavedData
{
	private String dataName;
	private String consumerKey;
	private String consumerSecret;
	private String authKey;
	private String authSecret;

	public String toString()
	{
		return dataName + ":" + consumerKey + ":" + consumerSecret + ":" + authKey + ":" + authSecret;
	}
	
	public String getDataName()
	{
		return dataName;
	}

	public void setDataName(String dataName)
	{
		this.dataName = dataName;
	}

	public String getConsumerKey()
	{
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey)
	{
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret()
	{
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret)
	{
		this.consumerSecret = consumerSecret;
	}

	public String getAuthKey()
	{
		return authKey;
	}

	public void setAuthKey(String authKey)
	{
		this.authKey = authKey;
	}

	public String getAuthSecret()
	{
		return authSecret;
	}

	public void setAuthSecret(String authSecret)
	{
		this.authSecret = authSecret;
	}

}
