package neo4jplugin.configuration;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import neo4jplugin.Neo4jPlugin;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import play.Logger;

/**
 *
 * This is the base configuration for the Neo4j Plugin.
 * @author Sebastian Hardt (sebasth@gmx.de)
 *         Date: 27.08.15
 *         Time: 21:04
 */
public class Neo4JBaseConfiguration extends Neo4jConfiguration
{

  /**
   * Config key which defines where the base packages are located.
   */
  private static String BASE_PACKAGES_CFG = "neo4j.basepackage";

  /**
   * The base package where to lookup classes for neo4j.
   */
  private String basePackages = "neo4j";


  public Neo4JBaseConfiguration() {
    super();
    try {
      basePackages  = ConfigFactory.load().getString(BASE_PACKAGES_CFG);
    } catch(ConfigException cfge) {
      Logger.warn("Could not find configuration: " + BASE_PACKAGES_CFG + " falling back to: neo4j as basepackage.");
      basePackages = "neo4j";
    }
  }

 /* @Bean
  public AuditingEventListener auditingEventListener() throws Exception {

    return new AuditingEventListener(new ObjectFactory<IsNewAwareAuditingHandler>() {
      @Override
      public IsNewAwareAuditingHandler getObject() throws BeansException
      {
        try {
          return new IsNewAwareAuditingHandler(neo4jMappingContext());
        } catch (Exception e) {
          throw new BeanCreationException("Error while creating "+IsNewAwareAuditingHandler.class.getName(),e);
        }
      }
    });
  }      */

  @Bean
  public SessionFactory getSessionFactory() {
    // with domain entity base package(s)
    return new SessionFactory(basePackages);
  }


  public Session getSession() throws Exception {
    return super.getSession();
  }

  /**
   * Run a block of code in a NEO4J transaction.
   *
   * @param block    Block of code to execute.
   */
  public static <T> T withTransaction(play.libs.F.Function0<T> block) throws Throwable {
    T result = block.apply();
    return result;
    /*try {
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().begin();
      T result = block.apply();
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().commit();
      return result;
    } catch (Throwable t) {
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().rollback();
      throw t;
    } finally {

    }*/
  }

}

