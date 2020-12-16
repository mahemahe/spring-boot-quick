package quick.others.cars;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.UrlUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import quick.others.ExecutorConstant;
import quick.others.FutureUtils;
import quick.others.StreamUtils;

/**
 * <p> TODO description </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/14 周一 7:59 下午
 */
@Slf4j
public class HtmlParseTest {
    // https://dealer.autohome.com.cn/wuhu/0/0/0/0/5/1/0/0.html
    // 第一个字符是城市拼音，第二个是页数
    private static final String URL = "https://dealer.autohome.com.cn/%s/0/0/0/0/%s/1/0/0.html";
    private static final String LICENCE_URL = "https://dealer.autohome.com.cn/Ajax/GetLicensePic?dealerId=%s";
    private static final String TAG_A = "a";
    private static final String TAG_SPAN = "span";
    private static final String TAG_EM = "em";
    private static final String ATTR_HREF = "href";
    private static final String ATTR_ID = "id";
    private static final String CLASS_ID = "list-item";
    private static final String CLASS_DEALER = "info-wrap";
    private static final String CLASS_LOCATION = "info-addr";
    private static final String CLASS_COMPANY = "company";
    private static final String HTTPS = "https:";
    private static final Element EMPTY_ELEMENT = new Element("span");
    private static final String TAB = "\t";
    private static final String PREFIX_GPS = "^[\\s\\S]*(dealerlist=\\[)";
    private static final String POST_GPS = "\\];[\\s\\S]*$";
    private static final String LONGITUDE = "MapLonBaidu";
    private static final String LATITUDE = "MapLatBaidu";
    /**
     *
     */
    public static void main(String[] args) {
//        testGZ();
        testAll();
    }

    public static void testGZ() {
        HtmlParseTest test = new HtmlParseTest();
        test.outputDealers(test.outOnePage("广东", "广州", "guangzhou", 2));
    }

    public static void testAll() {
        HtmlParseTest test = new HtmlParseTest();

        for (AbcGroup group : AreasUtil.ABC_GROUPS) {
            for (Province province : group.getValues()) {
                String provinceName = province.getName();
                // 具体城市
                province.getCities().forEach(city -> {
                    String cityName = city.getName();
                    Integer totalPage = test.getPages(city.getCount());
                    if (0 == totalPage) {
                        return;
                    }
                    List<Integer> pages = new ArrayList<>();
                    for (int i = 1; i < totalPage + 1; i++) {
                        pages.add(i);
                    }

                    List<CompletableFuture<List<Dealer>>> resultFutures = StreamUtils.convert(pages,
                            pageNo -> CompletableFuture.supplyAsync(
                                    () -> test.outOnePage(provinceName, cityName, city.getPinyin(), pageNo),
                                    ExecutorConstant.OUTER_NET_IO_EXECUTOR
                            )
                    );
                    CompletableFuture<List<List<Dealer>>> resultsFuture = FutureUtils.sequence(resultFutures);
                    List<List<Dealer>> results = FutureUtils.getResultWithDrownException(
                            resultsFuture,
                            e -> System.out.println(e.getLocalizedMessage())
                    );
                    if (CollectionUtils.isNotEmpty(results)) {
                        // 这里就不输出了   放到组装dealer里输出
//                        results.forEach(test::outputDealers);
                    }
                });

            }
        }
    }

    public List<Dealer> outOnePage(String province, String city, String pinyin, Integer pageNo) {
        String url = String.format(URL, pinyin, pageNo);

        Document doc = getDomByUrl(url);
        Elements elements = doc.getElementsByClass(CLASS_DEALER);
        List<CompletableFuture<Dealer>> resultFutures = StreamUtils.convert(elements,
                element -> CompletableFuture.supplyAsync(
                        () -> this.assemblyDealer(element, province, city),
                        ExecutorConstant.INNER_NET_IO_EXECUTOR
                )
        );
        CompletableFuture<List<Dealer>> resultsFuture = FutureUtils.sequence(resultFutures);

        return FutureUtils.getResultWithDrownException(
                resultsFuture,
                e -> System.out.println(e.getLocalizedMessage())
        );
    }

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

        System.out.println(
                dealer.getId() + TAB + dealer.getName() + TAB + dealer.getSpec() + TAB + dealer.getProvince() + TAB
                        + dealer.getCity() + TAB + TAB + dealer.getLocation() + TAB + dealer.getDetailInfo()
                        .getLongitude() + TAB + dealer.getDetailInfo().getLatitude() + TAB + dealer.getDetailInfo()
                        .getFinalUrl());
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

        Document doc = getDomByUrl(url);
        if (null == doc) {
            return info;
        }
        Elements gpsEle = doc.getElementsByClass(CLASS_COMPANY);
        if (CollectionUtils.isNotEmpty(gpsEle)) {
            String gpsScript = gpsEle.get(1).data();
            String gpsJson = gpsScript.replaceFirst(PREFIX_GPS, StrUtil.EMPTY).replaceFirst(POST_GPS, StrUtil.EMPTY);
            JSONObject gpsJSON = JSONUtil.parseObj(gpsJson);
            info.setLongitude(gpsJSON.get(LONGITUDE).toString());
            info.setLatitude(gpsJSON.get(LATITUDE).toString());
        }

        // 通过get请求直接获取企业的营业执照
        try {
            String licenceUrl = OkHttpUtils.get(String.format(LICENCE_URL, dealerId), MapUtils.EMPTY_MAP);
            if (StrUtil.isNotEmpty(licenceUrl)) {
                info.setFinalUrl(HTTPS + licenceUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    public Document getDomByUrl(String url) {
        // HtmlUnit 模拟浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
        final WebRequest request;
        try {
            request = new WebRequest(UrlUtils.toUrlUnsafe(url),
                    webClient.getBrowserVersion().getHtmlAcceptHeader(),
                    webClient.getBrowserVersion().getAcceptEncodingHeader());
            request.setCharset(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        HtmlPage page;
        try {
            page = webClient.getPage(webClient.getCurrentWindow().getTopWindow(), request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 等待js后台执行30秒
        webClient.waitForBackgroundJavaScript(3 * 1000);
        page.getWebResponse().defaultCharsetUtf8();
        String pageAsXml = page.asXml();

        return Jsoup.parse(pageAsXml);
    }

    public void outputDealers(List<Dealer> dealers) {
        if (CollectionUtils.isEmpty(dealers)) {
            return;
        }
        dealers.forEach(dealer -> {
            System.out.println(dealer.getName() + TAB + dealer.getSpec() + TAB + dealer.getProvince() + TAB + dealer.getCity() + TAB + TAB + dealer.getLocation() + TAB + dealer.getDetailInfo().getLongitude() + TAB + dealer.getDetailInfo().getLatitude());
        });
    }

    /**
     * 获取总页数
     * @param input 总数
     * @return 总页数
     */
    public int getPages(BigDecimal input) {
        if (null == input) {
            return 0;
        }
        if (input.equals(new BigDecimal(0))) {
            return 0;
        }
        return input.divide(new BigDecimal(15), 0, BigDecimal.ROUND_CEILING).intValue();
    }

}
