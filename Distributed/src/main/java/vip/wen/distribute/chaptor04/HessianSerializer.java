package vip.wen.distribute.chaptor04;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import vip.wen.distribute.chaptor02.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements ISerializer {

    @Override
    public <T> byte[] serializer(T obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(outputStream);
        try {
            hessianOutput.writeObject(obj);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T doSerializer(byte[] data, Class<T> clazz) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        HessianInput hessianInput = new HessianInput(inputStream);
        try {
            return (T)hessianInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
