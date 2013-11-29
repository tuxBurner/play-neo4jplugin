package neo4jplugin;

import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tuxburner
 *         Neo4j Plugin with spring data
 */
@EnableTransactionManagement
@Configuration
@EnableNeo4jRepositories(basePackages = "neo4j.repositories")
@ComponentScan("neo4j")
public class EmbbededNeo4jConfig extends Neo4JBaseConfiguration {

    private static String embeddedDBPath = "neo4j.embeddedDB";


    @Bean
    public GraphDatabaseService graphDatabaseService() {
        String embeddedDB = ConfigFactory.load().getString(embeddedDBPath);
        if (StringUtils.isEmpty(embeddedDB) == true) {
            throw new RuntimeException("Could not find config for embedded DB: " + embeddedDBPath);
        }
        GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();

        return graphDatabaseFactory.newEmbeddedDatabase(embeddedDB);
    }
}
