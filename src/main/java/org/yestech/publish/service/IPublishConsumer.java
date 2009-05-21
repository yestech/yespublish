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
 * Consumes a publish request from a {@link org.yestech.publish.service.IPublishProducer} and processes the request.
 *  
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IPublishConsumer {

    void recieve(IArtifact artifact);

}