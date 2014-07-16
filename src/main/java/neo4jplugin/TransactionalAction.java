package neo4jplugin;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import static play.libs.F.Promise;


/**
 * Wraps an action in a Neo4J transaction.
 * Tooked the code from the jpa plaugin
 */
public class TransactionalAction extends Action<Transactional> {

    public F.Promise<Result> call(final Http.Context ctx) throws Throwable {
        return Neo4JPlugin.withTransaction(new F.Function0<Promise<Result>>() {
            public F.Promise<Result> apply() throws Throwable {
                return delegate.call(ctx);
            }
        });
    }


}
