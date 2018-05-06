package ikaoyaner.util;

import java.io.IOException;
import java.util.Scanner;

public class RuntimeProcessUtil
{
	  public static boolean exsitProcess(String processName)
	  {
		  boolean exsit = false;
		  try
		  {
			  Process process = Runtime.getRuntime().exec("tasklist");
			  @SuppressWarnings("resource")
			Scanner in = new Scanner(process.getInputStream());
			  while (in.hasNextLine())
			  {
				  String p = in.nextLine();
				  if (p.contains(processName)) {
					  exsit = true;
				  }
			  }
		  }
		  catch (Exception localException) {}
		  return exsit;
	  }
	  
	  public static void killProcess(String processName) {
		  String command = "taskkill /F /IM ";
		  command = command + processName;
		  try {
			  Runtime.getRuntime().exec(command);
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }
}