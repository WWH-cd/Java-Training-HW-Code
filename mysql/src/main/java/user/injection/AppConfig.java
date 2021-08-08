package user.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class AppConfig {
    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties("app.datasource.mysql")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    public JdbcTemplate getJdbcTemplate() throws ClassCastException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }
}
