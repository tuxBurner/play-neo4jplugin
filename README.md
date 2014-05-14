# Play Neo4JPlugin

This is a simple Play 2.2.1 plugin, for NEO4J.

I got the ideas how to do it from https://github.com/tomasmuller/playframework-neo4j-template THANKS A LOT @tomasmuller for giving me the ground ideas :)

What i didn't liked that i can't call neo4j in the static way, like Ebean etc...

So here we go, i created this plugin :)

## Versions
1.3.5 Fixed Scala action transaction handling by implememnting an ActionBuilder. Example is in examples/playframework-neo4j-template/app/controllers/Application.scala.

1.3.4 Lifted to new Versions: spring-data-neo4j: 3.0.2.RELEASE, play 2.2.3, neo4j 2.0.3

1.3.3 Lifted to new Versions: spring-data-neo4j: 3.0.1.RELEASE, play 2.2.2 **Cause of https://github.com/spring-projects/spring-data-neo4j/issues/161 there is a new config option: neo4j.basepackage="neo4j" # the base package where the entities are located : when not set the plugin defaults to neo4j**

1.3.2 Lifted to new Versions: neo4j: 2.0.1 spring: 4.0.1 spring-data-neo4j: 3.0.0.RC1

1.3.1  Version which fixes https://github.com/tuxBurner/play-neo4jplugin/issues/7 **ATTENTION DONT  USE VERSION 1.3.0 IT IS BROKEN DUE THIS ISSUE**

1.3.0  Lifted to neo4j-2.0.0-M06, spring-data-neo4j-3.0.0.M1 and spring-context-3.2.5.RELEASE **ATTENTION SEE  resolver for version 1.3.0 cause of the milestone you need an extra repo** 

1.2.1  Lifted to neo4j 1.9.5

1.2    Lifted to play 2.2.1 and spring-data-neo4j 2.3.2.RELEASE 

1.1.1: Lifted to neo4j-1.9.3 and spring-data-neo4j 2.3.1-RELEASE

1.1.0: Added controller for transactional scala

1.0.7: RestConfiguration and added AuditingEventListener

1.0.2: New Spring-Data-2.2.2 Version Dep. 



## Installation (using sbt)

You will need to add the following resolver in your `project/Build.scala` file:

```scala
resolvers += "tuxburner.github.io" at "http://tuxburner.github.io/repo",
resolvers += "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/"
```

**!!! For Version 1.3.0 you need also to add !!!**
```scala
resolvers += "Spring milestones" at "http://repo.spring.io/milestone"
```

Add a dependency on the following artifact:

```scala
libraryDependencies += "com.github.tuxBurner" %% "play-neo4jplugin" % "1.3.4"
```

Activate the plugin in the `conf/play.plugins` like this:

```
900:neo4jplugin.Neo4JPlugin
```

Settings for the plugin go into the `conf/application.conf`:

```
neo4j.serviceProviderClass="neo4j.services.Neo4JServiceProvider" # the provider class which holds the annotated neo4j beans
neo4j.basepackage="neo4j" # the base package where the entities are located

neo4j.mode="embedded" # mode to run embedded or remote

# embedded db config
neo4j.embeddedDB="target/neo4j-db" # where to put the embedded database

# remote db config
neo4j.restDB.host="http://localhost:7474/db/data"
neo4j.restDB.user=""
neo4j.restDB.password=""

```

## Usage

All neo4j relevant stuff must go to `app/neo4j/`

All neo4j repositories go to `app/neo4j/repositories/`


You need a class which must extend from `neo4jplugin.ServiceProvider` and must be configured in the `application.conf` under the key `neo4j.serviceProviderClass`:

Example: 
```java
    @Component
    public class Neo4JServiceProvider extends ServiceProvider {

      @Autowired
      public NeoUserRepository neoUserRepository;

      public static Neo4JServiceProvider get() {
        return Neo4JPlugin.get();
      }
}

```

To access your repository you can call: `Neo4JServiceProvider.get().neoUserRepository.<do magic neo4j stuff>`

To access the neo4jtemplate you can call: `Neo4JServiceProvider.get().template.<do magic neo4j stuff>`

## Transactions

There is also a `@Transactional` annotation which I addopted from the play jpa plugin.

Just annotate your Result with it and it runs in a neo4j Transaction.

Example Java: 
```java
  @Transactional
  public static Result doSomethingInTransaction(Long id) {
    Neo4JServiceProvider.get().neoUserRepository.<do magic neo4j stuff>
    Neo4JServiceProvider.get().neoUserRepository.<do magic neo4j stuff>

    return ok("Woohhoh Neo4jTransaction");
  }  
```

For Scala i used the Actionbuilder pattern described at: http://www.playframework.com/documentation/2.2.x/ScalaActionsComposition
This allows you to combine several actions. 

Example Scala:
```scala
  def index = Neo4jTransactionAction {
    Neo4JServiceProvider.get().neoUserRepository.<do magic neo4j stuff>
    Neo4JServiceProvider.get().neoUserRepository.<do magic neo4j stuff>

    Ok("Woohhoh Neo4jTransaction")
  }
```

Take a look into the examples


## TODO

Make the spring configuration configable with the play configuration so the neo4j stuff has not to be in the folder app/neoj do the same with the repositories.