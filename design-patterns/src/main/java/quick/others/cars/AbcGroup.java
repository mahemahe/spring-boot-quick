package quick.others.cars;

import java.util.List;
import lombok.Data;

/**
 * <p> TODO description </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/14 周一 8:43 下午
 */
@Data
public class AbcGroup {

    /**
     * 首字母
     */
    private String Key;
    /**
     * 省份list
     */
    private List<Province> Values;
}
