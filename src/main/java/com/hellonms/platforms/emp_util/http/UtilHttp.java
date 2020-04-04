/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.http;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * Insert description of UtilHttp.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 17.
 * @modified 2015. 7. 17.
 * @author cchyun
 *
 */
public class UtilHttp {

	private static HttpClientBuilder builderNoSequerty;

	public static CloseableHttpClient newHttpClientNoSequerty() throws EmpException {
		try {
			if (builderNoSequerty == null) {
				SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
				sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
				SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(), NoopHostnameVerifier.INSTANCE);

				builderNoSequerty = HttpClientBuilder.create();
				builderNoSequerty.setSSLSocketFactory(sslConnectionSocketFactory).build();
			}

			return builderNoSequerty.build();
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

}
