package neo4j.services;


import neo4jplugin.Neo4jPlugin;
import neo4jplugin.Neo4jServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Neo4jServiceProviderImpl extends Neo4jServiceProvider
{


  @Autowired
  public GalaxyService galaxyService;

  public static Neo4jServiceProviderImpl get() {
    return Neo4jPlugin.get();
  }
}
