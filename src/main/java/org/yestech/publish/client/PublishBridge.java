/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.publish.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yestech.publish.objectmodel.ArtifactType;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.IFileArtifact;
import org.yestech.publish.service.IPublishProducer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublishBridge implements IPublishBridge {
    final private static Logger logger = LoggerFactory.getLogger(PublishBridge.class);

    private Map<ArtifactType, IPublishProducer> producers = new HashMap<ArtifactType, IPublishProducer>();

    @Override
    public Map<ArtifactType, IPublishProducer> getProducers() {
        return producers;
    }

    @Override
    public void setProducers(Map<ArtifactType, IPublishProducer> producers) {
        this.producers = producers;
    }

    private IPublishProducer getProducer(IArtifactMetaData metaData) {
        return producers.get(metaData.getArtifactType());
    }

    @Override
    public void publish(IArtifact artifact) {
        IPublishProducer producer = getProducer(artifact.getArtifactMetaData());
        producer.send(artifact);
    }

    @Override
    public void publish(IFileArtifact artifact) {
        IPublishProducer producer = getProducer(artifact.getArtifactMetaData());
        producer.send(artifact);
    }

}
