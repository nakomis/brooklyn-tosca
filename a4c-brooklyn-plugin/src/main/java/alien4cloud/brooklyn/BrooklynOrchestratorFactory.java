package alien4cloud.brooklyn;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import alien4cloud.brooklyn.metadata.BrooklynToscaTypeProvider;
import alien4cloud.brooklyn.metadata.DefaultToscaTypeProvider;
import lombok.extern.slf4j.Slf4j;

import org.apache.brooklyn.util.collections.MutableList;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import alien4cloud.model.components.PropertyDefinition;
import alien4cloud.model.orchestrators.ArtifactSupport;
import alien4cloud.model.orchestrators.locations.LocationSupport;
import alien4cloud.orchestrators.plugin.IOrchestratorPluginFactory;

@Slf4j
@Component("brooklyn-orchestrator-factory")
public class BrooklynOrchestratorFactory implements IOrchestratorPluginFactory<BrooklynOrchestrator, Configuration> {

    public static final String BROOKLYN = "Brooklyn";

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public BrooklynOrchestrator newInstance() {
        BrooklynOrchestrator instance = beanFactory.getBean(BrooklynOrchestrator.class);
        log.info("Init brooklyn provider and beanFactory is {}", beanFactory);
        return instance;
    }

    @Override
    public void destroy(BrooklynOrchestrator instance) {
        log.info("DESTROYING (noop)", instance);
    }

    @Override
    public Class<Configuration> getConfigurationType() {
        return Configuration.class;
    }

    @Override
    public Configuration getDefaultConfiguration() {
        // List ordered lowest to highest priority.
        List<String> metadataProviders = MutableList.of(
                DefaultToscaTypeProvider.class.getName(),
                BrooklynToscaTypeProvider.class.getName());
        return new Configuration("http://localhost:8081/",
                "brooklyn",
                "brooklyn",
                "localhost",
                metadataProviders);
    }

    @Override
    public LocationSupport getLocationSupport() {
        return new LocationSupport(true, new String[] {BROOKLYN});
    }

    @Override
    public ArtifactSupport getArtifactSupport() {
        return new ArtifactSupport();
    }

    @Override
    public Map<String, PropertyDefinition> getDeploymentPropertyDefinitions() {
        return null;
    }

}
