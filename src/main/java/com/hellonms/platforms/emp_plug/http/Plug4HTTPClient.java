/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.http.UtilHttp;

/**
 * <p>
 * HTTP Client
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 22.
 * @modified 2015. 5. 22.
 * @author cchyun
 *
 */
public class Plug4HTTPClient {

	private CloseableHttpClient client;

	public Plug4HTTPClient() throws EmpException {
		client = UtilHttp.newHttpClientNoSequerty();
	}

	public byte[] downloadFile(URL url) throws EmpException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		downloadFile(url, os);
		return os.toByteArray();
	}

	public void downloadFile(URL url, File file) throws EmpException {
		try {
			FileOutputStream os = new FileOutputStream(file);
			downloadFile(url, os);
			os.close();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO, file.getAbsolutePath());
		}

	}

	public void downloadFile(URL url, OutputStream os) throws EmpException {
		CloseableHttpResponse response = null;
		try {
			HttpGet method = new HttpGet(url.toString());

			response = client.execute(method);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = response.getEntity();
				byte[] buf = EntityUtils.toByteArray(httpEntity);

				os.write(buf);
				os.flush();
			}
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO, url);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() {
		try {
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String get(URL url) throws EmpException {
		return get(url, null);
	}

	public String get(URL url, Map<String, String> headers) throws EmpException {
		CloseableHttpResponse response = null;
		try {

			HttpGet method = new HttpGet(url.toString());
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					method.addHeader(entry.getKey(), entry.getValue());
				}
			}

			response = client.execute(method);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity);
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO, url);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] getBytes(URL url) throws EmpException {
		return getBytes(url, null);
	}

	public byte[] getBytes(URL url, Map<String, String> headers) throws EmpException {
		CloseableHttpResponse response = null;
		try {

			HttpGet method = new HttpGet(url.toString());
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					method.addHeader(entry.getKey(), entry.getValue());
				}
			}

			response = client.execute(method);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toByteArray(httpEntity);
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO, url);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String put(URL url, String content_type, String request) throws EmpException {
		return put(url, content_type, null, request);
	}

	public String put(URL url, String content_type, Map<String, String> headers, String request) throws EmpException {
		CloseableHttpResponse response = null;
		try {

			HttpPut method = new HttpPut(url.toString());
			StringEntity input = new StringEntity(request);
			if (content_type != null) {
				input.setContentType(content_type);
			}
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					method.addHeader(entry.getKey(), entry.getValue());
				}
			}
			method.setEntity(input);

			response = client.execute(method);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity);
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO, url);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String post(URL url, String content_type, String request) throws EmpException {
		return post(url, content_type, null, request);
	}

	public String post(URL url, String content_type, Map<String, String> headers, String request) throws EmpException {
		CloseableHttpResponse response = null;
		try {

			HttpPost method = new HttpPost(url.toString());
			StringEntity input = new StringEntity(request, "UTF-8");
			if (content_type != null) {
				input.setContentType(content_type);
			}
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					method.addHeader(entry.getKey(), entry.getValue());
				}
			}
			method.setEntity(input);

			response = client.execute(method);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity);
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO, url);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String delete(URL url) throws EmpException {
		return delete(url, null);
	}

	public String delete(URL url, Map<String, String> headers) throws EmpException {
		CloseableHttpResponse response = null;
		try {

			HttpDelete method = new HttpDelete(url.toString());
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					method.addHeader(entry.getKey(), entry.getValue());
				}
			}

			response = client.execute(method);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity);
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO, url);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
