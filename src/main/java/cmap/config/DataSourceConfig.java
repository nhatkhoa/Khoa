package cmap.config;

import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

// --- Cấu hình datasource cho Cloud
@Profile("cloud")
public class DataSourceConfig extends AbstractCloudConfig {
	@Bean
	public DataSource dataSource(){
		return connectionFactory().dataSource();
	}
}
