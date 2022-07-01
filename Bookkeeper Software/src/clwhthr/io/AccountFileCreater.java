package clwhthr.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import clwhthr.account.Account;
import clwhthr.exception.FileExistException;
import clwhthr.setting.Config;

public class AccountFileCreater {
	
	
	public static void create(String name, String passwordHash) throws FileExistException {
		Config config = Config.getInstance();
		Path filePath = Paths.get(config.getAccountPath().toString(), name +".account");
		if(Files.exists(filePath))throw new FileExistException("accountFileHasExist");
		try {
			Files.createFile(filePath);
			AccountFileWriter writer = new AccountFileWriter(filePath.toFile());
			writer.println(passwordHash);
			writer.close();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
	}
	public static void create(Account account) throws FileExistException {
		create(account.getName(), account.getPasswordHash());
	}
}
