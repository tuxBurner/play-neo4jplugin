package neo4jplugin.configuration;

import neo4jplugin.Neo4jPlugin;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

/**
 *
 * This is the base configuration for the Neo4j Plugin.
 * @author Sebastian Hardt (sebasth@gmx.de)
 *         Date: 27.08.15
 *         Time: 21:04
 */
public abstract class Neo4JBaseConfiguration extends Neo4jConfiguration
{

  public Neo4JBaseConfiguration() {
    super();
  }

  // FIXME SDN4.1
//  @Bean
//  public AuditingEventListener auditingEventListener() throws Exception {
//
//    return new AuditingEventListener(new ObjectFactory<IsNewAwareAuditingHandler>() {
//      @Override
//      public IsNewAwareAuditingHandler getObject() throws BeansException
//      {
//        try {
//          return new IsNewAwareAuditingHandler(neo4jMappingContext());
//        } catch (Exception e) {
//          throw new BeanCreationException("Error while creating "+IsNewAwareAuditingHandler.class.getName(),e);
//        }
//      }
//    });
//  }

  /**
   * Run a block of code in a NEO4J transaction.
   *
   * @param block    Block of code to execute.
   */
  public static <T> T withTransaction(play.libs.F.Function0<T> block) throws Throwable {
      return Neo4jPlugin.withTransaction(block);
  }

}

