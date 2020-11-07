package rimidalv111.main;

public class Post
{
	private String blogName;
	private String postTitle;
	private String postText;

	public String toString()
	{
		return "Blog Name: " + blogName + " Post Title: " + postTitle + " Post Text: " + postText;
	}
	
	public String getBlogName()
	{
		return blogName;
	}

	public void setBlogName(String blogName)
	{
		this.blogName = blogName;
	}

	public String getPostTitle()
	{
		return postTitle;
	}

	public void setPostTitle(String postTitle)
	{
		this.postTitle = postTitle;
	}

	public String getPostText()
	{
		return postText;
	}

	public void setPostText(String postText)
	{
		this.postText = postText;
	}
}
