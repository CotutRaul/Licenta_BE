package uvt.cotut.licenta_be;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Licenta API",
				version = "1.0.0",
				description = "Magazin online modular, o aplicatie web pentru vanzatorii independenti"
		))
public class LicentaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicentaBeApplication.class, args);
	}

}
