package quick.others.cars;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p> TODO description </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/14 周一 7:59 下午
 */
public class HtmlParseTest {
    private static final List<String> EXCEPT_CITYS = new ArrayList<>();
    private static final List<String> EXCEPT_PROVINCES = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        testAll();
    }

    static {
        EXCEPT_PROVINCES.add("安徽");
        EXCEPT_PROVINCES.add("北京");
        EXCEPT_PROVINCES.add("天津");
        EXCEPT_PROVINCES.add("上海");
        EXCEPT_PROVINCES.add("重庆");
        EXCEPT_PROVINCES.add("福建");
        EXCEPT_PROVINCES.add("广东");
        EXCEPT_PROVINCES.add("广西");
        EXCEPT_PROVINCES.add("贵州");
        EXCEPT_PROVINCES.add("甘肃");
        EXCEPT_PROVINCES.add("河北");
        EXCEPT_PROVINCES.add("黑龙江");
        EXCEPT_PROVINCES.add("河南");
        EXCEPT_PROVINCES.add("湖北");
        EXCEPT_PROVINCES.add("湖南");
        EXCEPT_PROVINCES.add("海南");
        EXCEPT_PROVINCES.add("吉林");

        EXCEPT_CITYS.add("南京");
        EXCEPT_CITYS.add("无锡");
        EXCEPT_CITYS.add("徐州");
        EXCEPT_CITYS.add("常州");
        EXCEPT_CITYS.add("苏州");
        EXCEPT_CITYS.add("南通");
        EXCEPT_CITYS.add("连云港");

    }
    private static final String CURRENT_CITY = "淮安";
    private static final Integer CURRENT_PAGE = 3;

    public static void testAll() {
        for (AbcGroup group : AreasUtil.ABC_GROUPS) {
            for (Province province : group.getValues()) {
                String provinceName = province.getName();
                if (EXCEPT_PROVINCES.contains(provinceName.trim())) {
                    continue;
                }
                // 具体城市
                for (City city : province.getCities()) {
                    final String cityName = city.getName();
                    final int totalPage = getPages(city.getCount());
                    if (0 == totalPage) {
                        return;
                    }
                    if (EXCEPT_CITYS.contains(cityName.trim())) {
                        return;
                    }
                    final List<Integer> pages = new ArrayList<>();
                    for (int i = 1; i < totalPage + 1; i++) {
                        pages.add(i);
                    }
                    for (Integer pageNo : pages) {
                        if (CURRENT_CITY.equals(cityName.trim())) {
                            if (pageNo < CURRENT_PAGE) {
                                return;
                            }
                        }
                        new HtmlParseDeal().outOnePage(provinceName, cityName, city.getPinyin(), pageNo);
                    }
                }
            }
        }
    }

    /**
     * 获取总页数
     * @param input 总数
     * @return 总页数
     */
    public static int getPages(BigDecimal input) {
        if (null == input) {
            return 0;
        }
        if (input.equals(new BigDecimal(0))) {
            return 0;
        }
        return input.divide(new BigDecimal(15), 0, BigDecimal.ROUND_CEILING).intValue();
    }

}
