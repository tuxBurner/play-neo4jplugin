package neo4j.models;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * @author Sebastian Hardt (s.hardt@micromata.de)
 *         Date: 27.08.15
 *         Time: 21:39
 */
@NodeEntity
public class World
{


  @GraphId
  public Long id;

  @Indexed
  public String name;

  @Indexed
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

  public enum RelTypes implements RelationshipType
  {
    REACHABLE_BY_ROCKET
  }

}
