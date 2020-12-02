package quick.others;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.mhc.framework.common.jackson.JsonUtil;
import com.mhc.framework.common.util.BeanCopierUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralHandwritingOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralHandwritingOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.VinOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.VinOCRResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 9:02 上午
 */
@Slf4j
public class NoParam {

    private static final String SECRET_ID = "AKIDhGi09Aa8SZUEpEvRGuLe3jXbgdPy4YYM";
    private static final String SECRET_KEY = "lLjw8DAxYxBG9qLfnnbDetyrcDE7nHdo";
    private static final String HOST = "ocr.tencentcloudapi.com";
    private static final String REGION = "ap-shanghai";
    private static final String ERROR_MESSAGE_REGEX = "^[A-Za-z0-9_\\.]{0,}-";

    /**
     *
     */
    public static void main(String[] args) {
        testVinOcr();
    }

    public static void testVinOcr() {
        VinOCRRequest req = new VinOCRRequest();
        req.setImageUrl("https://img.maihaoche.com/0EDC248A-0DD8-4A30-9C90-1777DED2AC61.jpg");

        OcrClient client = getClient();
        VinOCRResponse resp = null;
        try {
            resp = client.VinOCR(req);
        } catch (TencentCloudSDKException e) {
            log.error("OcrApi.generalBasicOCR occurred error, input:[{}]", JSONUtil.toJsonStr(resp), e);
            String error = e.getLocalizedMessage().replaceFirst(ERROR_MESSAGE_REGEX, StrUtil.EMPTY);
            System.out.println(error);
        }
        System.out.println(JSONUtil.toJsonStr(resp));
    }

    public static void testOcr() {
        GeneralHandwritingOCRRequest req = new GeneralHandwritingOCRRequest();
        req.setImageUrl("https://img.maihaoche.com/0EDC248A-0DD8-4A30-9C90-1777DED2AC61.jpg");

        OcrClient client = getClient();
        GeneralHandwritingOCRResponse resp = null;
        try {
            resp = client.GeneralHandwritingOCR(req);
        } catch (TencentCloudSDKException e) {
            log.error("OcrApi.generalBasicOCR occurred error, input:[{}]", JSONUtil.toJsonStr(resp), e);
            String error = e.getLocalizedMessage().replaceFirst(ERROR_MESSAGE_REGEX, StrUtil.EMPTY);
            System.out.println(error);
        }
        JsonNode jsonNode = JsonUtil.readTree("{\"Parag\":{\"ParagNo\":1}}");
        AA aa = BeanCopierUtil.convert(resp.getTextDetections()[0], AA.class);
        System.out.println(JSONUtil.toJsonStr(resp));
    }

    public void testBeanCopier() {
        AA aa = new AA();
        aa.setImage("imageUrl");
        aa.setTest("测试");

        BB bb = BeanCopierUtil.convert(aa, BB.class);
        BB b2 = JSONUtil.toBean(JSONUtil.toJsonStr(aa), BB.class);
        System.out.println(JSONUtil.toJsonStr(bb));
        System.out.println(JSONUtil.toJsonStr(b2));
    }

    public static OcrClient getClient() {
        Credential cred = new Credential(SECRET_ID, SECRET_KEY);

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(HOST);

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        return new OcrClient(cred, REGION, clientProfile);
    }

    public static void encodeUrlTest() throws UnsupportedEncodingException {
        String encodeUrl1 = URLEncoder.createDefault()
                .encode("https://img.maihaoche.com/80972E3E-211D-485E-A947-DCD771D813CF/铭牌.jpg",
                        StandardCharsets.UTF_8);
        System.out.println(encodeUrl1);
        String encodeUrl2 = java.net.URLEncoder
                .encode("https://img.maihaoche.com/80972E3E-211D-485E-A947-DCD771D813CF/铭牌.jpg", "utf-8");
        System.out.println(encodeUrl2);
    }

    public static void regex2() {
        String pattern = ".*邀请你加入了群聊.*";
        String permition = "\"韩永峰\"邀请你加入了群，群聊参与人还有：车马邦汽车 销售 托运18919212173、卖好车-物流-腿腿、A华月运车--梁一17760348707、卖好车科科、卖好车-交付客服-剑痕、卖好车-交付中心-小梦";
        System.out.println(permition.matches(pattern));
//        String pattern = "修改群名为“";
//        String permition = "\"贵达物流垫款专员吴娅15180805715\"修改群名为“信泰&贵达&凯里-六盘水&朗逸”";
//        System.out.println(permition.indexOf(pattern));
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(content);

//        String empty = "  　";
//        for (int i = 0; i < empty.length(); i++) {
//            char c = empty.charAt(i);
//            System.out.println("\\u" + Integer.toHexString(c));
//        }
//        char c = (short)'\u3000';
//        System.out.println(c);
//        String emoji = "  \u2600\uFE0F \u2600  @Inart Damon \uD83D\uDC2C 发送测试\uD83D\uDC7F \uD83D\uDE04\uD83D\uDE37   \uE40C  \uD83D\uDE37 \uE412 \uE412   \uD83D\uDE02 \uD83D\uDE00";
//        System.out.println(emoji);
//        System.out.println(EmojiUtil.toUnicode(emoji));

//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        String abc = list.stream().map(Objects::toString).collect(Collectors.joining(","));
//        System.out.println(abc);
//        A a = new A();
//        a.setList(list);
//
//        B b = new B();
//        BeanUtils.copyProperties(a, b);
//
//        List<Integer> list2 = new ArrayList<>();
//        System.out.println(JSONUtil.toJsonStr(a));
//        System.out.println(JSONUtil.toJsonStr(b));
//
//        BeanUtils.copyProperties(list, list2);
//        System.out.println(JSONUtil.toJsonStr(list2));

//        Map<Integer, Integer> map = list.stream().collect(Collectors.toMap(Integer::intValue, Integer::intValue));
//        System.out.println(JSONUtil.toJsonPrettyStr(map));
//        String abc = "2020-06-24 16:37:25";
//        LocalDateTime aaa = LocalDateTime.parse(abc, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        LocalDateTime bbb = null;
//        if (bbb instanceof LocalDateTime) {
//            System.out.println(bbb);
//        }
//        Long abcL = (Long)ConvertUtils.convert(abc, Long.class);
//        Long abcC = Long.valueOf(abc);
//        System.out.println(abc);
//        System.out.println(abcL);
//        System.out.println(abcC);
    }

    public static void regex() {
        String pattern = ".*(@chatroom|@im.chatroom)$";
        System.out.println("25984982100253528@openim : " + "25984982100253528@openim".matches(pattern));
        System.out.println("9223372041406172873@im.chatroom : " + "9223372041406172873@im.chatroom".matches(pattern));
        System.out.println("wxid_4vl3ggsuvloa22 : " + "wxid_4vl3ggsuvloa22".matches(pattern));
        System.out.println("21537379559@chatroom : " + "21537379559@chatroom".matches(pattern));
    }
}
