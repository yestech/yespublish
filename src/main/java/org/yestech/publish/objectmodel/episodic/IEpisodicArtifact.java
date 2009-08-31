package org.yestech.publish.objectmodel.episodic;

/**
 * Contains episodic specific information that can be saved to an object if this interface is implemented.
 *
 * @author A.J. Wright
 * @see org.yestech.publish.objectmodel.episodic.IEpisodicArtifactPersister
 */
public interface IEpisodicArtifact {

    String getAssetId();

    void setAssetId(String assetId);

    String getEpisodeId();

    void setEpisodeId(String episodeId);

}
