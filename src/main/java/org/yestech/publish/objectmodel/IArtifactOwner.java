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
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IArtifactOwner<T extends IArtifactOwnerType, I> extends Serializable {
     T getOwnerType();

    void setOwnerType(T type);
    
    I getIdentifier();

    void setIdentifier(I identifier);

}
