package quick.others;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * <p> TODO description </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/11/30 周一 5:11 下午
 */
@Data
public class BB {
    @SerializedName(value = "Test")
    private String test;

    private String image;

    private Integer paragNo;
}
