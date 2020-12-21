package quick.others.cars;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
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
public class HtmlParseDeal {
    // https://dealer.autohome.com.cn/wuhu/0/0/0/0/5/1/0/0.html
    // 第一个字符是城市拼音，第二个是页数
    private static final String URL = "https://dealer.autohome.com.cn/%s/0/0/0/0/%s/1/0/0.html";
    private static final String CLASS_DEALER = "info-wrap";
    private static final String TAB = "\t";


    public void outOnePage(String province, String city, String pinyin, Integer pageNo) {
        long start = System.currentTimeMillis();
        String url = String.format(URL, pinyin, pageNo);

        Document doc = OkHttpUtils.getDomByUrl(url);
        Elements elements = doc.getElementsByClass(CLASS_DEALER);
        List<CompletableFuture<Dealer>> resultFutures = StreamUtils.convert(elements,
                element -> CompletableFuture.supplyAsync(
                        () -> new HtmlParseDealDetail().assemblyDealer(element, province, city),
                        ExecutorConstant.OUTER_NET_IO_EXECUTOR
                )
        );
        CompletableFuture<List<Dealer>> resultsFuture = FutureUtils.sequence(resultFutures);
        List<Dealer> dealers = FutureUtils.getResultWithDrownException(
                resultsFuture,
                e -> System.out.println("error " + e.getLocalizedMessage())
        );
        outputDealers(dealers);
        long end = System.currentTimeMillis();
        System.out.println("test_test: " + (end - start)/1000 + " @@ " + pageNo);
    }
    public void outputDealers(List<Dealer> dealers) {
        if (CollectionUtils.isEmpty(dealers)) {
            return;
        }
        dealers.forEach(dealer -> {
            if (null == dealer.getDetailInfo()) {
                dealer.setDetailInfo(new DetailInfo());
            }
            System.out.println(
                    dealer.getId() + TAB + dealer.getName() + TAB + dealer.getSpec() + TAB + dealer.getProvince() + TAB
                            + dealer.getCity() + TAB + TAB + dealer.getLocation() + TAB + dealer.getDetailInfo()
                            .getLongitude() + TAB + dealer.getDetailInfo().getLatitude() + TAB + dealer.getDetailInfo()
                            .getFinalUrl());
        });
    }
}
