package neo4j.services;

import neo4j.models.World;
import neo4j.repositories.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sebastian Hardt (s.hardt@micromata.de)
 *         Date: 27.08.15
 *         Time: 21:34
 */
@Service
public class GalaxyService
{
  @Autowired
  private WorldRepository worldRepository;

  public long getNumberOfWorlds() {
    return worldRepository.count();
  }

  public List<World> getAllWorlds()
  {
    ArrayList<World> worlds = new ArrayList<>();
    for (World world : worldRepository.findAll()) {
      worlds.add(world);
    }
    return worlds;
  }

  public List<World> makeSomeWorldsAndRelations() {

    Logger.debug("Creating test data set in database.");

    List<World> worlds = new ArrayList<World>();

    // Solar worlds
    worlds.add(createWorld("Mercury", 0));
    worlds.add(createWorld("Venus", 0));
    worlds.add(createWorld("Earth", 1));
    worlds.add(createWorld("Mars", 2));
    worlds.add(createWorld("Jupiter", 63));
    worlds.add(createWorld("Saturn", 62));
    worlds.add(createWorld("Uranus", 27));
    worlds.add(createWorld("Neptune", 13));

    // Norse worlds
    worlds.add(createWorld("Alfheimr", 0));
    worlds.add(createWorld("Midgard", 1));
    worlds.add(createWorld("Muspellheim", 2));
    worlds.add(createWorld("Asgard", 63));
    worlds.add(createWorld("Hel", 62));


    // Add relations
    for (int i = 0; i < worlds.size() - 1; i++) {
      World world = worlds.get(i);
      world.addRocketRouteTo(worlds.get(i + 1));
      worldRepository.save(world);
    }

    Logger.debug("Creating test data done, have fun with it :).");

    return worlds;
  }

  // FIXME JU
  public List<World> getWorldPath(final World worldA, final World worldB) {
//    Path path = GraphAlgoFactory.shortestPath(Traversal.expanderForTypes(World.RelTypes.REACHABLE_BY_ROCKET, RelationShip.OUTGOING).add(World.RelTypes.REACHABLE_BY_ROCKET), 100)
//        .findSinglePath(Neo4jServiceProviderImpl.get().template.getNode(worldA.id), Neo4jServiceProviderImpl.get().template.getNode(worldB.id));
//    if (path == null) {
//      return Collections.emptyList();
//    }
//    return convertNodesToWorlds(path);
    return new ArrayList<>();
  }

//
//  private List<World> convertNodesToWorlds(final Path list) {
//    final List<World> result = new LinkedList<World>();
//    for (Node node : list.nodes()) {
//      result.add(Neo4jServiceProviderImpl.get().template.load(node, World.class));
//    }
//    return result;
//  }

  private World createWorld(String name, int moons) {
    return worldRepository.save(new World(name, moons));
  }
}
