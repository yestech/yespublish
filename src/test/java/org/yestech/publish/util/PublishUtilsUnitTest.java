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

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.yestech.publish.objectmodel.IArtifactOwner;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.IFileArtifactMetaData;
import org.apache.commons.lang.StringUtils;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublishUtilsUnitTest {
    private Mockery context = new Mockery();

    @Test
    public void testGenerateUniqueIdentifierFromOwner() {
        final IArtifactOwner owner = context.mock(IArtifactOwner.class, "owner");
        context.checking(new Expectations(){{
            oneOf(owner).getIdentifier();
            will(returnValue("1"));
        }});

        assertEquals("356a192b7913b04c54574d18c28d46e6395428ab", PublishUtils.generateUniqueIdentifier(owner));
    }

    @Test
    public void testGenerateUniqueIdentifierFromMetaData() {
        final IFileArtifactMetaData metaData = context.mock(IFileArtifactMetaData.class, "metaData");
        final String fileName = "1.jpg";
        context.checking(new Expectations(){{
            oneOf(metaData).getFileName();
            will(returnValue(fileName));
        }});
        String id = PublishUtils.generateUniqueIdentifier(metaData);
        assertEquals(46, id.length());
        String idSuffix = "_" + fileName;
        assertTrue(StringUtils.endsWith(id, idSuffix));
    }
}
