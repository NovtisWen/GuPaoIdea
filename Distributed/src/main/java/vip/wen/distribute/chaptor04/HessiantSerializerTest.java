package vip.wen.distribute.chaptor04;

import vip.wen.distribute.chaptor02.FastjsonSerializer;
import vip.wen.distribute.chaptor02.ISerializer;
import vip.wen.distribute.chaptor02.User;

public class HessiantSerializerTest {

    public static void main(String[] args){
        //Hessian 序列化
        ISerializer iSerializer = new HessianSerializer();
        User user = new User();
        user.setAge(18);
        user.setName("Wen");

        byte[] bytes = iSerializer.serializer(user);
        System.out.println("byte.length:"+bytes.length);
        System.out.println(new String(bytes));

        User user1 = iSerializer.doSerializer(bytes,User.class);
        System.out.println(user1);



    }
}
