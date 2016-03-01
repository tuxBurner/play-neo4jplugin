package neo4jplugin;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

public class Neo4jModule extends Module {

  @Override
  public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
    return seq(
        bind(Neo4jPlugin.class).toSelf().eagerly()
    );
  }

}