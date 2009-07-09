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

import org.yestech.lib.util.Pair;

import java.io.File;
import java.io.InputStream;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class DefaultFileArtifact<FMD extends IFileArtifactMetaData, ID> implements IFileArtifact<FMD, ID> {
    private FMD artifactMetaData;
    private ID artifactIdentifier;
    private File file;
    private InputStream stream;
    private Pair<String, String> uniqueNames;

    public FMD getArtifactMetaData() {
        return artifactMetaData;
    }

    public void setArtifactMetaData(FMD artifactMetaData) {
        this.artifactMetaData = artifactMetaData;
    }

    public ID getArtifactIdentifier() {
        return artifactIdentifier;
    }

    public void setArtifactIdentifier(ID identifier) {
        this.artifactIdentifier = identifier;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public Pair<String, String> getUniqueNames() {
        return uniqueNames;
    }

    public void setUniqueNames(Pair<String, String> uniqueNames) {
        this.uniqueNames = uniqueNames;
    }
}
