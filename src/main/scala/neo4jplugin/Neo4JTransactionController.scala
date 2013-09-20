package neo4jplugin

import play.api.mvc._

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
        val serviceProvider: ServiceProvider = Neo4JPlugin.get();
        val tx = serviceProvider.template.getGraphDatabase.beginTx;
        try {
          val result = f(request);
          tx.success()
          result
        }
        catch {
          case t: Throwable => {
            tx.failure();
            throw t
          }
        } finally {
          tx.finish();
        }
    }

}
