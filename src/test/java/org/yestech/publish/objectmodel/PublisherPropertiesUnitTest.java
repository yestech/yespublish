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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.yestech.lib.util.Pair;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublisherPropertiesUnitTest {
    private PublisherProperties properties;

    @Before
    public void setUp() {
        properties = new PublisherProperties();
    }
    
    @Test
    public void testRetrievingNonSetProperty() {
        Object result = properties.getProperty(new Pair<ArtifactType, String>(ArtifactType.IMAGE, "url"));
        assertNull(result);
    }

    @Test
    public void testRetrievingProperty() {
        String value = "dave chappelle";
        String typeKey = "name";
        ArtifactType type = ArtifactType.VIDEO;
        properties.addProperty(new Pair<ArtifactType, String>(type, typeKey), value);
        String result = properties.getProperty(new Pair<ArtifactType, String>(type, typeKey));
        assertEquals(value, result);
    }
}
