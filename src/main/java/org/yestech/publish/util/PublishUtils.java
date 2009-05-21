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
package org.yestech.publish.util;

import org.yestech.publish.objectmodel.*;
import org.yestech.lib.crypto.CryptoUtils;
import org.yestech.lib.crypto.MessageDigestUtils;
import static org.yestech.lib.crypto.MessageDigestUtils.sha1Hash;

import java.util.UUID;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublishUtils {

    /**
     * Generates a Unique Identifer using the form:
     * <br/>
     * sha1({@link org.yestech.publish.objectmodel.IArtifactOwner#getIdentifier()#toString()})
     *
     * @param owner The Owner
     * @return the identifier
     */
    public static String generateUniqueIdentifier(IArtifactOwner owner) {
        return sha1Hash(owner.getIdentifier().toString());
    }

    /**
     * Generates a Unique Identifer using the form:
     * <br/>
     * sha1({@link UUID#randomUUID}) + "_" + {@link org.yestech.publish.objectmodel.IFileArtifactMetaData#getFileName()}
     *
     * @param metaData The metadata
     * @return the identifier
     */
    public static String generateUniqueIdentifier(IFileArtifactMetaData metaData) {
        return sha1Hash(UUID.randomUUID().toString()) + "_" + metaData.getFileName();
    }

    /**
     * Determines if an Artifact is an {@link org.yestech.publish.objectmodel.IFileArtifact}
     *
     * @param artifact Artifact to check
     * @return true if of type IFileArtifact and non null
     */
    public static boolean isFileArtifact(IArtifact artifact) {
        return (artifact instanceof IFileArtifact);
    }

    /**
     * Resets a {@link org.yestech.publish.objectmodel.IFileArtifact} by setting both File and Stream to null
     *
     * @param artifact Artifact to reset
     */
    public static void reset(IFileArtifact artifact) {
        if (artifact != null) {
            artifact.setFile(null);
            artifact.setStream(null);
        }
    }
}
