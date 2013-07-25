package controllers


import play.api.mvc._
import neo4j.services.{Neo4JServiceProvider, GalaxyService}
import neo4j.models.World

object Application extends Controller {

	def index = Action {

    def galaxyService: GalaxyService = Neo4JServiceProvider.get().galaxyService;

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