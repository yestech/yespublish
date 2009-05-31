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
package org.yestech.publish.publisher;

import static org.apache.commons.io.FileUtils.openOutputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yestech.publish.objectmodel.*;
import org.yestech.publish.util.PublishUtils;
import static org.yestech.publish.util.PublishUtils.generateUniqueIdentifier;
import org.springframework.beans.factory.annotation.Required;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Writes to the Local FileSystem.  It doesnt remove the original file.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@ProducerArtifactType(type = {ArtifactType.IMAGE, ArtifactType.VIDEO, ArtifactType.TEXT, ArtifactType.AUDIO})
public class LocalFileSystemPublisher extends BasePublisher implements IPublisher<IFileArtifact> {
    final private static Logger logger = LoggerFactory.getLogger(LocalFileSystemPublisher.class);

    private File directory;

    public File getDirectory() {
        return directory;
    }

    @Required
    public void setDirectory(File directory) {
        this.directory = directory;
    }

    @Override
    public void publish(IFileArtifact artifact) {
        IFileArtifactMetaData metaData = artifact.getArtifactMetaData();
        InputStream artifactStream = artifact.getStream();
        File fullPath = new File(directory + File.separator + generateUniqueIdentifier(metaData.getArtifactOwner()));
        if (!fullPath.exists()) {
            fullPath.mkdirs();
        }
        String location = fullPath.getAbsolutePath() + File.separator + generateUniqueIdentifier(metaData);
        FileOutputStream outputStream = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Saving file: " + location);
            }
            outputStream = openOutputStream(new File(location));
            IOUtils.copyLarge(artifactStream, outputStream);
            outputStream.flush();
            if (logger.isDebugEnabled()) {
                logger.debug("Saved file: " + location);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            IOUtils.closeQuietly(artifactStream);
            IOUtils.closeQuietly(outputStream);
            PublishUtils.reset(artifact);
        }

        metaData.setLocation(location);
    }
}
