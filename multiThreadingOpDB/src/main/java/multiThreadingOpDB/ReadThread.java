package multiThreadingOpDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName ReadThread
 * @Description TODO
 * @Author shichao.chen
 * @Date 2019/8/7 10:56
 * @Version 1.0
 **/
public class ReadThread implements Runnable {
    private DBManger dbManger = new DBManger();
    Connection conn = dbManger.getConnection();
    int start = 0;
    int end = -1;
    BlockingQueue<Integer> buffer;

    public ReadThread(BlockingQueue<Integer> buffer) throws SQLException, ClassNotFoundException {
        this.buffer = buffer;
    }

    public synchronized void run() {
        System.out.println("线程" + Thread.currentThread().getName() + "正在执行。。");
        int sum =  getSum();
        int size = 5;
        int page = sum % size == 0 ? sum / size : sum / size + 1;
        System.out.println(sum + "    " + page);
        for (int i = 0; i < page; i++) {
            start = end + 1;
            end = start + size - 1;
            try {
                sel(start, size);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            buffer.put(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getSum()  {
        String sql = "select count(*) from test1";
        ResultSet resultSet = null;
        try {
            resultSet = dbManger.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public synchronized void sel(int start, int size) throws InterruptedException, SQLException {
        CallableStatement call = null;
        System.out.println(start+"  "+size);
        String sql = "{call test1Sel(?,?)}";
        call = conn.prepareCall(sql);
        call.setInt(1, start);
        call.setInt(2, size);
        //call.registerOutParameter(3, Types.);
        boolean hadResults = call.execute();
        ResultSet resultSet = call.getResultSet();
        //resultSet.last();
        while (resultSet != null && resultSet.next()) {
            buffer.put(resultSet.getInt(1));
        }
//        while (hadResults) {
//            ResultSet resultSet = call.getResultSet();
//            //resultSet.last();
//            while (resultSet != null && resultSet.next()) {
//                System.out.println("jieguo"+resultSet.getInt(1));
//                buffer.put(resultSet.getInt(1));
//            }
//            hadResults = call.getMoreResults();
//        }
    }
}
