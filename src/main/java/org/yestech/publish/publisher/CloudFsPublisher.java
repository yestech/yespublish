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

import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.ProducerArtifactType;
import org.yestech.publish.objectmodel.ArtifactType;

import java.io.InputStream;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@ProducerArtifactType(type = {ArtifactType.IMAGE, ArtifactType.VIDEO, ArtifactType.TEXT,ArtifactType.AUDIO})
public class CloudFsPublisher implements IPublisher {

    @Override
    public void publish(IArtifactMetaData metaData, InputStream artifact) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
