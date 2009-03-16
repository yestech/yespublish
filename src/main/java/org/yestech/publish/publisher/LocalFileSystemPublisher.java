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
import org.yestech.publish.objectmodel.ArtifactType;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.ProducerArtifactType;
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
public class LocalFileSystemPublisher implements IPublisher {
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
    public void publish(IArtifactMetaData metaData, InputStream artifact) {
        File fullPath = new File(directory + File.separator + metaData.getOwner().getIdentifier());
        String location = fullPath.getAbsolutePath() + File.separator + metaData.getFileName() + System.currentTimeMillis();
        FileOutputStream outputStream = null;
        try {
            outputStream = openOutputStream(new File(location));
            IOUtils.copyLarge(artifact, outputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            IOUtils.closeQuietly(artifact);
            IOUtils.closeQuietly(outputStream);
        }

        metaData.setLocation(location);
    }
}