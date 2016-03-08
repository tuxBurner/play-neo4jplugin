package neo4j.models;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
