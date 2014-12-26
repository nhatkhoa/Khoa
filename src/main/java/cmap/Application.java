package cmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration

public class Application {

    public static void main(String[] args) {
    	// --- Cấu hình chạy trên heroku
    	String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "80";
		}
		System.setProperty("server.port", webPort);
		
		
		
        SpringApplication.run(Application.class, args);
    }
    
    
    
}
