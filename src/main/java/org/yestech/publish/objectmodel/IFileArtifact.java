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
package org.yestech.publish.objectmodel;

import java.io.File;
import java.io.InputStream;

/**
 * Represents a generic type that can be published.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IFileArtifact<MD extends IFileArtifactMetaData, ID> extends IArtifact<MD, ID> {
    ID getArtifactIdentifier();

    void setArtifactIdentifier(ID identifier);

    public MD getArtifactMetaData();

    public void setArtifactMetaData(MD artifactMetaData);

    public File getFile();

    public void setFile(File file);

    public InputStream getStream();

    public void setStream(InputStream stream);

}