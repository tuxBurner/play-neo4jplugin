package neo4j.repositories;

import neo4j.models.World;
import org.springframework.data.neo4j.repository.GraphRepository;



public interface WorldRepository extends GraphRepository<World> {
}