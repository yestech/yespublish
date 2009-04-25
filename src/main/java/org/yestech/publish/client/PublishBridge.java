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
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.ArtifactType;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.service.IPublishProducer;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublishBridge implements IPublishBridge {
    final private static Logger logger = LoggerFactory.getLogger(PublishBridge.class);

    private Map<ArtifactType, IPublishProducer> producers = new HashMap<ArtifactType, IPublishProducer>();

    public Map<ArtifactType, IPublishProducer> getProducers() {
        return producers;
    }

    public void setProducers(Map<ArtifactType, IPublishProducer> producers) {
        this.producers = producers;
    }

    public void publish(IArtifactMetaData metaData, InputStream artifact) {
        IPublishProducer producer = getProducer(metaData);
        producer.send(metaData, artifact);
    }

    private IPublishProducer getProducer(IArtifactMetaData metaData) {
        return producers.get(metaData.getArtifactType());
    }

    public void publish(IArtifactMetaData metaData, URL artifact) {
        IPublishProducer producer = getProducer(metaData);
        producer.send(metaData, artifact);
    }

    public void publish(IArtifactMetaData metaData, File artifact) {
        IPublishProducer producer = getProducer(metaData);
        producer.send(metaData, artifact);
    }

    @Override
    public void publish(IArtifact artifact) {
        IPublishProducer producer = getProducer(artifact.getArtifactMetaData());
        producer.send(artifact);
    }
}
