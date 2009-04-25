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
package org.yestech.publish.publisher;

import org.yestech.publish.objectmodel.*;

import java.io.InputStream;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@ProducerArtifactType(type = {ArtifactType.IMAGE, ArtifactType.VIDEO, ArtifactType.TEXT, ArtifactType.AUDIO})
public class AmazonS3Publisher extends BasePublisher implements IPublisher<IFileArtifactMetaData, InputStream> {

    @Override
    public void publish(IFileArtifactMetaData metaData, InputStream artifact) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}