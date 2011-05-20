/**
 * 
 */
package org.jboss.seam.social.oauth;

/**
 * @author  antoine
 */
public class OAuthSessionSettingsScribe implements OAuthSessionSettings {
    /**
     * 
     */
    private OAuthToken requestToken;
    /**
     * 
     */
    private OAuthToken accessToken;
    /**
     * 
     */
    private String verifier;
    /**
     * 
     */
    private Boolean connected;
    /**
     * 
     */
    private UserProfile userProfile;
    /**
     * 
     */
    private String status;

    /**
     * 
     */
    public OAuthSessionSettingsScribe(Boolean connected) {
        this.connected = connected;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getRequestToken()
     */
    @Override
    public OAuthToken getRequestToken() {
        return requestToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setRequestToken(org.jboss.seam.social.oauth.OAuthTokenScribe)
     */
    @Override
    public void setRequestToken(OAuthToken requestToken) {
        this.requestToken = requestToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getAccessToken()
     */
    @Override
    public OAuthToken getAccessToken() {
        return accessToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setAccessToken(org.jboss.seam.social.oauth.OAuthTokenScribe)
     */
    @Override
    public void setAccessToken(OAuthToken accessToken) {
        this.accessToken = accessToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getVerifier()
     */
    @Override
    public String getVerifier() {
        return verifier;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setVerifier(java.lang.String)
     */
    @Override
    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#isConnected()
     */
    @Override
    public Boolean isConnected() {
        return connected;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setConnected(java.lang.Boolean)
     */
    @Override
    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getUserProfile()
     */
    @Override
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setUserProfile(org.jboss.seam.social.oauth.UserProfile)
     */
    @Override
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getStatus()
     */
    @Override
    public String getStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setStatus(java.lang.String)
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}