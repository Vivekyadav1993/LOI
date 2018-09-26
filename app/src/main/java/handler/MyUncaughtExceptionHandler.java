package handler;

import java.io.IOException;

/**
 * Created by lenovo on 8/14/2018.
 */

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler  {



    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        if (throwable.getClass().equals(OutOfMemoryError.class)) {
            try {
                android.os.Debug.dumpHprofData("/sdcard/dump.hprof");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throwable.printStackTrace();

    }
}
