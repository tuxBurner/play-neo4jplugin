package neo4j.models;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class World extends AbstractNode {

	@Indexed
	public String name;

	@Indexed(indexName = "moon-index")
	public int moons;

	@Fetch
	@RelatedTo(type = "REACHABLE_BY_ROCKET", direction = Direction.OUTGOING)
	public Set<World> reachableByRocket;

	public World(String name, int moons) {
		this.name = name;
		this.moons = moons;
	}

	public World() {
	}

	public void addRocketRouteTo(World otherWorld) {
		reachableByRocket.add(otherWorld);
	}

	@Override
	public String toString() {
		return String.format("World{name='%s', moons=%d}", name, moons);
	}

	public enum RelTypes implements RelationshipType {
		REACHABLE_BY_ROCKET
	}
}