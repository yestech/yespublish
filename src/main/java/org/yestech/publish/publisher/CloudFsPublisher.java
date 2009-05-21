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
@ProducerArtifactType(type = {ArtifactType.IMAGE, ArtifactType.VIDEO, ArtifactType.TEXT,ArtifactType.AUDIO})
public class CloudFsPublisher extends BasePublisher implements IPublisher<IFileArtifact> {

    @Override
    public void publish(IFileArtifact iFileArtifact) {
        throw new UnsupportedOperationException();
    }
}
