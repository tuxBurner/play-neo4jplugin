package plugins.neo4j;


import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * Wraps an action in a Neo4J transaction.
 * Tooked the code from the jpa plaugin
 */
public class TransactionalAction extends Action<Transactional> {

    public Result call(final Context ctx) throws Throwable {
        return Neo4JPlugin.withTransaction(new play.libs.F.Function0<Result>() {
            public Result apply() throws Throwable {
                return delegate.call(ctx);
            }
        });
    }


}
