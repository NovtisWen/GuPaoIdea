package vip.wen.distribute.chaptor02;

public class SerializerTest {

    public static void main(String[] args){
        ISerializer iSerializer = new XmlSerializer();
        User user = new User();
        user.setAge(18);
        user.setName("Wen");
        byte[] bytes = iSerializer.serializer(user);

        System.out.println(new String(bytes));

        User user1 = iSerializer.doSerializer(bytes,User.class);
        System.out.println(user1+"->"+user1.getName());



    }
}
