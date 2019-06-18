package vip.wen.distribute.chaptor02;

import com.alibaba.fastjson.JSON;

public class FastjsonSerializer implements ISerializer{


    @Override
    public <T> byte[] serializer(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T doSerializer(byte[] data, Class<T> clazz) {

        return (T)JSON.parseObject(new String(data),clazz);
    }
}
