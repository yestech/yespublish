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
import org.yestech.publish.objectmodel.IFileArtifact;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Sends out a notification that an {@link org.yestech.publish.objectmodel.IArtifactMetaData} needs to be published
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IPublishProducer {
    
    void send(IArtifact artifact);

    void send(IFileArtifact artifact);

    public String getUrl();

    public void setUrl(String url);

}
