/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdata;

import POJO.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tony
 */
public class ServerData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(12348);
            while (true){
                System.out.println("服务器等待连接...");
                Socket socket = serverSocket.accept();
                System.out.println("客户端连接成功！");
                
                PrintStream ps = new PrintStream(socket.getOutputStream());//打开输出流

                //-----打开输入流-------------------------------------
                InputStream is=null;
                InputStreamReader isr=null;
                BufferedReader br=null;
                OutputStream os=null;
                PrintWriter pw=null;
                is = socket.getInputStream();     //获取输入流
                isr = new InputStreamReader(is,"UTF-8");
                br = new BufferedReader(isr);
                String info = null;
                //--------------------------------------------------
                info=br.readLine();
                if(!info.isEmpty()){
                    DBService dbService = DBService.getDbService();
                    List<User> users = dbService.getOneUserData(info);
                    if(!users.get(0).getName().equals("Wrong")){
                        String name=users.get(0).getName();
                        String phone=users.get(0).getPhone();
                        String score=users.get(0).getScore();
                        ps.println(name);
                        ps.println(phone);
                        ps.println(score);
                        ps.flush();
                        System.out.println("name:"+name);
                        System.out.println("phone:"+phone);
                        System.out.println("score:"+score);
                        System.out.println("------------------------");
                    }else{
                        ps.println("NO");
                        ps.flush();
                    }  
                }
  
//               }else if("create".equals(info)){
//                List<User> list=new ArrayList<User>();
//                User user1 = new User();
//                User user2 = new User();
//                user1.setName("小秀秀");
//                user1.setPhone("19984521564");
//                user1.setScore("88");
//                list.add(0,user1);
//                
//                user2.setName("孙大圣");
//                user2.setPhone("12548974565");
//                user2.setScore("90");
//                list.add(1,user2);
//                
//                DBService dbService = DBService.getDbService();
//                dbService.insertUserData(list);
//                
//            }else{
//                     System.out.println("难道我会到这里来？？？？");
//                     System.out.println(info);
//                }
//                

                socket.shutdownInput();//关闭输入流
                socket.close(); 
            }

  

    }
    
}

