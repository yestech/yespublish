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

import org.yestech.publish.util.PublishUtils;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class TerracottaPipeArtifact implements IArtifact {
    private IArtifact artifact;
    private Map<String, Object> parameters = newHashMap();

    @Override
    public Object getArtifactIdentifier() {
        return null;
    }

    @Override
    public void setArtifactIdentifier(Object identifier) {
    }

    @Override
    public IArtifactMetaData getArtifactMetaData() {
        return null;
    }

    @Override
    public void setArtifactMetaData(IArtifactMetaData artifactMetaData) {
    }

    public IArtifact getArtifact() {
        return artifact;
    }

    public void setArtifact(IArtifact artifact) {
        this.artifact = artifact;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public <T> T getParameter(String key) {
        return (T) parameters.get(key);
    }

    public static TerracottaPipeArtifact create(IArtifact artifact) {
        TerracottaPipeArtifact terracottaArtifact = new TerracottaPipeArtifact();
        terracottaArtifact.setArtifact(artifact);
        if (PublishUtils.isFileArtifact(artifact)) {
            IFileArtifact fileArtifact = (IFileArtifact) artifact;
            PublishUtils.reset(fileArtifact);
        }
        return terracottaArtifact;
    }
}
