package utils;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLTestContainer extends PostgreSQLContainer<PostgreSQLTestContainer> {
    private static PostgreSQLTestContainer container;

    private PostgreSQLTestContainer() {
        super("postgres:latest");
    }

    public static PostgreSQLTestContainer getInstance() {
        if (container == null) {
            container = new PostgreSQLTestContainer();
            container.start();
        }
        return container;
    }
}
