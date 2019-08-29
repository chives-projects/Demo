package multiThreadingOpDB;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class demo {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            System.out.println((int) (Math.random() * 6));
//        }
//        System.out.println(ToPinyin("csc1"));
        BlockingQueue<Integer> buffer = new LinkedBlockingQueue<Integer>(10);

        new Thread(new ReadThread(buffer)).start();
        new Thread(new AddThread(0,0,buffer)).start();
//        int sum=1000;
//        int count=250;
//        int threadCount=sum%count==0?sum/count:sum/count+1;
//        int start=0,end=0;
//        //final CountDownLatch latch = new CountDownLatch(4);
//        for (int i = 0; i < threadCount;i++ ) {
//            start=end+1;
//            end=start+count-1;
//            new Thread(new AddThread(start,end)).start();
//        }
        //new Thread(new AddThread(0,5000,buffer)).start();
//        AddThread addThread=new AddThread();
//        new Thread(addThread).start();
//        if(!addThread.isAlive()){
//            long endTime =System.currentTimeMillis();
//            System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
//            System.out.println("end");
//        }
        //latch.await();1415
    }


    public static String ToPinyin(String chinese) {
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }
}
