package com.tianji.chain.utils;

import com.tianji.chain.config.HttpConfig;
import com.tianji.chain.exception.BussinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketException;


/**
 * @author tanwei
 * @desc
 * @time 1/28/21 10:23 AM
 * @since 1.0.0
 */
@Slf4j
@Component
public class HttpClientUtil {


    final static int threads = 5;
    final static HttpConnectionPool hcm = new HttpConnectionPool(threads);

   /* public static <T> T postForObject(String postUrl, String params, Class<T> clazz, Header... headers) {
        CloseableHttpClient httpClient = hcm.getClient(HttpConfig.getEnableProxy());
        try {
            HttpPost post = new HttpPost(postUrl);
            if (params != null) {
                StringEntity myEntity = new StringEntity(params, ContentType.APPLICATION_JSON);
                post.setEntity(myEntity);
            }
            post.setHeaders(headers);
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("http请求异常: " + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String responseStr = EntityUtils.toString(entity, "UTF-8");
            return JSONObject.parseObject(responseStr, clazz);
        } catch (SocketException e) {
            log.error("error: "+e.getMessage());
            throw new BussinessException("连接接口超时");
        } catch (ClientProtocolException e) {
            log.error("error: "+e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (IOException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException(e.getMessage());
        }finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }
*/

    public static <T> Result<T> postForObject(String postUrl, String params, Class<T> clazz, Header... headers) {
        CloseableHttpClient httpClient = hcm.getClient(HttpConfig.getEnableProxy());
        try {
            HttpPost post = new HttpPost(postUrl);
            if (params != null) {
                StringEntity myEntity = new StringEntity(params, ContentType.APPLICATION_JSON);
                post.setEntity(myEntity);
            }
            post.setHeaders(headers);
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("http请求异常: " + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String responseStr = EntityUtils.toString(entity, "UTF-8");
            return Result.parse(responseStr, clazz);
        } catch (SocketException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException("连接接口超时");
        } catch (ClientProtocolException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (IOException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException(e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    public static <T> Result<T> getForObject(String getUrl, Class<T> clazz, Header... headers) {
        CloseableHttpClient httpClient = hcm.getClient(HttpConfig.getEnableProxy());
        try {
            HttpGet get = new HttpGet(getUrl);
            get.setHeaders(headers);
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("http请求异常: " + statusCode);
            }
            String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            return Result.parse(responseStr, clazz);
        } catch (SocketException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException("连接接口超时");
        } catch (ClientProtocolException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (IOException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException(e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    public static String getForObject(String getUrl, Header... headers) {
        CloseableHttpClient httpClient = hcm.getClient(HttpConfig.getEnableProxy());
        try {
            HttpGet get = new HttpGet(getUrl);
            get.setHeaders(headers);
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("http请求异常: " + statusCode);
            }
            String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            return responseStr;
        } catch (SocketException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException("连接接口超时");
        } catch (ClientProtocolException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (IOException e) {
            log.error("error: " + e.getMessage());
            throw new BussinessException(e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }


    static class HttpConnectionPool {

        private PoolingHttpClientConnectionManager cm;
        private int max;

        public HttpConnectionPool(int maxTotal) {
            this.max = maxTotal;
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(maxTotal);
            cm.setDefaultMaxPerRoute(maxTotal);

        }

        public CloseableHttpClient getClient(boolean enableProxy) {

            RequestConfig defaultRequestConfig = RequestConfig.custom().build();
            if (enableProxy) {
                HttpHost proxy = new HttpHost(HttpConfig.getProxyIp(), HttpConfig.getProxyPort(), "http");
                defaultRequestConfig = RequestConfig.custom().setSocketTimeout(10 * 1000).
                        setConnectionRequestTimeout(10 * 1000)
                        .setConnectTimeout(10 * 1000)
                        .setProxy(proxy)
                        .build();
            }

            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
                    .setConnectionManagerShared(true)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();
            return httpClient;
        }

        public int getActiveNum() {
            return this.max - cm.getTotalStats().getAvailable();
        }

    }

}
