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

import java.io.Serializable;

/**
 * Represents a generic type that can be published.
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IArtifact<MD extends IArtifactMetaData, ID> extends Serializable {
    ID getIdentifier();

    void setIdentifier(ID identifier);

    public MD getArtifactMetaData();

    public void setArtifactMetaData(MD artifactMetaData);

}
