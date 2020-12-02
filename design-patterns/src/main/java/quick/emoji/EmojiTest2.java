package quick.emoji;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.emoji.EmojiUtil;
import cn.hutool.json.JSONUtil;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.EmojiTransformer;
import com.vdurmont.emoji.EmojiParser.UnicodeCandidate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/7/30 周四 1:49 下午
 */
public class EmojiTest2 {
    private static final Integer SOFT_BANK_MIN = Integer.valueOf("E001", 16);
    private static final Integer SOFT_BANK_MAX = Integer.valueOf("E537", 16);
    /**
     *
     */
    public static void main(String[] args) {
        String emoji = "@Inart Damon \uE520 发送测试\uE11A \uE415\uE40C";
        for (char c : emoji.toCharArray()) {
            if (SOFT_BANK_MIN <= c && SOFT_BANK_MAX >= c) {
                System.out.println(c);
            }
        }

//        System.out.println(String.valueOf(100));
//        String emoji = "@Inart Damon \uD83D\uDC2C 发送测试\uD83D\uDC7F \uD83D\uDE04\uD83D\uDE37";
//        String feiji = utfToSoftbank.get("✈️");
//        System.out.println("\\u" + feiji);
//        EmojiUtil.toAlias(JSONUtil.toJsonStr(EmojiTest.MAP));
//        System.out.println(EmojiParser.parseToAliases(emoji));
//        System.out.println(parseToSoftbank(emoji));
//
//        StringBuilder sb = new StringBuilder();
//        for (String temp : EmojiTest.UNICODE_TO_SOFT_BANK_MAP.keySet()) {
//            sb.append(temp);
//        }
//
//        for (int i = 0; i < sb.length(); i++) {
//            String temp = EmojiTest.SOFT_BANK_TO_UNICODE_MAP.get(String.valueOf(sb.charAt(i)));
//            if (null != temp) {
//                System.out.println(temp);
//            }
//        }
//        System.out.println(JSONUtil.toJsonStr(EmojiTest.MAP));
    }


    /**
     * utf-16be 转 soft bank
     * done
     * @param input
     * @return
     */
    public static String parseToSoftbank(String input) {
        EmojiTransformer emojiTransformer = unicodeCandidate -> {
            String softBank = EmojiTest.UNICODE_TO_SOFT_BANK_MAP.get(unicodeCandidate.getEmoji().getUnicode());
            return StrUtil.isEmpty(softBank) ? unicodeCandidate.getFitzpatrickUnicode() : softBank;
        };

        return EmojiParser.parseFromUnicode(input, emojiTransformer);
    }
}
