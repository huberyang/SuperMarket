package com.ncs.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	public static String getStatckTrace(Throwable t) {
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		try {
			t.printStackTrace(printWriter);
			return writer.toString();
		} finally {
			printWriter.close();
		}

	}

}
