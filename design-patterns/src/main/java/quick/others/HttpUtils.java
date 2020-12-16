package quick.others;

import cn.hutool.core.util.StrUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections.MapUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author oudesine
 * @date 2020年12月03日
 */
@UtilityClass
public class HttpUtils {

    public String get(String url, Map<String, String> headers) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
        httpGet.setHeader("Content-Type", "application/json");
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> temp : headers.entrySet()) {
                httpGet.setHeader(temp.getKey(), temp.getValue());
            }
        }
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpGet, context);
        } catch (IOException e) {
            e.printStackTrace();
            return StrUtil.EMPTY;
        }
        String content;
        try {
            content = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return StrUtil.EMPTY;
        }
        return content;
    }

    public String post(String url, Map<String, String> inputs) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        // 入参
        List<NameValuePair> params = new ArrayList<>();
        if (MapUtils.isNotEmpty(inputs)) {
            for (Map.Entry<String, String> temp : inputs.entrySet()) {
                params.add(new BasicNameValuePair(temp.getKey(), temp.getValue()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpPost, context);
        } catch (IOException e) {
            e.printStackTrace();
            return StrUtil.EMPTY;
        }
        String content;
        try {
            content = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return StrUtil.EMPTY;
        }
        return content;
    }

}
