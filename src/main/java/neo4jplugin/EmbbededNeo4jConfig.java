package neo4jplugin;

import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.GraphRepositoryFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tuxburner
 *         Neo4j Plugin with spring data
 */
@EnableTransactionManagement
@Configuration
@EnableNeo4jRepositories(basePackages = "neo4j.repositories",repositoryFactoryBeanClass = GraphRepositoryFactoryBean.class)
@ComponentScan("neo4j")
public class EmbbededNeo4jConfig extends Neo4JBaseConfiguration
{

    /**
     * Config key which defines where the embedded database path is.
     */
    private static String EMBEDDED_DB_CFG_KEY = "neo4j.embeddedDB";


    @Bean
    public GraphDatabaseService graphDatabaseService() {
        String embeddedDB = ConfigFactory.load().getString(EMBEDDED_DB_CFG_KEY);
        if (StringUtils.isEmpty(embeddedDB) == true) {
            throw new RuntimeException("Could not find config for embedded DB: " + EMBEDDED_DB_CFG_KEY);
        }
        GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();

        return graphDatabaseFactory.newEmbeddedDatabase(embeddedDB);
    }
}
