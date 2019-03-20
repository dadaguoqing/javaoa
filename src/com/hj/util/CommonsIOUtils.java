package com.hj.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonsIOUtils {
	public static void closeStream(InputStream is, OutputStream os) {

		if (null != is) {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				System.out.println("close InputStream fail!");
			}
		}

		if (null != os) {
			try {
				os.close();
				os = null;
			} catch (IOException e) {
				System.out.println("close OutputStream fail!");
			}
		}
	}
}

