package quick.others.cars;

import java.math.BigDecimal;
import lombok.Data;

/**
 * <p> 城市 </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/14 周一 8:39 下午
 */
@Data
public class City {

    /**
     * 名字
     */
    private String Name;
    /**
     * 拼音
     */
    private String Pinyin;
    /**
     * 总数
     */
    private BigDecimal Count;
}
