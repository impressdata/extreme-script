package org.extreme.commons.util;

import java.util.logging.Logger;


public class LogUtil {
	private static Logger logger = null;
	
	public static Logger getLogger() {
		if (LogUtil.logger == null) {
			LogUtil.logger = Logger.getLogger("org.extreme");
		}

		return LogUtil.logger;
	}

}
