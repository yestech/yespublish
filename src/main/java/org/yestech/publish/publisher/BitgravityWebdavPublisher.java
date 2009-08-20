package org.yestech.publish.publisher;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import static org.yestech.lib.util.Pair.create;
import org.yestech.publish.objectmodel.*;
import static org.yestech.publish.util.PublishUtils.generateUniqueIdentifier;

import java.io.IOException;
import java.io.InputStream;

/**
 * Publishes to Bitgravity using their webdav api.
 * <br />
 * <ul>
 * <li>webDavPrefix - Prefix to the web dav</li>
 * <li>uriPrefix - Base URL for git hub</ul>
 * <li>username - The username used for pushing to Bitgravity</li>
 * <li>password - The password for Bitgravity</li>
 * </ul>
 *
 * @author A.J. Wright
 */
@ProducerArtifactType(type = {ArtifactType.IMAGE, ArtifactType.VIDEO, ArtifactType.TEXT, ArtifactType.AUDIO})
public class BitgravityWebdavPublisher extends BasePublisher implements IPublisher<IFileArtifact> {

    private static final Logger log = LoggerFactory.getLogger(BitgravityWebdavPublisher.class);
    private static final String HTTP_SEPARATOR = "/";

    private PublisherProperties properties;
    private ArtifactType artifactType;

    public BitgravityWebdavPublisher() {
        properties = new PublisherProperties();
    }

    @Override
    public void publish(IFileArtifact artifact) {
        IFileArtifactMetaData metaData = artifact.getArtifactMetaData();
        InputStream artifactStream = artifact.getStream();

        try {

            HttpClient client = new HttpClient();
            Credentials creds = new UsernamePasswordCredentials(getUsername(), getPassword());
            client.getState().setCredentials(AuthScope.ANY, creds);

            PutMethod method = new PutMethod(getPublishUrl(metaData));
            RequestEntity requestEntity = new InputStreamRequestEntity(artifactStream);
            method.setRequestEntity(requestEntity);
            client.executeMethod(method);
            log.debug(method.getStatusCode() + " " + method.getStatusText());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    protected void setLocation(IFileArtifactMetaData metaData) {
        String defaultLocation = metaData.getLocation();
        if (StringUtils.isBlank(defaultLocation)) {
            String artifactDirectoryName = generateUniqueIdentifier(metaData.getArtifactOwner());
            String uniqueFileName = generateUniqueIdentifier(metaData);
            final StringBuilder builder = new StringBuilder();
            String location = builder.append(getUrlPrefix())
                    .append(HTTP_SEPARATOR)
                    .append(artifactDirectoryName)
                    .append(HTTP_SEPARATOR)
                    .append(uniqueFileName)
                    .toString();
            metaData.setLocation(location);
        }
    }

    protected String getPublishUrl(IFileArtifactMetaData metaData) {
        String defaultLocation = metaData.getLocation();
        if (StringUtils.isBlank(defaultLocation)) {
            String artifactDirectoryName = generateUniqueIdentifier(metaData.getArtifactOwner());
            String uniqueFileName = generateUniqueIdentifier(metaData);
            final StringBuilder builder = new StringBuilder();
            String location = builder.append(getWebDavPrefix())
                    .append(HTTP_SEPARATOR)
                    .append(artifactDirectoryName)
                    .append(HTTP_SEPARATOR)
                    .append(uniqueFileName)
                    .toString();
            metaData.setLocation(location);
        }
        return defaultLocation;
    }

    @Required
    public void setArtifactType(ArtifactType artifactType) {
        this.artifactType = artifactType;
    }

    @Required
    public void setProperties(PublisherProperties properties) {
        this.properties = properties;
    }

    public String getUrlPrefix() {
        return properties.getProperty(create(getArtifactType(), "urlPrefix"));
    }

    public String getUsername() {
        return properties.getProperty(create(getArtifactType(), "username"));
    }

    public String getPassword() {
        return properties.getProperty(create(getArtifactType(), "password"));
    }

    public String getWebDavPrefix() {
        return properties.getProperty(create(getArtifactType(), "webDavPrefix"));
    }

    public ArtifactType getArtifactType() {
        return artifactType;
    }
}
