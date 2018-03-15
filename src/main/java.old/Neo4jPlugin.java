package neo4jplugin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.ConfigFactory;
import neo4jplugin.configuration.EmbbededNeo4jConfig;
import neo4jplugin.configuration.RestNeo4jConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import play.Logger;
import play.api.Play;
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
  public Neo4jPlugin(ApplicationLifecycle applicationLifecycle)
  {

    initialize();


    applicationLifecycle.addStopHook(() -> {

      Neo4jPlugin.get().template.getGraphDatabaseService().shutdown();
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
   */
  private void initialize()
  {

    String serviceProviderClassName = ConfigFactory.load().getString(SERVICE_PROVIDER_NAME_CFG);
    if (StringUtils.isEmpty(serviceProviderClassName) == true) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("No configuration for the neo4h ServiceProvider found: " + SERVICE_PROVIDER_NAME_CFG + " must be" +
            " set for this plugin.");
      }
      return;
    }
    try {
      final ClassLoader classLoader = Play.classloader(Play.current());
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


    final String mode = ConfigFactory.load().getString("neo4j.mode");

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
    try {
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().begin();
      T result = block.apply();
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().commit();
      return result;
    } catch (Throwable t) {
      Neo4jPlugin.get().template.getGraphDatabase().getTransactionManager().rollback();
      if (LOGGER.isErrorEnabled() == true) {
        LOGGER.error("An error happend while in transaction: ", t);
      }
      throw t;
    } finally {
      /// TODO: what should happen here :)
    }
  }
}
