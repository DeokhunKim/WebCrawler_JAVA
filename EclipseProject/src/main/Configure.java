package main;

public class Configure {
	boolean isTryReconnectLagacyFailUrl ;
	boolean isCrawlingOtherWebsite ;	
	
	int numDeepLevel ;
	int watingTime ;
	
	String startUrl ;
	String logFileDirPath ;
	String logFileName ;
	
	enum FileOpenMode 
	{
		UseLagacyLogFile,
		UseCurrentTimeFile,
		UseUserInputNameFile
	}
	
	FileOpenMode fileOpenMode = FileOpenMode.UseCurrentTimeFile ;

	public FileOpenMode getFileOpenMode() {
		return fileOpenMode;
	}

	public void setFileOpenMode(FileOpenMode fileOpenMode) {
		this.fileOpenMode = fileOpenMode;
	}

	public boolean isTryReconnectLagacyFailUrl() {
		return isTryReconnectLagacyFailUrl;
	}

	public void setTryReconnectLagacyFailUrl(boolean isTryReconnectLagacyFailUrl) {
		this.isTryReconnectLagacyFailUrl = isTryReconnectLagacyFailUrl;
	}

	public boolean isCrawlingOtherWebsite() {
		return isCrawlingOtherWebsite;
	}

	public void setCrawlingOtherWebsite(boolean isCrawlingOtherWebsite) {
		this.isCrawlingOtherWebsite = isCrawlingOtherWebsite;
	}

	public int getNumDeepLevel() {
		return numDeepLevel;
	}

	public void setNumDeepLevel(int numDeepLevel) {
		this.numDeepLevel = numDeepLevel;
	}

	public int getWatingTime() {
		return watingTime;
	}

	public void setWatingTime(int watingTime) {
		this.watingTime = watingTime;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getLogFileDirPath() {
		return logFileDirPath;
	}

	public void setLogFileDirPath(String logFileDirPath) {
		this.logFileDirPath = logFileDirPath;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}
	
	

}
