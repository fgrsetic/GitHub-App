

# Authentication
// 1. Developer registers at the GitHub service for the new App	-> Receives clientID, client secret
// 2. Registered App requests authorization to access service data
// 3. The app opens a special page of the service, where User logs in an then permits the app access
// 4. If successful, the service will respond with an authorization token
// 5. App uses the authorization token to request an access token
// 6. Save token to secured prefs
// 7. Access token can be used for all following requests