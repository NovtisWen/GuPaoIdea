package vip.wen.distribute.chaptor02;

public class SerializerTest {

    public static void main(String[] args){
        //XStream XML 序列化
        //ISerializer iSerializer = new XmlSerializer();

        //FastJson 序列化
        ISerializer iSerializer = new FastjsonSerializer();
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
