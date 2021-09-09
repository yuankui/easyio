package io.github.yuankui.easyio.compiler.base64;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class URITest {
    @Test
    public void test() throws IOException {
        URI uri = URI.create("string:///hello");

        System.out.println("uri.toASCIIString() = " + uri.toASCIIString());
        File file = new File(uri);
        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String s = bufferedReader.readLine();

        System.out.println("s = " + s);


    }
}
