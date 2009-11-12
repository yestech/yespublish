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

import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;

/**
 * Doesnt do anything it is a No Operation
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class NoOpPublishProducer implements IPublishProducer {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void send(IArtifact artifact) {
    }

    @Override
    public void send(IFileArtifact artifact) {
    }


}
