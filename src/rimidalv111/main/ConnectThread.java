package rimidalv111.main;

import javax.swing.JOptionPane;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;

public class ConnectThread extends Thread
{
	private TumblrPoster tumblrPoster;
	
	private JumblrClient client;
	private User user;
	
	public ConnectThread(TumblrPoster tp, String ck, String cs, String at, String as)
	{
		tumblrPoster = tp;
		
		// Create a new client
		client = new JumblrClient(ck, cs);
		client.setToken(at, as);
		System.out.println("Connecting to: ck: " + ck + " cs: " + cs + " at: " + at + " as: " + as);
	}

	public void runConnect()
	{
		//clear the old jtree
		tumblrPoster.getjTree().removeAllChildren();
		
		try
		{
	
			user = client.user();
			System.out.println("Connected to: " + user.getName());
			
			// list their blogs
			for(Blog blog : user.getBlogs())
			{
				System.out.println("Blog: " + blog.getName());
				tumblrPoster.addBlog(blog.getName());
			}
			
			tumblrPoster.expandAllNodes(tumblrPoster.getAccountsTree(), 0, tumblrPoster.getAccountsTree().getRowCount());
		} catch (Exception io)
		{
		
			System.out.println("Could not complete connect! ");
			JOptionPane.showMessageDialog(null, "Failed To Connect!", "Connect [x]", JOptionPane.INFORMATION_MESSAGE);
			io.printStackTrace();
		}
	}

	public TumblrPoster getTumblrPoster()
	{
		return tumblrPoster;
	}

	public void setTumblrPoster(TumblrPoster tumblrPoster)
	{
		this.tumblrPoster = tumblrPoster;
	}

	public JumblrClient getClient()
    {
    	return client;
    }

	public void setClient(JumblrClient client)
    {
    	this.client = client;
    }

	public User getUser()
    {
    	return user;
    }

	public void setUser(User user)
    {
    	this.user = user;
    }
}
