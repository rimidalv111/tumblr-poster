package rimidalv111.main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class TumblrPoster extends JFrame
{
    private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					TumblrPoster frame = new TumblrPoster();
					frame.setVisible(true);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public String dataFile;
	public ArrayList<String> rawData = new ArrayList<String>();
	public ArrayList<Job> jobs = new ArrayList<Job>();
	private JButton btnRunPoster;
	private JTextField titleField;
	private JTree accountsTree;
	private JScrollPane treeScrollPane;

	private JTextArea descriptionField;
	private ConnectThread connectThread;
	private DefaultMutableTreeNode jTree;
	private Config config;
	private AddAccount addAccount;
	private JComboBox<String> accountsComboBox;

	public TumblrPoster()
	{
		
		//set jtree icons
		URL faviconResource = getClass().getResource("favicon.png");
		
		URL resource = getClass().getResource("icon-parent.png");
		Icon iconParent = new ImageIcon(resource);

		URL resourceChild = getClass().getResource("icon-child.png");
		Icon iconChild = new ImageIcon(resourceChild);
		
		UIManager.put("Tree.closedIcon", iconParent);
		UIManager.put("Tree.openIcon", iconParent);
		UIManager.put("Tree.leafIcon", iconChild);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		setTitle("Swifposter [^] - Welcome!");
		setIconImage(new ImageIcon(faviconResource).getImage());
		
		btnRunPoster = new JButton("Submit");
		btnRunPoster.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				submitPosts();
			}
		});
		btnRunPoster.setBounds(555, 319, 108, 23);
		contentPane.add(btnRunPoster);

		titleField = new JTextField();
		titleField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		titleField.setPreferredSize(new Dimension(10, 25));
		titleField.setBounds(237, 41, 426, 36);
		titleField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		contentPane.add(titleField);
		titleField.setColumns(10);

		treeScrollPane = new JScrollPane();
		treeScrollPane.setBounds(10, 11, 217, 297);
		contentPane.add(treeScrollPane);

		jTree = new DefaultMutableTreeNode("Tumblr Blogs");

		accountsTree = new JTree(jTree);
		accountsTree.setBorder(new EmptyBorder(5, 5, 5, 5));
		treeScrollPane.setViewportView(accountsTree);

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setBounds(237, 16, 101, 23);
		contentPane.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(237, 113, 426, 195);
		contentPane.add(scrollPane);

		descriptionField = new JTextArea();
		descriptionField.setFont(new Font("Monospaced", Font.PLAIN, 15));
		descriptionField.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		descriptionField.setLineWrap(true);
		descriptionField.setWrapStyleWord(false);

		scrollPane.setViewportView(descriptionField);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDescription.setBounds(237, 88, 108, 23);
		contentPane.add(lblDescription);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnConnect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				connectToTumblrAccount();
			}
		});
		btnConnect.setBounds(470, 319, 75, 23);
		contentPane.add(btnConnect);

		JButton btnAddAccount = new JButton("Add Tumblr Account");
		btnAddAccount.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAddAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addAccount.setVisible(true);
			}
		});
		btnAddAccount.setBounds(10, 319, 217, 23);
		contentPane.add(btnAddAccount);

		accountsComboBox = new JComboBox<String>();
		accountsComboBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
		accountsComboBox.setBounds(237, 319, 223, 22);
		contentPane.add(accountsComboBox);

		config = new Config(this);

		addAccount = new AddAccount(this);
		
		//System.out.println(parseSpintax(new Random(),"{hi|Hello|Hey}, {this is a|this will be a|this could be a|lets see if this is a} {test|quiz}. Thanks!"));
	}

	public void connectToTumblrAccount()
	{
		String account = String.valueOf(accountsComboBox.getSelectedItem());
		 
		clearTree();
		
		SavedData data = config.getDataByName(account);
		if(data != null)
		{
			connectThread = new ConnectThread(this, data.getConsumerKey(), data.getConsumerSecret(), data.getAuthKey(), data.getAuthSecret());

			connectThread.runConnect();
		}
	}

	public void addBlog(String node)
	{
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(node);
		jTree.add(newNode);
		accountsTree.validate();
		accountsTree.repaint();
	}

	public void clearTree()
	{
		DefaultTreeModel model = (DefaultTreeModel) accountsTree.getModel();
		jTree = (DefaultMutableTreeNode) model.getRoot();
		jTree.removeAllChildren();
        model.reload();
        model.setRoot(jTree);
        
        
		accountsTree.validate();
		accountsTree.repaint();
		treeScrollPane.validate();
		treeScrollPane.repaint();
	}
	
	public void expandAllNodes(JTree tree, int startingIndex, int rowCount)
	{
		for(int i = startingIndex; i < rowCount; ++i)
		{
			tree.expandRow(i);
		}

		if(tree.getRowCount() != rowCount)
		{
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public void submitPosts()
	{
		TreePath[] paths = accountsTree.getSelectionPaths();

		ArrayList<String> blogsLists = new ArrayList<String>();

		for(TreePath path : paths)
		{
			System.out.println("You've selected: " + path.getLastPathComponent());
			blogsLists.add("" + path.getLastPathComponent());
			//post here
		}

		(new JobThread(this, titleField.getText(), descriptionField.getText(), blogsLists)).start();
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
	
	public String getDataFile()
	{
		return dataFile;
	}

	public void setDataFile(String dataFile)
	{
		this.dataFile = dataFile;
	}

	public Config getConfig()
	{
		return config;
	}

	public void setConfig(Config config)
	{
		this.config = config;
	}

	public JComboBox<String> getAccountsComboBox()
	{
		return accountsComboBox;
	}

	public void setAccountsComboBox(JComboBox<String> accountsComboBox)
	{
		this.accountsComboBox = accountsComboBox;
	}

	public ConnectThread getConnectThread()
	{
		return connectThread;
	}

	public void setConnectThread(ConnectThread connectThread)
	{
		this.connectThread = connectThread;
	}

	public DefaultMutableTreeNode getjTree()
    {
    	return jTree;
    }

	public void setjTree(DefaultMutableTreeNode jTree)
    {
    	this.jTree = jTree;
    }

	public JTree getAccountsTree()
    {
    	return accountsTree;
    }

	public void setAccountsTree(JTree accountsTree)
    {
    	this.accountsTree = accountsTree;
    }
}
