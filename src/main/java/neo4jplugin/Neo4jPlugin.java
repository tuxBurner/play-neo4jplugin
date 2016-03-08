package neo4jplugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import neo4jplugin.configuration.EmbbededNeo4jConfig;
import neo4jplugin.configuration.RestNeo4jConfig;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import play.Configuration;
import play.Environment;
import play.Logger;
import play.inject.ApplicationLifecycle;
import play.libs.F;

import java.lang.annotation.Annotation;

/**
 * This is the main singleton handling all the stuff :)
 *
 * @author Sebastian Hardt (sebasth@gmx.de)
 *         Date: 27.08.15
 *         Time: 16:45
 */
@Singleton
public class Neo4jPlugin
{

  public static Logger.ALogger LOGGER = Logger.of(Neo4jPlugin.class);

  private static ThreadLocal<Neo4jServiceProvider> neo4jProvider = new ThreadLocal<>();


  private static AnnotationConfigApplicationContext springContext = null;

  private final static String SERVICE_PROVIDER_NAME_CFG = "neo4j.serviceProviderClass";

  private static Class<?> serviceProviderClass = null;


  @Inject
  public Neo4jPlugin(Environment environment, Configuration configuration, ApplicationLifecycle applicationLifecycle)
  {

    initialize(environment, configuration);


    applicationLifecycle.addStopHook(() -> {

      // FIXME JU
//      Neo4jPlugin.get().template.getGraphDatabaseService().shutdown();
      springContext.stop();

      // TODO clean the application lifecycle here
      return F.Promise.pure(null);
    });
  }


  /**
   * Get the implementation of ServiceProvider holding the Neo4jTemplate and all your repositories.
   * This is configured in your application configuration with the key: neo4j.serviceProviderClass.
   *
   * @param <E> the implementation class of your ServiceProvider.
   * @return the configured implementation of the ServiceProvider.
   */
  public static <E extends Neo4jServiceProvider> E get()
  {
    neo4jProvider.set((Neo4jServiceProvider) springContext.getBean(serviceProviderClass));
    return (E) neo4jProvider.get();
  }


  /**
   * This does the initializiation of the plugin by wiring up the springcontext according to the configuration settings.
   * @param environment the play environment
   * @param configuration the play configuration
   */
  private void initialize(Environment environment, Configuration configuration)
  {

    String serviceProviderClassName = configuration.getString(SERVICE_PROVIDER_NAME_CFG);
    if (StringUtils.isEmpty(serviceProviderClassName) == true) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("No configuration for the neo4h ServiceProvider found: " + SERVICE_PROVIDER_NAME_CFG + " must be" +
            " set for this plugin.");
      }
      return;
    }
    final ClassLoader classLoader = environment.classLoader();

    try {
      serviceProviderClass = Class.forName(serviceProviderClassName, false, classLoader);
      Annotation annotation = serviceProviderClass.getAnnotation(Component.class);
      if (annotation == null) {
        if (LOGGER.isErrorEnabled()) {
          LOGGER.error("Class : " + serviceProviderClassName + " must be annotated with: " + Component.class
              .getCanonicalName());
        }
        return;
      }
    } catch (ClassNotFoundException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Error while getting Neo4J class from configuration: " + SERVICE_PROVIDER_NAME_CFG + " = " + serviceProviderClassName, e);
      }
      return;
    }


    final String mode = configuration.getString("neo4j.mode");

    if (mode.equals("embedded")) {
      if(LOGGER.isDebugEnabled() == true) {
        LOGGER.debug("Loading embedded configuration");
      }
      springContext = new AnnotationConfigApplicationContext(EmbbededNeo4jConfig.class);
    }

    if (mode.equals("remote")) {
      if(LOGGER.isDebugEnabled() == true) {
        LOGGER.debug("Loading remote configuration");
      }
      springContext = new AnnotationConfigApplicationContext(RestNeo4jConfig.class);
    }

    if (mode.equals("own")) {
      if(LOGGER.isDebugEnabled() == true) {
        LOGGER.debug("Loading own configuration");
      }
      String configurationClassName = configuration.getString("neo4j.ownConfigurationClass");
      if (StringUtils.isEmpty(configurationClassName) == true) {
        if (LOGGER.isErrorEnabled()) {
          LOGGER.error("if you use neo4j.mode = own, you must provide a configuration class neo4j.ownConfigurationClass");
          return;
        }
      }
      try {
        Class<?> configurationClass = Class.forName(configurationClassName, false, classLoader);
        springContext = new AnnotationConfigApplicationContext(configurationClass);
      } catch (ClassNotFoundException e) {
        if (LOGGER.isErrorEnabled()) {
          LOGGER.error("Error while getting Neo4J class for configuration: " + configurationClassName, e);
        }
      }
    }


    if (springContext == null) {
      if (LOGGER.isErrorEnabled() == true) {
        LOGGER.error("Could not load config must be: embedded or embeddedWithWebServer");
      }
    }

    springContext.start();
    //springContext.scan();
    springContext.getAutowireCapableBeanFactory().autowireBean(serviceProviderClass);
    springContext.registerShutdownHook();
  }

  /**
   * Run a block of code in a NEO4J transaction.
   *
   * @param block Block of code to execute.
   */
  public static <T> T withTransaction(play.libs.F.Function0<T> block) throws Throwable
  {
    Transaction transaction = null;
    try {
      transaction = Neo4jPlugin.get().transactionManager.openTransaction();
      T result = block.apply();
      transaction.commit();
      return result;
    } catch (Throwable t) {
      if (transaction != null) {
        transaction.rollback();
      }
      if (LOGGER.isErrorEnabled() == true) {
        LOGGER.error("An error happened while in transaction: ", t);
      }
      throw t;
    } finally {
      /// TODO: what should happen here :)
    }
  }
}
