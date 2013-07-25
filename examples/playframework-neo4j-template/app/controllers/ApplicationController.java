package controllers;

import neo4j.models.World;
import neo4j.services.GalaxyService;
import neo4j.services.Neo4JServiceProvider;
import play.mvc.Controller;
import play.mvc.Result;
import plugins.neo4j.Transactional;

import java.util.List;

/**
 * @author tuxburner
 */
public class ApplicationController extends Controller {

    @Transactional
    public static Result index() {

        final GalaxyService galaxyService = Neo4JServiceProvider.get().galaxyService;

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
