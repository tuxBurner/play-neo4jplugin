package com.github.tuxburner.neo4j;

import com.google.inject.AbstractModule;
import play.Logger;

/**
 * Module for accessing neo4j via spring data
 *  @author tuxburner
 *
 */
public class Neo4jModule extends AbstractModule
{

  /**
   * The Logger for this Module.
   * Can be configured in your conf/logback.xml
   * &lt;logger name="com.github.tuxBurner.neo4j.Neo4jModule level="Debug" /&gt;
   */
  public static Logger.ALogger LOGGER = Logger.of(Neo4jModule.class);

  @Override
  protected void configure()
  {
    bind(InitSpringDataNeo4J.class).asEagerSingleton();
  }
}
