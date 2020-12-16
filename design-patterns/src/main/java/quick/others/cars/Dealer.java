package quick.others.cars;

import lombok.Data;

/**
 * <p> 经销商 </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/14 周一 8:52 下午
 */
@Data
public class Dealer {

    /**
     * 主键
     */
    private String id;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 地址
     */
    private String location;
    /**
     * 名字
     */
    private String name;
    /**
     * 公司名
     */
    private String finalName;
    /**
     * 品牌
     */
    private String spec;
    /**
     * 经纬度
     */
    private DetailInfo detailInfo;
}
