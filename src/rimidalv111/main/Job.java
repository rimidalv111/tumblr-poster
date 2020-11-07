package rimidalv111.main;

import java.util.ArrayList;

public class Job
{
	private String consumerKey;
	private String consumerSecret;
	private String authToken;
	private String authSecret;

	private String email;
	private String password;

	private ArrayList<Post> posts = new ArrayList<Post>();

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

	public String getAuthToken()
	{
		return authToken;
	}

	public void setAuthToken(String authToken)
	{
		this.authToken = authToken;
	}

	public String getAuthSecret()
	{
		return authSecret;
	}

	public void setAuthSecret(String authSecret)
	{
		this.authSecret = authSecret;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public ArrayList<Post> getPosts()
	{
		return posts;
	}

	public void setPosts(ArrayList<Post> posts)
	{
		this.posts = posts;
	}
}
