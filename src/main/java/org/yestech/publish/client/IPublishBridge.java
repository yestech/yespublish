/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.publish.client;

import org.yestech.publish.service.IPublishProducer;
import org.yestech.publish.objectmodel.IArtifactMetaData;

import java.io.InputStream;
import java.io.File;
import java.net.URL;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IPublishBridge {
    IPublishProducer getProducer();

    void setProducer(IPublishProducer producer);

    void publish(IArtifactMetaData metaData, InputStream artifact);

    void publish(IArtifactMetaData metaData, URL artifact);

    void publish(IArtifactMetaData metaData, File artifact);
}
