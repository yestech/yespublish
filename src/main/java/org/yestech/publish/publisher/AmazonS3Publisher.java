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
import org.apache.commons.io.FileUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.Constants;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.utils.ServiceUtils;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.yestech.publish.objectmodel.*;
import static org.yestech.publish.util.PublishUtils.generateUniqueIdentifier;
import org.yestech.publish.util.PublishUtils;
import org.yestech.lib.util.Pair;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * Publishes to Amazons S3 storage.
 * <br/>
 * Valid Properties
 * <ul>
 * <li>urlPrefix = Mandatory</li>
 * <li>tempDirectory = Mandatory</li>
 * <li>secretKey = Mandatory</li>
 * <li>accessKey = Mandatory</li>
 * <li>bucketName = Mandatory</li>
 * </ul>
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@ProducerArtifactType(type = {ArtifactType.IMAGE, ArtifactType.VIDEO, ArtifactType.TEXT, ArtifactType.AUDIO})
public class AmazonS3Publisher extends BasePublisher implements IPublisher<IFileArtifact> {
    final private static Logger logger = LoggerFactory.getLogger(AmazonS3Publisher.class);

    private AWSCredentials awsCredentials;
    private S3Service s3Service;
    private S3Bucket artifactBucket;
    private static final String HTTP_SEPARATOR = "/";
    private PublisherProperties properties;
    private ArtifactType artifactType;

    public AmazonS3Publisher() {
        properties = new PublisherProperties();
    }

    public File getTempDirectory() {
        return properties.getProperty(Pair.create(getArtifactType(), "tempDirectory"));
    }

    public PublisherProperties getProperties() {
        return properties;
    }

    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Required
    public void setArtifactType(ArtifactType artifactType) {
        this.artifactType = artifactType;
    }

    @Required
    public void setProperties(PublisherProperties properties) {
        this.properties = properties;
    }

    private String getUrlPrefix() {
        return properties.getProperty(Pair.create(getArtifactType(), "urlPrefix"));
    }

    public String getAccessKey() {
        return properties.getProperty(Pair.create(getArtifactType(), "accessKey"));
    }

    public String getBucketName() {
        return properties.getProperty(Pair.create(getArtifactType(), "bucketName"));
    }

    public String getSecretKey() {
        return properties.getProperty(Pair.create(getArtifactType(), "secretKey"));
    }

    public S3Service getS3Service() {
        return s3Service;
    }

    public void setS3Service(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostConstruct
    public void init() {
        try {
            awsCredentials = new AWSCredentials(getAccessKey(), getSecretKey());
            s3Service = new RestS3Service(awsCredentials);
            S3Bucket[] myBuckets = s3Service.listAllBuckets();
            if (logger.isDebugEnabled()) {
                //test s3 connection
                logger.debug("How many buckets to I have in S3? " + myBuckets.length);
            }
            boolean createNewBucket = true;
            for (S3Bucket bucket : myBuckets) {
                if (StringUtils.equals(getBucketName(), bucket.getName())) {
                    artifactBucket = bucket;
                    createNewBucket = false;
                    break;
                }
            }
            if (createNewBucket) {
                artifactBucket = s3Service.createBucket(getBucketName());
                setACLOnBucket();
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Created test bucket: " + artifactBucket.getName());
            }
        } catch (S3ServiceException e) {
            logger.error("Error initializing Amazon S3 Publisher...", e);
        }
    }

    private void setACLOnBucket() throws S3ServiceException {
        AccessControlList bucketAcl = s3Service.getBucketAcl(artifactBucket);
        bucketAcl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
        artifactBucket.setAcl(bucketAcl);
        s3Service.putBucketAcl(artifactBucket);
    }

    @Override
    public void publish(IFileArtifact artifact) {
        IFileArtifactMetaData metaData = artifact.getArtifactMetaData();
        InputStream artifactStream = artifact.getStream();
        String artifactDirectoryName = generateUniqueIdentifier(metaData.getArtifactOwner());
        String uniqueFileName = generateUniqueIdentifier(metaData);
        
        Pair<String, String> names = metaData.getUniqueNames();
        if (names != null) {
            String path = names.getFirst();
            if (StringUtils.isNotBlank(path)) {
                artifactDirectoryName = path;
            }
        }
        if (names != null) {
            String fileName = names.getSecond();
            if (StringUtils.isNotBlank(fileName)) {
                uniqueFileName = fileName;
            }
        }

        final String tempFileFqn = saveToDisk(artifactDirectoryName, artifactStream, uniqueFileName);
        try {
            final StringBuilder s3LocationBuilder = new StringBuilder();
            final String s3Location = s3LocationBuilder.append(artifactDirectoryName).append(Constants.FILE_PATH_DELIM).append(uniqueFileName).toString();
            S3Object s3Artifact = new S3Object(s3Location);
            s3Artifact.setContentLength(metaData.getSize());
            s3Artifact.setContentType(metaData.getMimeType());

            s3Artifact.setMd5Hash(ServiceUtils.computeMD5Hash(new FileInputStream(new File(tempFileFqn))));
            s3Artifact.setDataInputStream(new FileInputStream(new File(tempFileFqn)));

            setCredentials(s3Artifact);
            // Upload the data objects.
            s3Service.putObject(artifactBucket, s3Artifact);
            setFinalLocationInMetaData(metaData, artifactDirectoryName, uniqueFileName);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (logger.isInfoEnabled()) {
                logger.info("removing file: " + tempFileFqn);
            }
            File uploadedFile = new File(tempFileFqn);
            if (uploadedFile.exists()) {
                uploadedFile.delete();
            }
            PublishUtils.reset(artifact);
        }
    }

    private void setCredentials(S3Object s3Artifact) {
        //TODO:  added better
        s3Artifact.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
    }

    private void setFinalLocationInMetaData(IFileArtifactMetaData metaData, String artifactDirectoryName, String uniqueFileName) {
        String defaultLocation = metaData.getLocation();
        if (StringUtils.isBlank(defaultLocation)) {
            final StringBuilder builder = new StringBuilder();
            String location = builder.append(getUrlPrefix()).append(HTTP_SEPARATOR).append(artifactDirectoryName).append(HTTP_SEPARATOR).append(uniqueFileName).toString();
            metaData.setLocation(location);
        }
    }

    private String saveToDisk(String artifactDirectoryName, InputStream artifact, String uniqueFileName) {
        File fullPath = new File(getTempDirectory() + File.separator + artifactDirectoryName);
        if (!fullPath.exists()) {
            fullPath.mkdirs();
        }
        String location = fullPath.getAbsolutePath() + File.separator + uniqueFileName;
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
        return location;
    }
}