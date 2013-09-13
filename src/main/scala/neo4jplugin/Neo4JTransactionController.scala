package neo4jplugin 

import play.api.mvc._
import neo4jplugin.{ServiceProvider, Neo4JPlugin}

/**
 * Created with IntelliJ IDEA.
 * User: tuxburner
 * Date: 9/12/13
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
trait Neo4JTransactionController extends Controller {

  def runInTransaction[A](bp: BodyParser[A])(f: Request[A] => Result) =
  Action(bp) {
      request =>
        val blubber:ServiceProvider = Neo4JPlugin.get();
        try {
          blubber.template.getGraphDatabase.getTransactionManager.begin;
      val blabber = f(request)
          blubber.template.getGraphDatabase.getTransactionManager.commit;

        blabber
          //f(request)
        }
        catch {
          case t: Throwable => {
            blubber.template.getGraphDatabase.getTransactionManager.rollback
            throw t
          }
        }
    }

}
