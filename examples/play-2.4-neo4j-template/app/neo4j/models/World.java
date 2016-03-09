package neo4j.models;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
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

//  @Indexed FIXME JU
  public String name;

//  @Indexed FIXME JU
  public int moons;

//  @Fetch FIXME JU
  @Relationship(type = "REACHABLE_BY_ROCKET", direction = Relationship.OUTGOING)
  public Set<World> reachableByRocket = new HashSet<>();

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

  public enum RelTypes // implements RelationshipType FIXME JU
  {
    REACHABLE_BY_ROCKET
  }

}
