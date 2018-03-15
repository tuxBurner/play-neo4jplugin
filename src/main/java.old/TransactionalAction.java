package neo4jplugin;


import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * @author Sebastian Hardt (s.hardt@micromata.de)
 *         Date: 30.08.15
 *         Time: 13:22
 */
public class TransactionalAction extends Action<Neo4jTransactional>
{

  public F.Promise<Result> call(final Http.Context ctx) throws Throwable {
    return Neo4jPlugin.withTransaction(new F.Function0<F.Promise<Result>>() {
      public F.Promise<Result> apply() throws Throwable {
        return delegate.call(ctx);
      }
    });
  }
}
