package neo4jplugin;


import play.libs.F;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;
import static play.libs.F.Promise;


/**
 * Wraps an action in a Neo4J transaction.
 * Tooked the code from the jpa plaugin
 */
public class TransactionalAction extends Action<Transactional> {

    public Promise<SimpleResult> call(final Context ctx) throws Throwable {
        return Neo4JPlugin.withTransaction(new F.Function0<Promise<SimpleResult>>() {
            public F.Promise<SimpleResult> apply() throws Throwable {
                return delegate.call(ctx);
            }
        });
    }


}
