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
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Doesnt do anything but close the {@link java.io.InputStream}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class NoOpPublishProducer implements IPublishProducer {
    @Override
    public void send(IArtifact artifact) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(IFileArtifact artifact) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
