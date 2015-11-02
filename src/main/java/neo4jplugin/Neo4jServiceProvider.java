package neo4jplugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jTemplate;

/**
 * This is the main class of the Neo4jServiceProvider. This must be overwritten by you and can than hold some spring
 * stuff.
 *
 * <code>
 * @Component
 *  public class Neo4jServiceProviderImpl extends Neo4jServiceProvider
 *  {
 *
 *
 *    @Autowired
 *    public GalaxyService galaxyService;
 *
 *    public static Neo4jServiceProviderImpl get() {
 *      return Neo4jPlugin.get();
 *     }
 *   }
 * </code>
 * @author Sebastian Hardt (s.hardt@micromata.de)
 *         Date: 27.08.15
 *         Time: 21:33
 */
public class Neo4jServiceProvider
{

  @Autowired
  public Neo4jTemplate template;
}
