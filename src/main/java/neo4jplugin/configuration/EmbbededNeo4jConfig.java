package neo4jplugin.configuration;


import com.typesafe.config.ConfigFactory;
import neo4jplugin.Neo4jPlugin;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.support.GraphRepositoryFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

/**
 * Configuration which is used to connect to embedded database.
 */
@EnableTransactionManagement
@Configuration
@EnableNeo4jRepositories(basePackages = "neo4j.repositories",repositoryFactoryBeanClass = GraphRepositoryFactoryBean.class)
@ComponentScan("neo4j")
public class EmbbededNeo4jConfig extends Neo4JBaseConfiguration
{

  // FIXME SDN4.1
//  /**
//   * Config key which defines where the embedded database path is.
//   */
//  private static String EMBEDDED_DB_CFG_KEY = "neo4j.embeddedDB";
//
//
//  @Bean
//  public GraphDatabaseService graphDatabaseService() {
//    String embeddedDB = ConfigFactory.load().getString(EMBEDDED_DB_CFG_KEY);
//    if (StringUtils.isEmpty(embeddedDB) == true) {
//      throw new RuntimeException("Could not find config for embedded DB: " + EMBEDDED_DB_CFG_KEY);
//    }
//
//    if(Neo4jPlugin.LOGGER.isDebugEnabled() == true) {
//      Neo4jPlugin.LOGGER.debug("Connecting to embedded database: "+embeddedDB);
//    }
//
//    GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();
//
//    final File embeddedStore = new File(embeddedDB);
//
//    return graphDatabaseFactory.newEmbeddedDatabase(embeddedStore);
//  }

  @Override
  public SessionFactory getSessionFactory()
  {
    return new SessionFactory("neo4j.models");
  }
}

