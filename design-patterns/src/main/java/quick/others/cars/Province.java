package quick.others.cars;

import java.util.List;
import lombok.Data;

/**
 * <p> 省份 </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/14 周一 8:20 下午
 */
@Data
public class Province {

    /**
     * 名字
     */
    private String Name;
    /**
     * 拼音
     */
    private String Pinyin;
    /**
     * 城市list
     */
    private List<City> Cities;
}
