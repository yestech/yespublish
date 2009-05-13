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

import org.apache.commons.lang.StringUtils;
import static org.apache.commons.io.FileUtils.openOutputStream;
import org.apache.commons.io.IOUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.yestech.publish.objectmodel.ArtifactType;
import org.yestech.publish.objectmodel.IFileArtifactMetaData;
import org.yestech.publish.objectmodel.ProducerArtifactType;
import static org.yestech.publish.util.PublishUtils.generateUniqueIdentifier;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Publishes to Amazons S3 storage.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@ProducerArtifactType(type = {ArtifactType.IMAGE, ArtifactType.VIDEO, ArtifactType.TEXT, ArtifactType.AUDIO})
public class AmazonS3Publisher extends BasePublisher implements IPublisher<IFileArtifactMetaData, InputStream> {
    final private static Logger logger = LoggerFactory.getLogger(AmazonS3Publisher.class);

    private String accessKey;
    private String secretKey;
    private AWSCredentials awsCredentials;
    private S3Service s3Service;
    private String bucketName;
    private S3Bucket artifactBucket;
    private String urlPrefix;

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    @Required
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Required
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @Required
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @PostConstruct
    public void init() throws S3ServiceException {
        awsCredentials = new AWSCredentials(accessKey, secretKey);
        s3Service = new RestS3Service(awsCredentials);
        S3Bucket[] myBuckets = s3Service.listAllBuckets();
        if (logger.isDebugEnabled()) {
            //test s3 connection
            logger.debug("How many buckets to I have in S3? " + myBuckets.length);
        }
        boolean createNewBucket = true;
        for (S3Bucket bucket : myBuckets) {
            if (StringUtils.equals(getBucketName(), bucket.getName())) {
                createNewBucket = false;
                break;
            }
        }
        if (createNewBucket) {
            artifactBucket = s3Service.createBucket(getBucketName());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Created test bucket: " + artifactBucket.getName());
        }
    }

    @Override
    public void publish(IFileArtifactMetaData metaData, InputStream artifact) {
        saveToDisk();
        String artifactName = generateUniqueIdentifier(metaData.getOwner());
        DateTime lengthOfLink = new DateTime();
        lengthOfLink.plusYears(20);
        try {
//            File
//            final byte[] bytes = ServiceUtils.computeMD5Hash(artifact);
//            artifact.reset();
            S3Object s3Artifact = new S3Object(artifactName);
            s3Artifact.setDataInputStream(artifact);
            s3Artifact.setContentLength(metaData.getSize());
            s3Artifact.setContentType(metaData.getMimeType());

            // Upload the data objects.
            s3Service.putObject(artifactBucket, s3Artifact);
            S3Service.createSignedGetUrl(artifactBucket.getName(), artifactName, s3Service.getAWSCredentials(), lengthOfLink.toDate());
            String location = getUrlPrefix();
            metaData.setLocation(location);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


//        File fullPath = null; //new File(directory + File.separator + generateUniqueIdentifier(metaData.getOwner()));
//        if (!fullPath.exists()) {
//            fullPath.mkdirs();
//        }
//        String location = fullPath.getAbsolutePath() + File.separator + generateUniqueIdentifier(metaData);
//        FileOutputStream outputStream = null;
//        try {
//            if (logger.isDebugEnabled()) {
//                logger.debug("Saving file: " + location);
//            }
//            outputStream = openOutputStream(new File(location));
//            IOUtils.copyLarge(artifact, outputStream);
//            outputStream.flush();
//            if (logger.isDebugEnabled()) {
//                logger.debug("Saved file: " + location);
//            }
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        finally {
//            IOUtils.closeQuietly(artifact);
//            IOUtils.closeQuietly(outputStream);
//        }

    }

    private void saveToDisk() {
        File fullPath = new File(directory + File.separator + generateUniqueIdentifier(metaData.getOwner()));
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
            IOUtils.copyLarge(artifact, outputStream);
            outputStream.flush();
            if (logger.isDebugEnabled()) {
                logger.debug("Saved file: " + location);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            IOUtils.closeQuietly(artifact);
            IOUtils.closeQuietly(outputStream);
        }
    }
}