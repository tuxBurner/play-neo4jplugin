package org.springframework.data.repository.core.support;

import org.springframework.data.neo4j.repository.GraphRepositoryFactory;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.mapping.Neo4jMappingContext;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: tuxburner
 * Date: 1/3/14
 * Time: 6:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayDevGraphRepositoryFactory extends GraphRepositoryFactory {

    /**
     * Creates a new {@link org.springframework.data.neo4j.repository.GraphRepositoryFactory} from the given {@link org.springframework.data.neo4j.support.Neo4jTemplate} and
     * {@link org.springframework.data.mapping.context.MappingContext}.
     *
     * @param template       must not be {@literal null}.
     * @param mappingContext must not be {@literal null}.
     */
    public PlayDevGraphRepositoryFactory(Neo4jTemplate template, Neo4jMappingContext mappingContext) {
        super(template, mappingContext);
    }

    /**
     * This is needed for bug https://github.com/tuxBurner/play-neo4jplugin/issues/7 to go arround the caching which causes class loader issuesd
     *
     * @param metadata
     * @param customImplementationClass
     * @return
     */
    protected RepositoryInformation getRepositoryInformation(RepositoryMetadata metadata,
                                                             Class<?> customImplementationClass) {


        return new PlayDefaultRepoInfo(metadata, getRepositoryBaseClass(metadata),
                customImplementationClass);
    }


}
