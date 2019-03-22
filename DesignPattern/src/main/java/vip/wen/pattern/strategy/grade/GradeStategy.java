package vip.wen.pattern.strategy.grade;

import java.util.HashMap;
import java.util.Map;

public class GradeStategy{
    /**普通合伙人**/
    public static final String GENERAL_GARDER = "1";
    /**银牌合伙人**/
    public static final String SILVER_GARDER = "2";
    /**金牌合伙人**/
    public static final String GOLD_GARDER = "3";
    /**白金合伙人**/
    public static final String PLATINUM_GARDER = "4";
    /**钻石合伙人**/
    public static final String DIAMOND_GARDER = "5";

    private static Map<String, Grade> GRADE_MAP = new HashMap<String, Grade>();

    static{
        GRADE_MAP.put(GENERAL_GARDER, new NormalGrade());
        GRADE_MAP.put(SILVER_GARDER, new SilverGrade());
        GRADE_MAP.put(GOLD_GARDER, new GoldGrade());
        GRADE_MAP.put(PLATINUM_GARDER, new PlatinumGrade());
        GRADE_MAP.put(DIAMOND_GARDER, new DiamondGrade());
    }

    public static Grade getGradeByKey(String gradeKey){
        if(!GRADE_MAP.containsKey(gradeKey)){
            return GRADE_MAP.get(gradeKey);
        }
        return null;
    }

}
