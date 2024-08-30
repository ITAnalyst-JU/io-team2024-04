package utils;

import java.io.OutputStream;
import java.io.PrintStream;

public class TestUtils {
    public static void discardSysOut() {
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {

            }
        }));
    }
}
