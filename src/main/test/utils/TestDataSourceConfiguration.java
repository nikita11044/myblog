package utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class TestDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new DriverManagerDataSource(
                PostgreSQLTestContainer.getInstance().getJdbcUrl(),
                PostgreSQLTestContainer.getInstance().getUsername(),
                PostgreSQLTestContainer.getInstance().getPassword()
        );
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
