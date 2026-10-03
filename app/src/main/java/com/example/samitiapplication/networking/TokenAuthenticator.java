//package com.example.samitiapplication.networking;
//
//import com.example.samitiapplication.modal.ApiInterface;
//import com.google.firebase.installations.remote.TokenResult;
//
//import java.io.IOException;
//import okhttp3.Authenticator;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.Route;
//
//public class TokenAuthenticator implements Authenticator {
//    private final TokenManager tokenManager;
//    private final ApiInterface apiService; // Separate service instance for refreshing tokens
//
//    public TokenAuthenticator(TokenManager tokenManager, ApiInterface apiService) {
//        this.tokenManager = tokenManager;
//        this.apiService = apiService;
//    }
//
//    @Override
//    public Request authenticate(Route route, Response response) throws IOException {
//        // Prevent infinite loops if the refresh attempt itself throws a 401
//        if (responseCount(response) >= 3) {
//            return null;
//        }
//
//        synchronized (this) {
//            String currentAccessToken = tokenManager.getAccessToken();
//            String authorizationHeader = response.request().header("Authorization");
//
//            // If the token changed while this thread was waiting, retry with the newly updated token
//            if (authorizationHeader != null && !authorizationHeader.contains(currentAccessToken)) {
//                return response.request().newBuilder()
//                        .header("Authorization", "Bearer " + currentAccessToken)
//                        .build();
//            }
//
//            // Otherwise, proceed to call the backend refresh token endpoint
//            String refreshToken = tokenManager.getRefreshToken();
//            if (refreshToken == null) {
//                handleLogout();
//                return null;
//            }
//
//            // Trigger execution of the refresh API synchronously
////            retrofit2.Response<TokenResult> refreshResponse = apiService.refreshToken(refreshToken).execute();
//
//            if (refreshResponse.isSuccessful() && refreshResponse.body() != null) {
////                String newAccess = refreshResponse.body().getAccessToken();
////                String newRefresh = refreshResponse.body().getRefreshToken();
//
////                tokenManager.saveTokens(newAccess, newRefresh);
//
//                // Build a new request populated with the updated Access Token
//                return response.request().newBuilder()
//                        .header("Authorization", "Bearer " + newAccess)
//                        .build();
//            } else {
//                // Refresh token is invalid/expired; clear data and boot the user out
//                handleLogout();
//                return null;
//            }
//        }
//    }
//
//    private int responseCount(Response response) {
//        int result = 1;
//        while ((response = response.priorResponse()) != null) {
//            result++;
//        }
//        return result;
//    }
//
//    private void handleLogout() {
//        tokenManager.clearTokens();
//        // TODO: Broadcast an event or navigate to LoginActivity using Intent.FLAG_ACTIVITY_NEW_TASK
//    }
//}
//
