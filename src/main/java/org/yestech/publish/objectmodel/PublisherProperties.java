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

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.yestech.lib.util.Pair;

/**
 * Container for properties used by {@link org.yestech.publish.publisher.IPublisher}
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class PublisherProperties {
    private Map<ArtifactType,Map<String, Object>> properties;

    public PublisherProperties() {
        properties = newHashMap();
    }

    public Map<ArtifactType, Map<String, Object>> getProperties() {
        return properties;
    }

    public void setProperties(Map<ArtifactType, Map<String, Object>> properties) {
        this.properties = properties;
    }

    public <V> V getProperty(Pair<ArtifactType, String> key) {
        Map<String, Object> typeProperties = getTypeProperties(key);
        return (V) typeProperties.get(key.getSecond());
    }

    private Map<String, Object> getTypeProperties(Pair<ArtifactType, String> key) {
        Map<String, Object> typeProperties = properties.get(key.getFirst());
        if (typeProperties == null) {
            typeProperties = newHashMap();
            properties.put(key.getFirst(), typeProperties);
        }
        return typeProperties;
    }

    public void addProperty(Pair<ArtifactType, String> key, Object value) {
        Map<String, Object> typeProperties = getTypeProperties(key);
        typeProperties.put(key.getSecond(), value);
    }
}
