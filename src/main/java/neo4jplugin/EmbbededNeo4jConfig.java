package plugins.neo4j;

import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang.StringUtils;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.kernel.impl.transaction.SpringTransactionManager;
import org.neo4j.kernel.impl.transaction.UserTransactionImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.JtaTransactionManagerFactoryBean;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * @author tuxburner
 * Neo4j Plugin with spring data
 */
@EnableTransactionManagement
@Configuration
@EnableNeo4jRepositories(basePackages = "neo4j.repositories")
@ComponentScan("neo4j")
public class EmbbededNeo4jConfig extends Neo4jConfiguration {

    private static  String embeddedDBPath = "neo4j.embeddedDB";


    @Bean
    public GraphDatabaseAPI graphDatabaseService() {
        String embeddedDB = ConfigFactory.load().getString(embeddedDBPath);
        if(StringUtils.isEmpty(embeddedDB) == true) {
            throw new RuntimeException("Could not find config for embedded DB: "+embeddedDBPath);
        }

        return new EmbeddedGraphDatabase(embeddedDB);
    }
}
