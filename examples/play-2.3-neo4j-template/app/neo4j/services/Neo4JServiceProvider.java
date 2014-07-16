package neo4j.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import neo4jplugin.Neo4JPlugin;
import neo4jplugin.ServiceProvider;

@Component
public class Neo4JServiceProvider extends ServiceProvider {


    @Autowired
    public GalaxyService galaxyService;

    public static Neo4JServiceProvider get() {
        return Neo4JPlugin.get();
    }
}