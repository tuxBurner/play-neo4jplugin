package controllers;

import com.google.inject.Inject;
import neo4j.models.World;
import neo4j.services.GalaxyService;
import neo4j.services.Neo4jServiceProviderImpl;
import neo4jplugin.Neo4jPlugin;
import neo4jplugin.Neo4jTransactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * @author Sebastian Hardt (sebasth@gmx.de)
 *         Date: 27.08.15
 *         Time: 18:15
 */
public class JavaController extends Controller
{


  @Inject
  Neo4jPlugin neo4jPlugin;

  @Neo4jTransactional
  public Result index() {

    final GalaxyService galaxyService = Neo4jServiceProviderImpl.get().galaxyService;

    if (galaxyService.getNumberOfWorlds() == 0) {
      galaxyService.makeSomeWorldsAndRelations();
    }

    final List<World> allWorlds = galaxyService.getAllWorlds();
    final World first = allWorlds.get(0);
    final World last = allWorlds.get(allWorlds.size() - 1);
    final List<World> pathFromFirstToLast = galaxyService.getWorldPath(first, last);


    return ok(views.html.index.render(allWorlds, pathFromFirstToLast));
  }

}
