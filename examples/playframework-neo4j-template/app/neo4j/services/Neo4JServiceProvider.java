package neo4j.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plugins.neo4j.Neo4JPlugin;
import plugins.neo4j.ServiceProvider;

@Component
public class Neo4JServiceProvider extends ServiceProvider {


    @Autowired
    public GalaxyService galaxyService;

    public static Neo4JServiceProvider get() {
        return Neo4JPlugin.get();
    }
}