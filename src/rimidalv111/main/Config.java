package rimidalv111.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Config
{
	private TumblrPoster tumblrPoster;

	private String dataFile;
	private ArrayList<SavedData> savedData = new ArrayList<SavedData>();

	public Config(TumblrPoster tp)
	{
		tumblrPoster = tp;
		read();
	}

	public void save()
	{
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter((new File(".")).getAbsolutePath() + "/config.tp"));

			for(SavedData data : savedData)
			{
				writer.write(data.toString() + "\r\n");
			}

			writer.flush();
			writer.close();
		} catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void read()
	{
		dataFile = new File(".").getAbsolutePath() + "/config.tp";

		ArrayList<String> rawData = new ArrayList<String>();

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			try
			{

				String line = br.readLine();

				while (line != null)
				{
					rawData.add(line);
					line = br.readLine();
				}
			} finally
			{
				//System.out.println(rawData.toString());
				br.close();
			}
		} catch(Exception io)
		{
			System.out.println("Cant read config file, creating file not there");
			save();
			io.printStackTrace();
		}

		for(String d : rawData)
		{
			String[] spl = d.split(":");
			SavedData data = new SavedData();
			data.setDataName(spl[0]);
			data.setConsumerKey(spl[1]);
			data.setConsumerSecret(spl[2]);
			data.setAuthKey(spl[3]);
			data.setAuthSecret(spl[4]);
			savedData.add(data);
		}
		System.out.println("Loaded Config:" + savedData.toString());
		setupComboBox();
	}

	public void setupComboBox()
	{
		tumblrPoster.getAccountsComboBox().removeAllItems();
		for(SavedData data : savedData)
		{
			tumblrPoster.getAccountsComboBox().addItem(data.getDataName());
			tumblrPoster.getAccountsComboBox().validate();
			tumblrPoster.getAccountsComboBox().repaint();
		}
	}

	public SavedData getDataByName(String name)
	{
		for(SavedData data : savedData)
		{
			if(data.getDataName().equalsIgnoreCase(name))
			{
				return data;
			}
		}
		return null;
	}

	public TumblrPoster getTumblrPoster()
	{
		return tumblrPoster;
	}

	public void setTumblrPoster(TumblrPoster tumblrPoster)
	{
		this.tumblrPoster = tumblrPoster;
	}

	public String getDataFile()
	{
		return dataFile;
	}

	public void setDataFile(String dataFile)
	{
		this.dataFile = dataFile;
	}

	public ArrayList<SavedData> getSavedData()
	{
		return savedData;
	}

	public void setSavedData(ArrayList<SavedData> savedData)
	{
		this.savedData = savedData;
	}
}
