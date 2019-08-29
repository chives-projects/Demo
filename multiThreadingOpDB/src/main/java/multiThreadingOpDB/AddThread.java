package multiThreadingOpDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class AddThread implements Runnable {
    private DBManger dbManger = new DBManger();
    Connection conn = dbManger.getConnection();
    int start=0;
    int end=0;
    BlockingQueue<Integer> buffer;

    public AddThread(int start,int end,BlockingQueue<Integer> buffer) throws SQLException, ClassNotFoundException {
        this.start=start;this.end=end;
        this.buffer=buffer;
    }

    public synchronized void run() {
        System.out.println("线程" + Thread.currentThread().getName()+ "正在执行。。");
        while (true){
            int i = add1();
            if (i==-1)break;
            add(i);
        }
//        String sql = "{call testAdd1(?,?)}";
//        try {
//            conn=dbManger.getConnection();
//            call=conn.prepareCall(sql);
//            call.setInt(1,3);
//            call.registerOutParameter(2, Types.INTEGER);
//            call.execute();
//            int res=call.getInt(2);
//            System.out.println(res);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        //latch.countDown();
    }
    public void add(int i){
        CallableStatement call = null;
//        String sql = "{call testAdd1(?,?)}";
//        try {
//            conn=dbManger.getConnection();
//            call=conn.prepareCall(sql);
//            call.setInt(1,3);
//            call.registerOutParameter(2, Types.INTEGER);
//            call.execute();
//            int res=call.getInt(2);
//            System.out.println(res);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        String sql = "{call testAdd(?)}";
        try {
//            for (int i = start; i <= end; i++) {
                call = conn.prepareCall(sql);
                call.setInt(1, i);
                call.execute();
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int add1(){
        try {
            return buffer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

}
