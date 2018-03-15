package com.github.tuxburner.neo4j;

import com.typesafe.config.ConfigException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import scala.Option;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * This initializes the spring data neo4j configuration
 *
 * @author tuxburner
 */
@Singleton
public class InitSpringDataNeo4J
{

  @Inject
  InitSpringDataNeo4J(play.api.Configuration playConfiguration)
  {

    //https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#reference:configuration:driver
    //https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#_multiple_entity_packages

    final Option<List<String>> stringList = playConfiguration.getStringList("neo4j.packagesToScan");

    if(stringList.isEmpty()) {

      final String errorMessage = "FAILURE: No packages to scan defined at: neo4j.packagesToScan !";

      if(Neo4jModule.LOGGER.isErrorEnabled()) {
        Neo4jModule.LOGGER.error(errorMessage);
      }
      throw new ConfigException.Missing(errorMessage);
    }

    AnnotationConfigApplicationContext springConfigApplicationContext = new AnnotationConfigApplicationContext();


    // check for driver here
    /*Configuration configuration = new Configuration.Builder()
      .
      .build();*/

  }

}
