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

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
//import org.jmock.lib.legacy.ClassImposteriser;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.Jets3tProperties;
import org.jets3t.service.S3ObjectsChunk;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.security.AWSCredentials;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.S3BucketLoggingStatus;
import org.jets3t.service.model.S3Owner;
import org.yestech.publish.objectmodel.IFileArtifactMetaData;
import org.yestech.publish.objectmodel.IArtifactOwner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;
import java.util.Calendar;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@RunWith(JMock.class)
public class AmazonS3PublisherUnitTest {
    private Mockery context = new Mockery();
//    private Mockery context = new Mockery();
//
//  {{
//           setImposteriser(ClassImposteriser.INSTANCE);
//       }};

    private AmazonS3Publisher publisher;

    @Before
    public void setUp() {
        publisher = new AmazonS3Publisher();
    }

    @Test
    public void testPublish() throws S3ServiceException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
//        final S3Service service = context.mock(S3Service.class, "s3service");
        final IFileArtifactMetaData fileArtifact = context.mock(IFileArtifactMetaData.class, "fileArtifact");
        final IArtifactOwner artifactOwner = context.mock(IArtifactOwner.class, "owner");
        final String ownerId = "100";
        final String fileName = "testFile.txt";
        
        String data = "this is a test";
        ByteArrayInputStream stream = new ByteArrayInputStream(data.getBytes());

        context.checking(new Expectations() {
            {
                oneOf(fileArtifact).getOwner();
                will(returnValue(artifactOwner));
                oneOf(artifactOwner).getIdentifier();
                will(returnValue(ownerId));
                oneOf(fileArtifact).getFileName();
                will(returnValue(fileName));
                oneOf(fileArtifact).getSize();
                will(returnValue(100l));
                oneOf(fileArtifact).getMimeType();
                will(returnValue("application/txt"));
//                oneOf(service).putObject(with(aNonNull(S3Bucket.class)), with(aNonNull(S3Object.class)));
                oneOf(fileArtifact).setLocation(with(aNonNull(String.class)));
            }
        });
        publisher.setS3Service(new MockS3Service(null));
        publisher.setTempDirectory(tempDir);
        publisher.publish(fileArtifact, stream);
    }

    private static class MockS3Service extends S3Service {
        private MockS3Service(AWSCredentials awsCredentials, String s, Jets3tProperties jets3tProperties) throws S3ServiceException {
            super(awsCredentials, s, jets3tProperties);
        }

        private MockS3Service(AWSCredentials awsCredentials, String s) throws S3ServiceException {
            super(awsCredentials, s);
        }

        private MockS3Service(AWSCredentials awsCredentials) throws S3ServiceException {
            super(awsCredentials);
        }

        @Override
        public boolean isBucketAccessible(String s) throws S3ServiceException {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int checkBucketStatus(String s) throws S3ServiceException {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected String getBucketLocationImpl(String s) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3BucketLoggingStatus getBucketLoggingStatusImpl(String s) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void setBucketLoggingStatusImpl(String s, S3BucketLoggingStatus s3BucketLoggingStatus) throws S3ServiceException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void setRequesterPaysBucketImpl(String s, boolean b) throws S3ServiceException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected boolean isRequesterPaysBucketImpl(String s) throws S3ServiceException {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3Bucket[] listAllBucketsImpl() throws S3ServiceException {
            return new S3Bucket[0];  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3Owner getAccountOwnerImpl() throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3Object[] listObjectsImpl(String s, String s1, String s2, long l) throws S3ServiceException {
            return new S3Object[0];  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3ObjectsChunk listObjectsChunkedImpl(String s, String s1, String s2, long l, String s3, boolean b) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3Bucket createBucketImpl(String s, String s1, AccessControlList accessControlList) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void deleteBucketImpl(String s) throws S3ServiceException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3Object putObjectImpl(String s, S3Object s3Object) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected Map copyObjectImpl(String s, String s1, String s2, String s3, AccessControlList accessControlList, Map map, Calendar calendar, Calendar calendar1, String[] strings, String[] strings1) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void deleteObjectImpl(String s, String s1) throws S3ServiceException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3Object getObjectDetailsImpl(String s, String s1, Calendar calendar, Calendar calendar1, String[] strings, String[] strings1) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected S3Object getObjectImpl(String s, String s1, Calendar calendar, Calendar calendar1, String[] strings, String[] strings1, Long aLong, Long aLong1) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void putBucketAclImpl(String s, AccessControlList accessControlList) throws S3ServiceException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void putObjectAclImpl(String s, String s1, AccessControlList accessControlList) throws S3ServiceException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected AccessControlList getObjectAclImpl(String s, String s1) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected AccessControlList getBucketAclImpl(String s) throws S3ServiceException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public S3Object putObject(S3Bucket s3Bucket, S3Object s3Object) throws S3ServiceException {
            return null;
        }
    }

}
