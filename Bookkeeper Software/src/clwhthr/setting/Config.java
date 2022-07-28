package clwhthr.setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import clwhthr.account.Account;
import clwhthr.resources.lang.Language;
import clwhthr.resources.lang.Languages;
import clwhthr.util.Debug;


public final class Config {
	
	private static Config instance;
	private File configFile;
	private Path pathProgramFile;//C:\User\User\document\clwhthr
	private Path pathConfig;//clwhthr/config
	private Path pathAccount;//clwhthr/account
	private Path pathRecords;//clwhthr/records
	
	private List<ConfigOption> options = new LinkedList<ConfigOption>();
	private ConfigOption rememberPassword;
	private ConfigOption lastAccountName;
	private ConfigOption screenSize;
	private ConfigOption language;
	
	private Config() {
		init();
	}
	
	public static synchronized Config getInstance() {
		if(instance == null)instance = new Config();
		return instance;
	}
	
	public void init(){
		setOptions();
		try {
			pathInit();
			fileInit();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		
	}
	
	
	private void pathInit() throws IOException {
		JFileChooser fr = new JFileChooser();
	    FileSystemView fw = fr.getFileSystemView();
	    pathProgramFile = Paths.get(fw.getDefaultDirectory().toString() ,"CLWhthr","Accounting");
	    pathConfig = Paths.get(pathProgramFile.toString(), "config");
	    pathAccount = Paths.get(pathProgramFile.toString(),"account");
	    pathRecords = Paths.get(pathProgramFile.toString(), "records");
	    if(!Files.exists(pathProgramFile))Files.createDirectories(pathProgramFile);
	    if(!Files.exists(pathConfig))Files.createDirectories(pathConfig);
	    if(!Files.exists(pathAccount))Files.createDirectories(pathAccount);
	    if(!Files.exists(pathRecords))Files.createDirectories(pathRecords);
	    configFile = new File(Paths.get(pathConfig.toString(),"config.properties").toString());
	}
	
	private void fileInit() throws IOException{
		boolean firstUse = true;
		if(!Files.exists(configFile.toPath()))Files.createFile(configFile.toPath());
    	else firstUse = false;
		if(firstUse) {
			buildConfigFile();
		}
		else {
			readConfigFile();
		}
	}
	private void setDefaultConfig() {
		rememberPassword = new ConfigOption<Boolean>("RememberPassword", "false");
		lastAccountName = new ConfigOption<String>("LastAccountName", "none");
		screenSize = new ConfigOption<Integer>("ScreenSize","2");
		language = new ConfigOption<String>("Language", Languages.en_US.getName());
	}
	private void buildConfigFile() throws IOException {
		setDefaultConfig();
		save();
	}
	private void readConfigFile() throws IOException {
		Properties configProperties = new Properties();
		FileInputStream inputStream = new FileInputStream(configFile.getPath());
		configProperties.load(inputStream);
		for(ConfigOption option : options) {
			option.setValue(configProperties.getProperty(option.getName()));
		}
		inputStream.close();
	}
	public void save() throws IOException {
		File configFile = new File(Paths.get(pathConfig.toString(),"config.properties").toString());
		Properties configProperties = new Properties();
		for(ConfigOption option : options) {
			configProperties.setProperty(option.getName(), option.getValue().toString());
		}
		FileOutputStream outputStream = new FileOutputStream(configFile.getPath());
		//save
		configProperties.store(outputStream, null);
		outputStream.close();
	}
	
	private void setOptions() {
		setDefaultConfig();
		//add to set
		options.add(rememberPassword);
		options.add(lastAccountName);
		options.add(screenSize);
		options.add(language);
	}
	public String getLastAccountName() {
		if(getRememberPassword() == false)return null;
		else return lastAccountName.getValue();
	}
	public void setLastAccountName(String string) {
		if(getRememberPassword() == false)lastAccountName.setValue("none");
		else lastAccountName.setValue(string);
	}
	public boolean getRememberPassword() {
		return Boolean.valueOf(rememberPassword.getValue());
	}
	public void setRememberPassword(Boolean bool) {
		rememberPassword.setValue(bool.toString());
	}
	public Path getAccountPath() {
		return this.pathAccount;
	}
	public Path getProgrameFilePath() {
		return this.pathProgramFile;
	}
	
	//clwhthr/records/<username>
	public Path getRecordPath(Account account) {
		Path path = Paths.get(pathRecords.toString(), account.getName().toString());
		if(path==null)Debug.log(this.getClass(), "path is null");
		if(Files.exists(path) == false) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}
		}
		return path;
	}
	
	public int getScreenSize() {
		return Integer.valueOf(screenSize.getValue());
	}
	public void setScreenSize(Integer size) {
		screenSize.setValue(size.toString());
	}
	public String getLanguageName() {
		return language.getValue();
	}
	public void setLanguage(Language lang) {
		setLanguage(lang.getName().toString());
	}
	public void setLanguage(String string) {
		language.setValue(string);
	}
	
}
