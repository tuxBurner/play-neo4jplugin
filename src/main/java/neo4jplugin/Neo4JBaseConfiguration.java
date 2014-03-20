package neo4jplugin;


import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.lifecycle.AuditingEventListener;
import play.Logger;

public class Neo4JBaseConfiguration extends Neo4jConfiguration {

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
            Logger.warn("Could not find configuration: "+BASE_PACKAGES_CFG+" falling back to: neo4j as basepackage.");
            basePackages = "neo4j";
        }

        setBasePackage(basePackages);
    }

    @Bean
    public AuditingEventListener auditingEventListener() throws Exception {
        return new AuditingEventListener(new IsNewAwareAuditingHandler(isNewStrategyFactory()));
    }

}
