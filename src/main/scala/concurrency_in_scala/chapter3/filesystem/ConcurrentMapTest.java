package concurrency_in_scala.chapter3.filesystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapTest {

    public static void main(String[] args) {
        Map<String, Void> m = new ConcurrentHashMap<>();

        m.put("abc", null);
    }

}
