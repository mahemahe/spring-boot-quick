package quick.others.cars;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.util.CollectionUtils;

/**
 * @Author andyXu(xiaohei) xiaohei@maihaoche.com
 * @Date 2017/1/4
 */
public class OkHttpUtils {

    private final static OkHttpClient CLIENT = getUnsafeOkHttpClient();


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            });
            builder.hostnameVerifier((hostname, session) -> true);
            builder.connectTimeout(5, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static OkHttpClient getProxyOkHttpClient(String proxy) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.connectTimeout(5, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String url, Map<String, String> header) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }
        Request request = requestBuilder.build();

        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }


        String reponseStr = response.body().string();
        //System.out.println(reponseStr);

        return reponseStr;

    }

    public static byte[] getByByte(String url, Map<String, String> header) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }
        Request request = requestBuilder.build();

        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }


        return response.body().bytes();

    }

    public static String getThroughProxy(String url, Map<String, String> header, String proxy) throws IOException{
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }
        Request request = requestBuilder.build();

        Response response = getProxyOkHttpClient(proxy).newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }


        String reponseStr = response.body().string();
        //System.out.println(reponseStr);

        return reponseStr;
    }

    public static String getThroughProxy(String url, Map<String, String> header, Map<String, String> param, String proxy) throws IOException{
        String queryStr = "";
        if (param!=null&&!param.isEmpty()){
            queryStr += "?";
            for (Map.Entry<String, String> entry : param.entrySet()) {
                queryStr += queryStr+entry.getKey()+"="+entry.getValue()+"&";
            }
        }
        Request.Builder requestBuilder = new Request.Builder().get().url(url + queryStr);
        if (!CollectionUtils.isEmpty(header)) {
            for (Map.Entry entry : header.entrySet()) {
                requestBuilder.addHeader((String)entry.getKey(), (String)entry.getValue());
            }
        }
        Request request = requestBuilder.build();

        Response response = getProxyOkHttpClient(proxy).newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }


        String reponseStr = response.body().string();
        //System.out.println(reponseStr);

        return reponseStr;
    }


    public static Response getResponse(String url, Map<String, String> header) throws IOException {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }
        Request request = requestBuilder.build();

        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }

        return response;
    }


    public static String post(String url, Map<String, String> header, Map<String, String> param) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(param)) {
            for (String key : param.keySet()) {
                builder.add(key, param.get(key));
            }
        }

        Request.Builder requestBuilder = new Request.Builder().post(builder.build()).url(url);

        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }

        Request request = requestBuilder.build();


        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }


        String reponseStr = response.body().string();
        //System.out.println(reponseStr);

        return reponseStr;
    }

    public static Response postResponse(String url, Map<String, String> header, Map<String, String> param) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(param)) {
            for (String key : param.keySet()) {
                builder.add(key, param.get(key));
            }
        }

        Request.Builder requestBuilder = new Request.Builder().post(builder.build()).url(url);

        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }

        Request request = requestBuilder.build();


        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }

        return response;
    }

    public static String postThroughProxy(String url, Map<String, String> header, Map<String, String> param, String proxy) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(param)) {
            for (String key : param.keySet()) {
                builder.add(key, param.get(key));
            }
        }

        Request.Builder requestBuilder = new Request.Builder().post(builder.build()).url(url);

        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }

        Request request = requestBuilder.build();

        Response response = getProxyOkHttpClient(proxy).newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }

        String reponseStr = response.body().string();

        return reponseStr;
    }

    public static byte[] postByBytes(String url, Map<String, String> header, Map<String, String> param) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(param)) {
            for (String key : param.keySet()) {
                builder.add(key, param.get(key));
            }
        }

        Request.Builder requestBuilder = new Request.Builder().post(builder.build()).url(url);
        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }

        Request request = requestBuilder.build();


        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }


        byte[] reponseStr = response.body().bytes();
        //System.out.println(reponseStr);

        return reponseStr;
    }

    public static String postJson(String url, String json, Map<String, String> header) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"), json);

        FormBody.Builder builder = new FormBody.Builder();


        Request.Builder requestBuilder = new Request.Builder().post(body).url(url);
        if (!CollectionUtils.isEmpty(header)) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }

        Request request = requestBuilder.build();


        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }

        return response.body().string();
    }

}
