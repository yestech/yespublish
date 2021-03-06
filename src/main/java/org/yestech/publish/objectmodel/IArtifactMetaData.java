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

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IArtifactMetaData<O extends IArtifactOwner, I> extends Serializable {
    I getArtifactMetaDataIdentifier();

    void setArtifactMetaDataIdentifier(I metaDataIdentifier);

    ArtifactType getArtifactType();

    void setArtifactType(ArtifactType type);

    void setArtifactOwner(O owner);

    O getArtifactOwner();

    public DateTime getCreated();

    public void setCreated(DateTime created);

    public DateTime getModified();

    public void setModified(DateTime modified);
}

