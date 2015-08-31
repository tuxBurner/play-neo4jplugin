package neo4j.repositories;

import neo4j.models.World;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Sebastian Hardt (sebasth@gmx.de)
 *         Date: 27.08.15
 *         Time: 21:44
 */
public interface WorldRepository extends GraphRepository<World>
{
}
