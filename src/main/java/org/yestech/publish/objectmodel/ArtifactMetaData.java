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

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class ArtifactMetaData<O extends IArtifactOwner, I> implements IArtifactMetaData<O, I> {
    private String mimeType;
    private String fileName;
    private long size;
    private ArtifactType type;
    private String location;
    private O owner;
    private I metaDataIdentifier;
    private DateTime created;
    private DateTime modified;

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getModified() {
        return modified;
    }

    public void setModified(DateTime modified) {
        this.modified = modified;
    }

    public I getArtifactMetaDataIdentifier() {
        return metaDataIdentifier;
    }

    public void setArtifactMetaDataIdentifier(I metaDataIdentifier) {
        this.metaDataIdentifier = metaDataIdentifier;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public ArtifactType getArtifactType() {
        return type;
    }

    public void setArtifactType(ArtifactType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public O getArtifactOwner() {
        return owner;
    }

    public void setArtifactOwner(O owner) {
        this.owner = owner;
    }
}
