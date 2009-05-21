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
package org.yestech.publish.service;

import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.IArtifact;

import java.io.InputStream;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class NoOpPublishConsumer implements IPublishConsumer {
    @Override
    public void recieve(IArtifact artifact) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
