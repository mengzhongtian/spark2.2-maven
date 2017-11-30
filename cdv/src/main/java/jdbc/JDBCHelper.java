package jdbc;


import conf.ConfigurationManager;
import constant.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * JDBC辅助组件
 *
 * @author Administrator
 */
public class JDBCHelper {


    /**
     * 加载数据库驱动
     * 静态代码块
     */
    static {
        try {
            Class.forName(ConfigurationManager.getProperty(Constants.JDBC_DRIVER));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 成员变量
     */

    private static JDBCHelper instance = null;
    public LinkedList<Connection> datasource = new LinkedList<Connection>();

    /**
     * 构造方法
     */
    private JDBCHelper() {
        int size = ConfigurationManager.getInteger(Constants.JDBC_DATASOURCE_SIZE);
        for (int i = 1; i <= size; i++) {
            String url = ConfigurationManager.getProperty(Constants.JDBC_URL);
            String user = ConfigurationManager.getProperty(Constants.JDBC_USER);
            String pswd = ConfigurationManager.getProperty(Constants.JDBC_PASSWORD);

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                datasource.add(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取单例
     *
     * @return 单例
     */
    public static JDBCHelper getInstance() {
        if (instance == null) {
            synchronized (JDBCHelper.class) {
                if (instance == null) {
                    instance = new JDBCHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 获取连接
     */
    public synchronized Connection getConnection() {
        while (datasource.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return datasource.poll();
    }


    /**
     * 对数据库更新的操作
     */
    public int executeUpdate(String sql, Object[] args) {
        int ii = 0;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            ii = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                datasource.add(connection);
            }
        }

        return ii;

    }

    /**
     * 对数据库进行查询的操作
     */

    public void executeQuery(String sql, Object[] args, QueryCallback callback) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);

            }
            ResultSet resultSet = preparedStatement.executeQuery();
            //调用接口里的方法。
            callback.process(resultSet);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                datasource.add(connection);
            }
        }

    }

    /**
     * 批量执行sql语句
     */
    public int[] executeBatch(String sql, List<Object[]> paramList) {
        Connection connection = null;
        int[] ints = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (Object[] ob : paramList) {
                for (int i = 0; i < ob.length; i++) {
                    preparedStatement.setObject(i + 1, ob[i]);

                }
                preparedStatement.addBatch();

            }
            ints = preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                datasource.add(connection);
            }
        }
        return ints;
    }


    /**
     * 查询回调接口
     */
    public static interface QueryCallback {
        void process(ResultSet rs) throws SQLException;

    }


}