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
import org.yestech.publish.service.IPublishProducer;

import java.io.*;
import java.net.URL;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublishBridge implements IPublishBridge {
    final private static Logger logger = LoggerFactory.getLogger(PublishBridge.class);
    public IPublishProducer producer;

    public IPublishProducer getProducer() {
        return producer;
    }

    public void setProducer(IPublishProducer producer) {
        this.producer = producer;
    }

    public void publish(IArtifactMetaData metaData, InputStream artifact) {
        producer.send(metaData, artifact);
    }

    public void publish(IArtifactMetaData metaData, URL artifact) {
        try {
            publish(metaData, artifact.openStream());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void publish(IArtifactMetaData metaData, File artifact) {
        try {
            publish(metaData, new FileInputStream(artifact));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
