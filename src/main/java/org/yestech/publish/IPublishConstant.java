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
package org.yestech.publish;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IPublishConstant {
    final public static String META_DATA = "metaData";
    final public static String META_DATA_IDENTIFIER = META_DATA + "_identifier";
    final public static String META_DATA_TYPE = META_DATA + "_type";

    final public static String CONTENT_SIZE = META_DATA + "_size";
    final public static String FILE_NAME = META_DATA + "_fileName";
    final public static String URL = META_DATA + "_url";
}
