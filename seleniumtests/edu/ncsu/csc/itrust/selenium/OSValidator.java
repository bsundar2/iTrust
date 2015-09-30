/**
 * Class that determines which OS you are running on. Needed for the Selenium test of EditLOINCDataTest.java.
 * Source code pulled from: mkyong, http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
 */

package edu.ncsu.csc.itrust.selenium;

public class OSValidator {
 
	private static String OS = System.getProperty("os.name").toLowerCase();
 
	public static void main(String[] args) {
 
		System.out.println(OS);
 
		
	}
 
	public static boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
	
	public String getOSType() {
		if (isWindows()) {
			return("Windows");
		} else if (isMac()) {
			return("Mac");
		} else if (isUnix()) {
			return("Unix/Linux");
		} else if (isSolaris()) {
			return("Windows");
		} 
		
		return null;
	}
 
}