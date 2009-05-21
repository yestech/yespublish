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

import org.yestech.publish.service.IPublishProducer;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.ArtifactType;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;

import java.io.InputStream;
import java.io.File;
import java.net.URL;
import java.util.Map;

/**
 * Bridge the provides access to the Publishing System.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IPublishBridge {
//    void publish(IArtifactMetaData metaData, InputStream artifact);
//
//    void publish(IArtifactMetaData metaData, URL artifact);
//
//    void publish(IArtifactMetaData metaData, File artifact);

    /**
     * Publish a generic artifact.
     *
     * @param artifact Artifact to publish
     */
    void publish(IArtifact artifact);

    Map<ArtifactType, IPublishProducer> getProducers();

    void setProducers(Map<ArtifactType, IPublishProducer> producers);

    /**
     * Publish a File Based Artifact.
     *
     * @param artifact Artifact to publish
     */
    void publish(IFileArtifact artifact);

//    void publish(IArtifact artifact, URL artifactUrl);
//
//    void publish(IArtifact artifact, File artifactFile);
}
