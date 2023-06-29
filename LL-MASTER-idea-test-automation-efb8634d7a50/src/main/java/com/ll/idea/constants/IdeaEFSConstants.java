package com.ll.idea.constants;

public class IdeaEFSConstants {

	/**
	 * Prevent anyone to create an instance of this class. All constant variables
	 * should have accessed through static way.
	 */
	private IdeaEFSConstants() {
		//Do nothing here
	}
	
	public static final String CMD_TO_RESTART_AUTOIMPORTOR = "cd /opt/autoimport/\npkill java\n./startnohup.sh\nps -ef|grep java";
	public static final String CMD_TO_KILL_JAVA = "cd /opt/autoimport/\npkill java";
}
