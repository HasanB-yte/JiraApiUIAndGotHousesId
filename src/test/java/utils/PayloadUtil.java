package utils;

import stepDefinitions.JiraPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayloadUtil {

    public static String getPetPayload(int id, String name, String status) {
        return "{\\n\" +\n" +
                "                \"  \\\"id\\\": 0,\\n\" +\n" +
                "                \"  \\\"category\\\": {\\n\" +\n" +
                "                \"    \\\"id\\\": " + id + ",\\n\" +\n" +
                "                \"    \\\"name\\\": \\\"string\\\"\\n\" +\n" +
                "                \"  },\\n\" +\n" +
                "                \"  \\\"name\\\": \\\"" + name + "\\\",\\n\" +\n" +
                "                \"  \\\"photoUrls\\\": [\\n\" +\n" +
                "                \"    \\\"string\\\"\\n\" +\n" +
                "                \"  ],\\n\" +\n" +
                "                \"  \\\"tags\\\": [\\n\" +\n" +
                "                \"    {\\n\" +\n" +
                "                \"      \\\"id\\\": 0,\\n\" +\n" +
                "                \"      \\\"name\\\": \\\"string\\\"\\n\" +\n" +
                "                \"    }\\n\" +\n" +
                "                \"  ],\\n\" +\n" +
                "                \"  \\\"status\\\": \\\"" + status + "\\\"\\n\" +\n" +
                "                \"}";
    }


    public static String generateStringFromResource(String path) throws IOException {
        String getPayload = new String(Files.readAllBytes(Paths.get(path)));
        return getPayload;

    }


    public static String getCookieAuthPayload(String username, String password) {
        return "{\n" +
                "    \"username\": \"" + username + "\",\n" +
                "    \"password\": \"" + password + "\"\n" +
                "}";


    }

    public static String jiraCreateIssuePayload(String summary, String description, String issueType) {
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"TEC\"\n" +
                "        },\n" +
                "        \"summary\": \"" + summary + "\",\n" +
                "        \"description\": \"" + description + "\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"" + issueType + "\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

    }

    public static String getJiraAutorization() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("rest/auth/1/session");

        HttpPost post = new HttpPost(uriBuilder.build());

        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");

        HttpEntity entity = new StringEntity(PayloadUtil.getCookieAuthPayload(ConfigReader.getProperty("jiraUserName"), ConfigReader.getProperty("jiraPassword")));
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        JiraPojo jiraPojo = objectMapper.readValue(response.getEntity().getContent(), JiraPojo.class);

        String name = jiraPojo.getSession().get("name");
        String value = jiraPojo.getSession().get("value");

        return String.format("%s=%s", name, value);


    }



}



