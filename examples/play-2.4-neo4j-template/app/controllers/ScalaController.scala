package controllers

import play.api.mvc._
import neo4j.models.World
import neo4j.services.{Neo4jServiceProviderImpl, GalaxyService}

class ScalaController extends Controller {



  def index = Neo4jTransactionAction {
    def galaxyService: GalaxyService = Neo4jServiceProviderImpl.get().galaxyService;

    if (galaxyService.getNumberOfWorlds() == 0) {
      galaxyService.makeSomeWorldsAndRelations()
    }

    def allWorlds: java.util.List[World] = galaxyService.getAllWorlds()
    def first: World = allWorlds.get(0)
    def last: World = allWorlds.get(allWorlds.size() - 1)
    def pathFromFirstToLast: java.util.List[World] = galaxyService.getWorldPath(first, last)

    Ok(views.html.index.render(allWorlds, pathFromFirstToLast))
  }

}
