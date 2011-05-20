/**
 * 
 */
package org.jboss.seam.social.oauth;

/**
 * @author antoine
 *
 */
public interface OAuthSessionSettings {

    /**
     * @return the requestToken
     */
    public OAuthToken getRequestToken();

    /**
     * @param requestToken the requestToken to set
     */
    public void setRequestToken(OAuthToken requestToken);

    /**
     * @return the accessToken
     */
    public OAuthToken getAccessToken();

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(OAuthToken accessToken);

    /**
     * @return the verifier
     */
    public String getVerifier();

    /**
     * @param verifier the verifier to set
     */
    public void setVerifier(String verifier);

    /**
     * @return the connected
     */
    public Boolean isConnected();

    /**
     * @param connected the connected to set
     */
    public void setConnected(Boolean connected);

    /**
     * @return the userProfile
     */
    public UserProfile getUserProfile();

    /**
     * @param userProfile the userProfile to set
     */
    public void setUserProfile(UserProfile userProfile);

    /**
     * @return the status
     */
    public String getStatus();

    /**
     * @param status the status to set
     */
    public void setStatus(String status);

}