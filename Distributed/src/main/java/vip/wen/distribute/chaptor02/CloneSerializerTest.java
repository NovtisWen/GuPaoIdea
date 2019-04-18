package vip.wen.distribute.chaptor02;

public class CloneSerializerTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Email email = new Email();
        email.setContent("晚上8点上课");
        Person p1 = new Person();
        p1.setName("Wen");
        p1.setEmail(email);

        Person p2 = (Person) p1.clone();
        p2.setName("Han");
        p2.setEmail(email);

        System.out.println(p1.getName()+"->"+p1.getEmail().getContent());
        System.out.println(p2.getName()+"->"+p2.getEmail().getContent());
    }
}
