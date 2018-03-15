package neo4jplugin;

/**
 * @author Sebastian Hardt (s.hardt@micromata.de)
 * Date: 30.08.15
 * Time: 13:21
 */

import play.mvc.With;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wraps the annotated action in an JPA transaction.
 */
@With(TransactionalAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Neo4jTransactional
{
}
