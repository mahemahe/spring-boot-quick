package quick.others.cars;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> TODO description </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/15 周二 1:36 上午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailInfo {

    /**
     * 经度
     */
    private String longitude = StrUtil.EMPTY;
    /**
     * 维度
     */
    private String latitude = StrUtil.EMPTY;

    /**
     * 营业执照的url
     */
    private String finalUrl = StrUtil.EMPTY;
}
