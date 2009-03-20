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

import org.yestech.publish.objectmodel.IArtifactOwner;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.lib.crypto.CryptoUtils;
import org.yestech.lib.crypto.MessageDigestUtils;
import static org.yestech.lib.crypto.MessageDigestUtils.sha1Hash;

import java.util.UUID;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PublishUtils {

    public static String generateUniqueIdentifier(IArtifactOwner owner) {
        return sha1Hash(owner.getIdentifier().toString());
    }

    public static String generateUniqueIdentifier(IArtifactMetaData metaData) {
        return sha1Hash(UUID.randomUUID().toString()) + "_" + metaData.getFileName();
    }
}
