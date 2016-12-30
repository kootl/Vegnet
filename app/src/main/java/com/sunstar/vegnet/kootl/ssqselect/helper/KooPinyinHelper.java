package com.sunstar.vegnet.kootl.ssqselect.helper;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * Created by louisgeek on 2016/12/26.
 */

public class KooPinyinHelper {
    /**
     * 如果c为汉字，则返回大写拼音；如果c不是汉字，则返回String.valueOf(c)
     * Pinyin.toPinyin(char c)
     * c为汉字，则返回true，否则返回false
     * Pinyin.isChinese(char c)
     *
     * @param inStr
     * @return
     */
    public static String parseCharToPinyin(String inStr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inStr.length(); i++) {
            if (inStr.equals("重庆")) {
                inStr = "#chongqing#重庆";//修正多音字
            }
            stringBuilder.append(Pinyin.toPinyin(inStr.charAt(i)));

        }
        return stringBuilder.toString();
    }

    /**
     * String str = "你好世界";
     * PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK); // nǐ,hǎo,shì,jiè
     * PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER); // ni3,hao3,shi4,jie4
     * PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
     * PinyinHelper.getShortPinyin(str); // nhsj
     *
     * @return
     */
    public static String parseStrToPinyin(String str) {
        String result = null;
        try {
            result = PinyinHelper.convertToPinyinString(str, "");
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return result;
    }
}
