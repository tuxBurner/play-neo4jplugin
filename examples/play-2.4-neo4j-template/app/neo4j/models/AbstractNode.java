package neo4j.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Sebastian Hardt (s.hardt@micromata.de)
 *         Date: 27.08.15
 *         Time: 21:38
 */

@NodeEntity
public class AbstractNode {

  @GraphId
  public Long id;

  @CreatedDate
  public Long createdDate;

  @LastModifiedDate
  public Long lastModifiedDate;

}
