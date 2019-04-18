package vip.wen.distribute.chaptor02;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javax.xml.stream.XMLStreamConstants;

public class XmlSerializer implements ISerializer{

    // Xstream工具包
    XStream xStream = new XStream(new DomDriver());

    @Override
    public <T> byte[] serializer(T obj) {
        return xStream.toXML(obj).getBytes();
        //return new byte[0];
    }

    @Override
    public <T> T doSerializer(byte[] data, Class<T> clazz) {
        return (T)xStream.fromXML(new String(data));
        //return null;
    }
}
