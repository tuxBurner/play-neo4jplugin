package org.springframework.data.repository.core.support;

import org.neo4j.graphdb.PropertyContainer;
import org.springframework.data.neo4j.repository.CRUDRepository;
import org.springframework.data.neo4j.repository.GraphRepositoryFactory;
import org.springframework.data.neo4j.repository.GraphRepositoryFactoryBean;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.mapping.Neo4jMappingContext;
import org.springframework.data.repository.core.support.PlayDevGraphRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.TransactionalRepositoryFactoryBeanSupport;
import org.springframework.util.Assert;
import play.Play;

/**
 * Copy paste from GraphRepositoryFactoryBean do get shit running for issue #7
 *
 * @param <S>
 * @param <R>
 * @param <T>
 */
public class PlayGraphRepositoryFactoryBean<S extends PropertyContainer, R extends CRUDRepository<T>, T> extends
        TransactionalRepositoryFactoryBeanSupport<R, T, Long> {

    private Neo4jTemplate template;
    private Neo4jMappingContext neo4jMappingContext;

    public void setNeo4jTemplate(Neo4jTemplate template) {
        this.template = template;
    }

    /**
     * @param neo4jMappingContext the mappingContext to set
     */
    public void setNeo4jMappingContext(
            Neo4jMappingContext neo4jMappingContext) {
        this.neo4jMappingContext = neo4jMappingContext;
    }

    @Override
    protected RepositoryFactorySupport doCreateRepositoryFactory() {
        return createRepositoryFactory(template);
    }

    protected RepositoryFactorySupport createRepositoryFactory(Neo4jTemplate template) {

        if (Play.isDev() == false) {
            return new GraphRepositoryFactory(template, neo4jMappingContext);
        } else {
            return new PlayDevGraphRepositoryFactory(template, neo4jMappingContext);
        }
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(template, "Neo4jTemplate must not be null!");

        if (neo4jMappingContext == null) {
            Neo4jMappingContext context = new Neo4jMappingContext();
            context.initialize();
            this.neo4jMappingContext = context;
        }
        setMappingContext(neo4jMappingContext);

        super.afterPropertiesSet();
    }
}
