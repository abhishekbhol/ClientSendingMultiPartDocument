package com.company;

import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String FilePath = "C:\\MultiDataTest\\test.pdf";
        String TargetURL = "https://abhishek.com/apiv1/test-multidata";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(TargetURL);

        File file = new File(FilePath);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, FilePath);

        HttpEntity entity = builder.build();
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        System.out.println("Statuscode : " +response.getStatusLine().getStatusCode());

        String result = getStringFromInputStream(response.getEntity().getContent());

        System.out.println("Response : " + result);
        client.close();
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
