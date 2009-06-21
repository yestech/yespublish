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
import static org.yestech.lib.crypto.MessageDigestUtils.sha1Hash;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.io.*;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublishUtils {
    final private static Logger logger = LoggerFactory.getLogger(PublishUtils.class);

    /**
     * Generates a Unique Identifer using the form:
     * <br/>
     * sha1({@link org.yestech.publish.objectmodel.IArtifactOwner#getOwnerIdentifier()#toString()})
     *                      
     * @param owner The Owner
     * @return the identifier
     */
    public static String generateUniqueIdentifier(IArtifactOwner owner) {
        return sha1Hash(owner.getOwnerIdentifier().toString());
    }

    /**
     * Returns the physical {@link IArtifact} type.
     *
     * @param artifact
     * @return
     */
    public static IArtifact translateArtifact(IArtifact artifact) {
        IArtifact artifactType = artifact;
        if (artifact instanceof TerracottaPipeArtifact) {
            artifactType = ((TerracottaPipeArtifact) artifact).getArtifact();
        }
        return artifactType;
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
     * Determines if an Artifact is an {@link org.yestech.publish.objectmodel.TerracottaPipeArtifact}
     *
     * @param artifact Artifact to check
     * @return true if of type TerracottaPipeArtifact and non null
     */
    public static boolean isTerracottaArtifact(IArtifact artifact) {
        return (artifact instanceof TerracottaPipeArtifact);
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

    public static void removeTempFile(String fqn) {
        if (logger.isInfoEnabled()) {
            logger.info("removing file: " + fqn);
        }
        File tmpFile = new File(fqn);
        if (tmpFile.exists()) {
            tmpFile.delete();
        }
    }

    public static String saveTempFile(File tempDirectory, InputStream inputStream, IFileArtifact artifact) {
        String fqn = "";
        FileOutputStream fileOutputStream = null;
        try {
            String ownerId = generateUniqueIdentifier(artifact.getArtifactMetaData().getArtifactOwner());
            String directory = tempDirectory + File.separator + ownerId + File.separator;

            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            fqn = directory + artifact.getArtifactMetaData().getFileName();
            final File newFile = new File(fqn);

            fileOutputStream = FileUtils.openOutputStream(newFile);
            IOUtils.copyLarge(inputStream, fileOutputStream);
            fileOutputStream.flush();
            artifact.getArtifactMetaData().setSize(newFile.length());
            artifact.setStream(new FileInputStream(newFile));
        } catch (IOException e) {
            logger.error("error saving streaming data: " + e);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
        return fqn;
    }

}
