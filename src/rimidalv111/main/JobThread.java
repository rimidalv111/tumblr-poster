package rimidalv111.main;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.User;

public class JobThread extends Thread
{
	private TumblrPoster tumblrPoster;
	private Job job;

	private String title;
	private String text;
	private ArrayList<String> blogs;

	public JobThread(TumblrPoster tp, String ti, String te, ArrayList<String> b)
	{
		tumblrPoster = tp;
		title = ti;
		text = te;
		blogs = b;
	}

	public void run()
	{
		try
		{

			if(tumblrPoster.getConnectThread().getClient() != null)
			{
				User user = tumblrPoster.getConnectThread().getClient().user();
				System.out.println("Connected to: " + user.getName());

				for(String blogNames : blogs)
				{
					//update with post
					TextPost newPost = tumblrPoster.getConnectThread().getClient().newPost(blogNames, TextPost.class);
					
					String getParsedTitle = parseSpintax(new Random(), title);
					String getParsedBody = parseSpintax(new Random(), text);
					System.out.println("Title: " + getParsedTitle);
					System.out.println("Body: " + getParsedBody);
					
					newPost.setTitle(getParsedTitle);
					newPost.setBody(getParsedBody);
					newPost.save();

					//sleep
					Thread.sleep(2);
				}

				JOptionPane.showMessageDialog(null, "Successfully Submitted!", "Submitted [/]", JOptionPane.INFORMATION_MESSAGE);

			} else
			{
				JOptionPane.showMessageDialog(null, "Sometimes Errors Happen!", "Submitted [x]", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch(Exception io)
		{
			System.out.println("Could not complete job! ");
			JOptionPane.showMessageDialog(null, "Sometimes Errors Happen!", "Submitted [x]", JOptionPane.INFORMATION_MESSAGE);
			io.printStackTrace();
		}
	}

	static String parseSpintax(Random rnd,String str)
	{
	    String pat = "\\{[^{}]*\\}";
	    Pattern ma; 
	    ma = Pattern.compile(pat);
	    Matcher mat = ma.matcher(str);
	    while(mat.find()) //when we find patter {||}
	    {
	        String segono = str.substring(mat.start() + 1,mat.end() - 1); //remove { }
	        String[] choies = segono.split("\\|",-1); //split by |
	        str = str.substring(0, mat.start()) + choies[rnd.nextInt(choies.length)].toString() + str.substring(mat.start()+mat.group().length()); //pick random
	        mat = ma.matcher(str); //
	    }
	    return str;
	}
	
	public TumblrPoster getTumblrPoster()
	{
		return tumblrPoster;
	}

	public void setTumblrPoster(TumblrPoster tumblrPoster)
	{
		this.tumblrPoster = tumblrPoster;
	}

	public Job getJob()
	{
		return job;
	}

	public void setJob(Job job)
	{
		this.job = job;
	}
}
