package rimidalv111.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddAccount extends JFrame
{
    private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JEditorPane outputPanel;

	private JTextField consumerKeyField;
	private JTextField consumerSecretField;
	private JLabel lblConsumerKey;
	private JLabel lblConsumerSecret;
	private JTextField authKeyField;
	private JTextField authSecretField;
	private JLabel lblAuthKey;
	private JLabel lblAuthSecret;

	private JButton btnAddAccount;
	private JButton btnRemoveAccount;
	private JTextField tumblrSaveNameField;
	private JLabel lblSaveName;

	public AddAccount(final TumblrPoster tp)
	{

		new JFrame("Add Tumblr Account:");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//		URL iconURL = getClass().getResource("favicon.png");
		//		ImageIcon icon = new ImageIcon(iconURL);
		//		contentPane.setIconImage(icon.getImage());

		setSize(new Dimension(500, 210));
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Swifposter - Add Account [^]");
		setResizable(false);

		URL iconURL = getClass().getResource("favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());

		consumerKeyField = new JTextField();
		consumerKeyField.setBounds(10, 27, 217, 20);
		contentPane.add(consumerKeyField);
		consumerKeyField.setColumns(10);

		consumerSecretField = new JTextField();
		consumerSecretField.setBounds(10, 79, 217, 20);
		contentPane.add(consumerSecretField);
		consumerSecretField.setColumns(10);
		//
		lblConsumerKey = new JLabel("Consumer Key:");
		lblConsumerKey.setBounds(10, 9, 91, 14);
		contentPane.add(lblConsumerKey);

		lblConsumerSecret = new JLabel("Consumer Secret:");
		lblConsumerSecret.setBounds(10, 58, 121, 14);
		contentPane.add(lblConsumerSecret);

		authKeyField = new JTextField();
		authKeyField.setBounds(237, 27, 231, 20);
		contentPane.add(authKeyField);
		authKeyField.setColumns(10);

		authSecretField = new JTextField();
		authSecretField.setBounds(237, 79, 231, 20);
		contentPane.add(authSecretField);
		authSecretField.setColumns(10);

		lblAuthKey = new JLabel("Auth Key:");
		lblAuthKey.setBounds(237, 9, 72, 14);
		contentPane.add(lblAuthKey);

		lblAuthSecret = new JLabel("Auth Secret:");
		lblAuthSecret.setBounds(237, 58, 72, 14);
		contentPane.add(lblAuthSecret);

		tumblrSaveNameField = new JTextField();
		tumblrSaveNameField.setBounds(10, 143, 217, 20);
		contentPane.add(tumblrSaveNameField);
		tumblrSaveNameField.setColumns(10);

		lblSaveName = new JLabel("Account Name:");
		lblSaveName.setBounds(10, 123, 121, 14);
		contentPane.add(lblSaveName);

		btnAddAccount = new JButton("Add");
		btnAddAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!tumblrSaveNameField.getText().isEmpty() && !consumerKeyField.getText().isEmpty() && !consumerSecretField.getText().isEmpty() && !authKeyField.getText().isEmpty() && !authSecretField.getText().isEmpty())
				{
					SavedData data = new SavedData();
					data.setDataName(tumblrSaveNameField.getText());
					data.setConsumerKey(consumerKeyField.getText());
					data.setConsumerSecret(consumerSecretField.getText());
					data.setAuthKey(authKeyField.getText());
					data.setAuthSecret(authSecretField.getText());

					tp.getConfig().getSavedData().add(data);
					tp.getConfig().save();
					tp.getConfig().setupComboBox();
					
					consumerKeyField.setText("");
					consumerSecretField.setText("");
					authKeyField.setText("");
					authSecretField.setText("");
					
					consumerKeyField.validate();
					consumerSecretField.validate();
					authKeyField.validate();
					authSecretField.validate();
					
					JOptionPane.showMessageDialog(null, "Saved Account: \r\n" + data.getDataName(), "Add Account [#]", JOptionPane.INFORMATION_MESSAGE);
				} else
				{
					JOptionPane.showMessageDialog(null, "Please Fill Out \r\n All Fields", "Add Account [@]", JOptionPane.INFORMATION_MESSAGE);	
				}
			}
		});
		btnAddAccount.setBounds(237, 110, 231, 23);
		contentPane.add(btnAddAccount);

		btnRemoveAccount = new JButton("Remove");
		btnRemoveAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!tumblrSaveNameField.getText().isEmpty())
				{
					SavedData account = tp.getConfig().getDataByName(tumblrSaveNameField.getText());
					if(account != null)
					{
						tp.getConfig().getSavedData().remove(account);
						tp.getConfig().save();
						tp.getConfig().setupComboBox();
						JOptionPane.showMessageDialog(null, "Removed Account: \r\n" + account.getDataName(), "Add Account [#]", JOptionPane.INFORMATION_MESSAGE);
					} else
					{
						JOptionPane.showMessageDialog(null, "No Account Found: \r\n" + tumblrSaveNameField.getText(), "Remove Account [*]", JOptionPane.INFORMATION_MESSAGE);		
					}
				} else
				{
					JOptionPane.showMessageDialog(null, "Please Specify Account \r\n For Removal", "Remove Account [@]", JOptionPane.INFORMATION_MESSAGE);		
				}
			}
		});
		btnRemoveAccount.setBounds(237, 139, 231, 23);
		contentPane.add(btnRemoveAccount);
		
	}
}
