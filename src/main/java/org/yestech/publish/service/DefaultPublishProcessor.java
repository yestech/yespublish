/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Original Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.publish.service;

import static com.google.common.collect.Maps.newHashMap;
import org.yestech.publish.objectmodel.ArtifactType;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.ProducerArtifactType;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.publisher.IPublisher;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author $Author: $
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class DefaultPublishProcessor implements IPublishProcessor {
    private Map<ArtifactType, IPublisher> publishers = newHashMap();


    public void setProcessorList(List<IPublisher> publisherList) {
        for (IPublisher processor : publisherList) {
            ProducerArtifactType producerArtifactType = processor.getClass().getAnnotation(ProducerArtifactType.class);
            for (ArtifactType artifactType : producerArtifactType.type()) {
                publishers.put(artifactType, processor);
            }
        }
    }

    @Override
    public void process(IArtifactMetaData metaData, InputStream artifact) {
        IPublisher publisher = publishers.get(metaData.getArtifactType());
        publisher.publish(metaData, artifact);
    }

    public Map<ArtifactType, IPublisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Map<ArtifactType, IPublisher> publishers) {
        this.publishers = publishers;
    }

    @Override
    public void process(IArtifact artifact) {
        IPublisher publisher = publishers.get(artifact.getArtifactMetaData().getArtifactType());
        publisher.publish(artifact.getArtifactMetaData(), artifact);
    }
}