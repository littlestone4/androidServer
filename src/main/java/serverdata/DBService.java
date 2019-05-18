package serverdata;


import POJO.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBService {

    private Connection conn=null; //打开数据库对象
    private PreparedStatement ps=null;//操作整合sql语句的对象
    private ResultSet rs=null;//查询结果的集合

    //DBService 对象
    public static DBService dbService=null;

    /**
     * 构造方法 私有化
     * */

    private DBService(){

    }

    /**
     * 获取MySQL数据库单例类对象
     * */

    public static DBService getDbService(){
        if(dbService==null){
            dbService=new DBService();
        }
        return dbService;
    }


    /**
     * 获取要发送短信的患者信息    查
     * */

    public List<User> getUserData(){
        //结果存放集合
        
        List<User> list=new ArrayList<User>();
        
        //MySQL 语句
        String sql="select * from user";
        //获取链接数据库对象
        conn= DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                
                if(ps!=null){
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            User u=new User();
                            //u.setId(rs.getString("id"));
                            u.setName(rs.getString("name"));
                            u.setPhone(rs.getString("phone"));
                            u.setScore(rs.getString("score"));
                            
                            list.add(u);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        return list;
    }
    
    
    
       /**
     * 获取某个学生的信息    查
     * */

    public List<User> getOneUserData(String id){
        //结果存放集合
        
        List<User> list=new ArrayList<User>();
        
        //MySQL 语句
        String sql="select * from user where id=?";
        //获取链接数据库对象
        conn= DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                ps.setString(1,id);//第一个参数state 一定要和上面SQL语句字段顺序一致
                if(ps!=null){
                    rs= ps.executeQuery();
                    if(rs!=null){
                        if(rs.next()){
                            User u=new User();
                            //u.setId(rs.getString("id"));
                            u.setName(rs.getString("name"));
                            u.setPhone(rs.getString("phone"));
                            u.setScore(rs.getString("score"));
                            
                            list.add(u);
                        }
                        else{
                            User u=new User();
                            //u.setId(rs.getString("id"));
                            u.setName("Wrong");                           
                            list.add(u);
                            
                        }
                    }
                }
            }
        } catch (SQLException e) {
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        return list;
    }

    /**
     * 修改数据库中某个对象的状态   改
     * */

    public int updateUserData(String phone){
        int result=-1;
        if(!phone.isEmpty()){
            //获取链接数据库对象
            conn= DBOpenHelper.getConn();
            //MySQL 语句
            String sql="update user set score=? where phone=?";
            try {
                boolean closed=conn.isClosed();
                if(conn!=null&&(!closed)){
                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1,"1");//第一个参数state 一定要和上面SQL语句字段顺序一致
                    ps.setString(2,phone);//第二个参数 phone 一定要和上面SQL语句字段顺序一致
                    result=ps.executeUpdate();//返回1 执行成功
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }

    /**
     * 批量向数据库插入数据   增
     * */

    public int insertUserData(List<User> list){
        int result=-1;
        if((list!=null)&&(list.size()>0)){
            //获取链接数据库对象
            conn= DBOpenHelper.getConn();
            //MySQL 语句
            String sql="INSERT INTO user (name,phone,score) VALUES (?,?,?)";
            try {
                boolean closed=conn.isClosed();
                if((conn!=null)&&(!closed)){
                    for(User user:list){
                        ps= (PreparedStatement) conn.prepareStatement(sql);
                        String name=user.getName();
                        String phone=user.getPhone();                       
                        String score=user.getScore();
                        ps.setString(1,name);//第一个参数 name 规则同上
                        ps.setString(2,phone);//第二个参数 phone 规则同上
                        ps.setString(3,score);//第三个参数 content 规则同上
                        result=ps.executeUpdate();//返回1 执行成功
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }


    /**
     * 删除数据  删
     * */

    public int delUserData(String phone){
        int result=-1;
        if((!phone.isEmpty())){
            //获取链接数据库对象
            conn= DBOpenHelper.getConn();
            //MySQL 语句
            String sql="delete from user where phone=?";
            try {
                boolean closed=conn.isClosed();
                if((conn!=null)&&(!closed)){
                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1, phone);
                    result=ps.executeUpdate();//返回1 执行成功
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }

}

//---------------------
//        作者：Penny-听海
//        来源：CSDN
//        原文：https://blog.csdn.net/weixin_37730482/article/details/77984417
//        版权声明：本文为博主原创文章，转载请附上博文链接！