package vip.wen.distribute.chaptor02;

/**
 * 序列化
 */
public interface ISerializer {

    <T> byte[] serializer(T obj);

    <T> T doSerializer(byte[] data,Class<T> clazz);
}
