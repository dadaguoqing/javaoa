package com.hj.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

	private String jsessionId = null;

	public String getContentWithSession(String turl,
			Map<String, String> params, String charset, Integer timeout) {

		StringBuilder sb = new StringBuilder("");
		StringBuilder psb = new StringBuilder("");
		try {	
			charset = isEmpty(charset) ? "utf-8" : charset;

			if (null != params) {
				Set<String> it = params.keySet();
				boolean first = true;

				for (String key : it) {
					if (first)
						first = false;
					else {
						psb.append("&");
					}
					psb.append(key);
					psb.append("=");
					String value = params.get(key);
					if (isNotEmpty(value)) {
						psb.append(URLEncoder.encode(value, charset));
					}
				}
			}

			URL url = new URL(turl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (HttpUtil.isNotEmpty(jsessionId)) {
				System.out.println("request id is " + this.jsessionId);
				conn.setRequestProperty("Cookie", jsessionId);
			}

			// Ĭ��ʮ�볬ʱ
			conn.setConnectTimeout((timeout == null) ? 10 * 1000 : timeout);

			String ops = psb.toString();

			if (!isEmpty(ops)) {
				conn.setDoOutput(true);

				OutputStreamWriter osw = new OutputStreamWriter(conn
						.getOutputStream(), charset);
				PrintWriter out = new PrintWriter(osw);

				out.write(ops);
				out.flush();
			}

			InputStreamReader insr = new InputStreamReader(conn
					.getInputStream(), charset);
			BufferedReader reader = new BufferedReader(insr);

			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\r\n");
				line = reader.readLine();
			}

			// ��ȡsession��Ϣ
			String session_value = conn.getHeaderField("Set-Cookie");
			if (HttpUtil.isNotEmpty(session_value)) {
				String[] ss = session_value.split(";");
				this.jsessionId = ss[0];
				System.out.println("sessionId is " + this.jsessionId);
			}

			conn.disconnect();
		} catch (Exception e) {
			// e.printStackTrace();
//			throw new RuntimeException(e);
			return null;
		}

		return sb.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null ? true : (str.length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static void main(String[] args) {
		HttpUtil h = new HttpUtil();
		String url = "http://192.168.88.31:8080/ydzd/getVisitorTaskList?vsLine=bslyzhaolh&userId=8a0d160c4448d4d401444ed3f9a70009";
		
		long l1 = System.currentTimeMillis();
		String str = h.getContentWithSession(url, null, null, null);
		long l2 = System.currentTimeMillis();
		System.out.println(str);
		
		System.out.println((l2-l1)+"nm");
	}

	/**
	 * ��ȡurl�����ݣ�Ĭ�ϳ�ʱ10��
	 * 
	 * @param turl
	 *            url��ַ���������http://
	 * @param params
	 *            ����
	 * @param charset
	 *            �����ַ�
	 * @param timeout
	 *            ��ʱʱ�䣬�������Ϊnull���ʾ10��
	 * @return
	 */
	public static String getContent(String turl, Map<String, String> params,
			String charset, Integer timeout) {

		StringBuilder sb = new StringBuilder("");
		StringBuilder psb = new StringBuilder("");
		try {
			charset = isEmpty(charset) ? "utf-8" : charset;

			if (null != params) {
				Set<String> it = params.keySet();
				boolean first = true;

				for (String key : it) {
					if (first)
						first = false;
					else {
						psb.append("&");
					}
					psb.append(key);
					psb.append("=");
					String value = params.get(key);
					if (isNotEmpty(value)) {
						psb.append(URLEncoder.encode(value, charset));
					}
				}
			}

			URL url = new URL(turl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Ĭ��ʮ�볬ʱ
			conn.setConnectTimeout((timeout == null) ? 10 * 1000 : timeout);
			// conn.connect();

			String ops = psb.toString();

			if (!isEmpty(ops)) {
				conn.setDoOutput(true);

				OutputStreamWriter osw = new OutputStreamWriter(conn
						.getOutputStream(), charset);
				PrintWriter out = new PrintWriter(osw);

				out.write(ops);
				out.flush();
			}

			InputStreamReader insr = new InputStreamReader(conn
					.getInputStream(), charset);
			BufferedReader reader = new BufferedReader(insr);

			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\r\n");
				line = reader.readLine();
			}

			conn.disconnect();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}

		return sb.toString();
	}
	
	public static byte[] getContent2(String turl, Map<String, String> params,
			String charset, Integer timeout) {

		StringBuilder psb = new StringBuilder("");
		try {
			charset = isEmpty(charset) ? "utf-8" : charset;

			if (null != params) {
				Set<String> it = params.keySet();
				boolean first = true;

				for (String key : it) {
					if (first)
						first = false;
					else {
						psb.append("&");
					}
					psb.append(key);
					psb.append("=");
					String value = params.get(key);
					if (isNotEmpty(value)) {
						psb.append(URLEncoder.encode(value, charset));
					}
				}
			}

			URL url = new URL(turl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Ĭ��ʮ�볬ʱ
			conn.setConnectTimeout((timeout == null) ? 10 * 1000 : timeout);
			// conn.connect();

			String ops = psb.toString();

			if (!isEmpty(ops)) {
				conn.setDoOutput(true);

				OutputStreamWriter osw = new OutputStreamWriter(conn
						.getOutputStream(), charset);
				PrintWriter out = new PrintWriter(osw);

				out.write(ops);
				out.flush();
			}
			
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			
			InputStream in = conn.getInputStream();
			byte[] buf = new byte[512];
			int len = 0;
			
			while( (len=in.read(buf)) != -1){
				bout.write(buf,0,len);
			}

			bout.flush();
			bout.close();

			conn.disconnect();
			
			return bout.toByteArray();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
