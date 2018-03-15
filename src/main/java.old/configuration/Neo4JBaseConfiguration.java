package neo4jplugin.configuration;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import neo4jplugin.Neo4jPlugin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.lifecycle.AuditingEventListener;
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


  public Neo4JBaseConfiguration() {
    super();

    // check if baspackes is defined in the configuration if not fall back to neo4j
    String basePackages;
    try {
      basePackages  = ConfigFactory.load().getString(BASE_PACKAGES_CFG);
    } catch(ConfigException cfge) {
      Logger.warn("Could not find configuration: " + BASE_PACKAGES_CFG + " falling back to: neo4j as basepackage.");
      basePackages = "neo4j";
    }

    setBasePackage(basePackages);
  }

  @Bean
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
  }

  /**
   * Run a block of code in a NEO4J transaction.
   *
   * @param block    Block of code to execute.
   */
  public static <T> T withTransaction(play.libs.F.Function0<T> block) throws Throwable {
    try {
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().begin();
      T result = block.apply();
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().commit();
      return result;
    } catch (Throwable t) {
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().rollback();
      throw t;
    } finally {

    }
  }

}

