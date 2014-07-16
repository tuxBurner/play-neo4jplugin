package neo4j.services;


import neo4j.models.World;
import neo4j.repositories.WorldRepository;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Service
public class GalaxyService {

	@Autowired
	private WorldRepository worldRepository;

	public long getNumberOfWorlds() {
		return worldRepository.count();
	}
	public List<World> getAllWorlds() {
		return new ArrayList<World>(IteratorUtil.asCollection(worldRepository.findAll()));
	}

	public List<World> makeSomeWorldsAndRelations() {
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

		return worlds;
	}

	public List<World> getWorldPath(final World worldA, final World worldB) {
		Path path = GraphAlgoFactory.shortestPath(Traversal.expanderForTypes(World.RelTypes.REACHABLE_BY_ROCKET, Direction.OUTGOING).add(World.RelTypes.REACHABLE_BY_ROCKET), 100)
				.findSinglePath(Neo4JServiceProvider.get().template.getNode(worldA.id), Neo4JServiceProvider.get().template.getNode(worldB.id));
		if (path == null) {
			return Collections.emptyList();
		}
		return convertNodesToWorlds(path);
	}

	private List<World> convertNodesToWorlds(final Path list) {
		final List<World> result = new LinkedList<World>();
		for (Node node : list.nodes()) {
			result.add(Neo4JServiceProvider.get().template.load(node, World.class));
		}
		return result;
	}
	
	private World createWorld(String name, int moons) {
		return worldRepository.save(new World(name, moons));
	}

}