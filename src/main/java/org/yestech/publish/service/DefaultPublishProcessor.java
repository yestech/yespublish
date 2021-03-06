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
 * Delegates to the correct {@link org.yestech.publish.publisher.IPublisher} for the {@link org.yestech.publish.objectmodel.ArtifactType}
 * wanting to be published.
 * 
 * @author $Author: $
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class DefaultPublishProcessor implements IPublishProcessor {
    private Map<ArtifactType, IPublisher> publishers = newHashMap();


    public void setProcessorList(List<IPublisher> publisherList) {
        for (IPublisher publisher : publisherList) {
            final Class<? extends IPublisher> publisherClass = publisher.getClass();
            if (publisherClass != null) {
                ProducerArtifactType producerArtifactType = publisherClass.getAnnotation(ProducerArtifactType.class);
                if (producerArtifactType != null) {
                    for (ArtifactType artifactType : producerArtifactType.type()) {
                        publishers.put(artifactType, publisher);
                    }
                }
            }
        }
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
        publisher.publish(artifact);
    }
}