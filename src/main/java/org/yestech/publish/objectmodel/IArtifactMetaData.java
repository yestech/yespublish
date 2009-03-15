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
public interface IArtifactMetaData<O, I> extends Serializable {
    I getIdentifier();

    void setIdentifier(I identifier);

    String getMimeType();

    void setMimeType(String mimeType);

    String getFileName();

    void setFileName(String fileName);

    long getSize();

    void setSize(long size);

    ArtifactType getType();

    void setType(ArtifactType type);

    void setLocation(String location);

    String getLocation();

    void setOwner(O owner);

    O getOwner();

    public DateTime getCreated();

    public void setCreated(DateTime created);

    public DateTime getModified();

    public void setModified(DateTime modified);
}

