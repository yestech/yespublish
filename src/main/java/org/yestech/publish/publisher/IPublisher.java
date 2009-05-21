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
import org.yestech.publish.objectmodel.IArtifact;

import java.io.InputStream;

/**
 * The classes that actually move an {@link org.yestech.publish.objectmodel.IArtifact} to it destination.
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IPublisher<ARTIFACT extends IArtifact> {
    void publish(ARTIFACT artifact);
}
