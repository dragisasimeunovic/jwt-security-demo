package com.example.jwtsecuritydemo.constants;

/**
 * SIGN_UP_URL: Determines a public endpoint to register the user.
 *
 * KEY: Contains the key to sign the token and it has a length of 512 bytes
 * because it‘ll be used by an algorithm that requires a string of at least that length.
 *(Normally the key would be obtained from a secret and would never be hardcoded).
 *
 * HEADER_NAME: Contains the name of the header you are going to add the JWT to when doing a request.
 *
 * EXPIRATION_DATE: Contains the time (in milliseconds) during which the token is valid before expiring.
 */
public class SecurityConstants {

    public static final String SIGN_UP_URL = "/users/record";
    public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh";
    public static final String HEADER_NAME  = "Authorization";
    public static final Long EXPIRATION_TIME = 1000L*60*30;

}
