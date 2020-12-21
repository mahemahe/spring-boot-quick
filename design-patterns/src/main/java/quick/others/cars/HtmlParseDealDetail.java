package quick.others.cars;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p> TODO description </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/14 周一 7:59 下午
 */
@Slf4j
public class HtmlParseDealDetail {
    // https://dealer.autohome.com.cn/wuhu/0/0/0/0/5/1/0/0.html
    // 第一个字符是城市拼音，第二个是页数
    private static final String LICENCE_URL = "https://dealer.autohome.com.cn/Ajax/GetLicensePic?dealerId=%s";
    private static final String TAG_A = "a";
    private static final String TAG_SPAN = "span";
    private static final String TAG_EM = "em";
    private static final String ATTR_HREF = "href";
    private static final String ATTR_ID = "id";
    private static final String CLASS_ID = "list-item";
    private static final String CLASS_LOCATION = "info-addr";
    private static final String CLASS_COMPANY = "company";
    private static final String HTTPS = "https:";
    private static final String PREFIX_HTTPS = "^(https:).*$";
    private static final Element EMPTY_ELEMENT = new Element("span");
    private static final String PREFIX_GPS = "^[\\s\\S]*(dealerlist=\\[)";
    private static final String POST_GPS = "\\];[\\s\\S]*$";
    private static final String LONGITUDE = "MapLonBaidu";
    private static final String LATITUDE = "MapLatBaidu";


    /**
     * 组装经销商数据
     */
    public Dealer assemblyDealer(Element element, String province, String city) {
        Elements liEles = element.getElementsByTag("li");

        // 获取公司name
        Elements tagAs = liEles.get(0).getElementsByTag(TAG_A);
        if (CollectionUtils.isEmpty(tagAs)) {
            return null;
        }
        Elements dealerNameEle = tagAs.get(0).getElementsByTag(TAG_SPAN);
        if (CollectionUtils.isEmpty(dealerNameEle)) {
            return null;
        }

        Dealer dealer = new Dealer();
        try {
            dealer.setId(element.parent().getElementsByClass(CLASS_ID).get(0).attr(ATTR_ID));
        } catch (Exception e) {
        }
        // 经销商name
        dealer.setName(Optional.ofNullable(dealerNameEle.get(0)).orElse(EMPTY_ELEMENT).ownText());
        // 获取经销商地址
        Elements locationEle = liEles.get(3).getElementsByClass(CLASS_LOCATION);
        if (CollectionUtils.isNotEmpty(locationEle)) {
            dealer.setLocation(Optional.ofNullable(locationEle.get(0)).orElse(EMPTY_ELEMENT).ownText());
        }
        // 经纬度和url
        try {
            Elements gpsPageEle = liEles.get(3).getElementsByTag(TAG_A);
            if (CollectionUtils.isNotEmpty(gpsPageEle)) {
                String gpsUrl = gpsPageEle.get(0).attr(ATTR_HREF);
                dealer.setDetailInfo(getInfo(gpsUrl, dealer.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 经销商主营品牌
        try {
            Elements specEle = liEles.get(1).getElementsByTag(TAG_EM);
            if (CollectionUtils.isNotEmpty(specEle)) {
                dealer.setSpec(Optional.ofNullable(specEle.get(0)).orElse(EMPTY_ELEMENT).ownText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dealer.setProvince(province);
        dealer.setCity(city);


        return dealer;
    }

    /**
     * 通过详情页的url获取gps和公司的营业执照url
     * @param uri 公司详情页地址
     * @return gps和公司营业执照url
     */
    public DetailInfo getInfo(String uri, String dealerId) {
        if (StrUtil.isEmpty(uri)) {
            return new DetailInfo();
        }
        String url = HTTPS + uri;
        DetailInfo info = new DetailInfo();

        Document doc = OkHttpUtils.getDomByUrl(url);
        if (null == doc) {
            return info;
        }
        Elements gpsEle = doc.getElementsByClass(CLASS_COMPANY);
        if (CollectionUtils.isNotEmpty(gpsEle)) {
            String gpsScript = gpsEle.get(1).data();
            String gpsJson = gpsScript.replaceFirst(PREFIX_GPS, StrUtil.EMPTY).replaceFirst(POST_GPS, StrUtil.EMPTY);
            try {
                JSONObject gpsJSON = JSONUtil.parseObj(gpsJson);
                info.setLongitude(gpsJSON.get(LONGITUDE).toString());
                info.setLatitude(gpsJSON.get(LATITUDE).toString());
            } catch (Exception e) {
                info.setLongitude(StrUtil.EMPTY);
                info.setLatitude(StrUtil.EMPTY);
            }
        }

        // 通过get请求直接获取企业的营业执照
        try {
            String licenceUrl = OkHttpUtils.get(String.format(LICENCE_URL, dealerId), MapUtils.EMPTY_MAP);
            if (StrUtil.isNotEmpty(licenceUrl)) {
                licenceUrl = licenceUrl.replaceAll("\"", StrUtil.EMPTY);
                licenceUrl = licenceUrl.matches(PREFIX_HTTPS) ? licenceUrl : HTTPS + licenceUrl;
                info.setFinalUrl(licenceUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

}
