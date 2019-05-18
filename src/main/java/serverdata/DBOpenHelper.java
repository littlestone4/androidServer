package serverdata;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOpenHelper {

    private static String driver = "com.mysql.cj.jdbc.Driver";//MySQL 驱动   com.mysql.cj.jdbc.Driver
    private static String url = "jdbc:mysql://127.0.0.1:3306/dgut_smarthomepro?useSSL=false&serverTimezone=GMT%2B8";//MYSQL数据库连接Url
    private static String user = "root";//用户名
    private static String password = "wybqsj1068";//密码

    /**
     * 连接数据库
     * */

    public static Connection getConn(){
        Connection conn = null;
        try {
            //System.out.println("提示:正在尝试获取驱动");
            //就是这里无法获取Class
            Class.forName(driver);//获取MYSQL驱动
            //System.out.println("提示:获取驱动成功！");
            conn = (Connection) DriverManager.getConnection(url, user, password);//获取连接
            //System.out.println("提示:获取连接啦啦啦啦啦啦！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库
     * */

    public static void closeAll(Connection conn, PreparedStatement ps){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭数据库
     * */

    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
//---------------------
//        作者：Penny-听海
//        来源：CSDN
//        原文：https://blog.csdn.net/weixin_37730482/article/details/77984417
//        版权声明：本文为博主原创文章，转载请附上博文链接！
