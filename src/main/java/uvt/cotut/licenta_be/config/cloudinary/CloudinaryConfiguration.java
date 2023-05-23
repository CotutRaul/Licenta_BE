package uvt.cotut.licenta_be.config.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

    @Value("${rest-service.cloudinary.name}")
    private String name;

    @Value("${rest-service.cloudinary.api-key}")
    private String apiKey;

    @Value("${rest-service.cloudinary.secret-key}")
    private String secretKey;

    @Bean
    public Cloudinary cloudinaryConfig() {
        return new Cloudinary(String.format("cloudinary://%s:%s@%s", apiKey, secretKey, name));
    }
}
