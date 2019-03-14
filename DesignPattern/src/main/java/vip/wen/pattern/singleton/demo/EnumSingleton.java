package vip.wen.pattern.singleton.demo;

/**
 * 枚举单例
 */
public enum EnumSingleton {
    INSTANCE;

    private Object data;

    private String code;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
