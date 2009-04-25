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
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IPublisher<TYPE extends IArtifactMetaData, ARTIFACT> {
    void publish(TYPE metaData, ARTIFACT artifact);
}
