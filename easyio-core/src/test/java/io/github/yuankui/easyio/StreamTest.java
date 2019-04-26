package io.github.yuankui.easyio;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class StreamTest {
    @Test
    public void test() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", "yuaknui");
        map.put("hello", "333");
        map.put("age", "22");
        
        map.entrySet().forEach(System.out::println);
    }
}
