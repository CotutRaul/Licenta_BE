package uvt.cotut.licenta_be.restclient.imgur;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Component
@Deprecated
public class ImgurRestClient {

    @Value("${rest-service.imgur.url}")
    private String imgurRestServiceUrl;

    @Value("${rest-service.imgur.key}")
    private String imgurRestServiceKey;

    @Deprecated
    public String uploadImage(MultipartFile image) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", Base64.encodeBase64String(image.getBytes()))
                .build();
        Request request = new Request.Builder()
                .url(imgurRestServiceUrl)
                .method("POST", body)
                .addHeader("Authorization", "Client-ID " + imgurRestServiceKey)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("data").get("link").asText();
        } else {
            throw new IOException("Unexpected error while uploading image: " + response);
        }
    }

}


