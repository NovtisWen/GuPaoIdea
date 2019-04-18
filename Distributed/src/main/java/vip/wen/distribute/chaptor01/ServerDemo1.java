package vip.wen.distribute.chaptor01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo1 implements Serializable {

    public static void main(String[] args){
        ServerSocket server= null;
        try {
            server = new ServerSocket(8080);
            Socket socket = server.accept();//阻塞过程
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter os = new PrintWriter(socket.getOutputStream());

            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("client:"+is.readLine());
            String line = sin.readLine();
            while (!line.equals("bye")){
                os.println(line);
                os.flush();//清空缓存
                System.out.println("server:"+line);
                System.out.println("client:"+is.readLine());

                line = sin.readLine();
            }
            os.close();
            is.close();
            socket.close();
        }catch (Exception e){

        }finally {

        }
    }
}
