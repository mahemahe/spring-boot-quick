package quick.emoji;
import cn.hutool.core.lang.TypeReference;
import	java.nio.charset.StandardCharsets;

import cn.hutool.json.JSONUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/7/30 周四 1:21 下午
 */
public class EmojiTest {
    private static final String SOFT_BANK_TO_UNICODE_PATH = "/softbank_unicode.json";
    private static final String UNICODE_TO_SOFT_BANK_PATH = "/unicode_softbank.json";
    public static final Map<String, String> SOFT_BANK_TO_UNICODE_MAP;
    public static final Map<String, String> UNICODE_TO_SOFT_BANK_MAP;

    static {
        SOFT_BANK_TO_UNICODE_MAP = loadJsonMap(SOFT_BANK_TO_UNICODE_PATH);
        UNICODE_TO_SOFT_BANK_MAP = loadJsonMap(UNICODE_TO_SOFT_BANK_PATH);
    }

    public static Map<String, String> loadJsonMap(String jsonPath) {
        try {
            InputStream is = EmojiTest.class.getResourceAsStream(jsonPath);
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int length;
            while ((length = is.read(buffer)) != -1) {
                byteStream.write(buffer, 0, length);
            }
            String json = byteStream.toString(StandardCharsets.UTF_8.name());
            return JSONUtil.toBean(json, new TypeReference<Map<String, String>>() {}, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
