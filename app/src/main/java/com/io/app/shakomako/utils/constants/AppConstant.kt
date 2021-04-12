package com.io.app.shakomako.utils.constants

class AppConstant {

    companion object {

        const val CHANGE_PHONE_NUMBER = 11
        const val PARCEL_DATA = "parcel_data"
        const val EXTRA_DATA = "extra_data"
        const val BUSINESS_ID = "business_id"
        const val PRODUCT_ID = "product_id"
        const val TYPE = "type"
        const val ROOM_ID = "room_id"
        const val INVOICE_ID = "invoice_id"
        const val LOCATION = 99

        /**Edit product Constant*/
        const val EDIT_PRODUCT = 10
        const val CREATE_PRODUCT = 11

        /**Edit address Constant*/
        const val UPDATE_ADDRESS = 10
        const val ADD_ADDRESS = 11

        /**Chat Activity Constant*/
        const val PERSONAL_CHAT = 10
        const val BUSINESS_CHAT = 11
        const val CREATE_CHAT = 12

        /**Invoice Activity Constant*/
        const val CREATE_INVOICE = 10
        const val VIEW_INVOICE = 11
        const val VIEW_INVOICE_PERSONAL = 12
        const val VIEW_INVOICE_BUSINESS = 13


        const val RC_SIGN_IN = 1001


        const val CURRENCY = "IQD"

        const val CHAT_FRAGMENT = 101
        const val VERIFY_BUSINESS_FRAGMENT = 102
        const val MY_DEAL = 103
        const val PROFILE_ANALYTICS = 104
        const val ANALYTICS = 105
        const val VERIFY_PROFILE_FRAGMENT = 106
        const val DELIVERY_ADDRESS = 107
        const val PRODUCT_DETAIL = 108
        const val EDIT_PROFILE = 109
        const val SHOP_ITEM_DETAILS = 110
        const val NAVIGATION_SHOP_DETAIL = 111
        const val RATING_FRAGMENT = 112
        const val EDIT_PROFILE_FIELD = 113

        /*******Ted permission */
        const val PERMISSION_ACCEPTED = 201
        const val PERMISSION_DENIED = 202

        const val YES = "yes"
        const val NO = "no"


        const val BASE_URL = "https://api.instagram.com/"
        const val TOKEN_URL = "https://api.instagram.com/oauth/access_token"


        const val OAUTH_PARAM = "oauth"
        const val CLIENT_ID_PARAM = "client_id"
        const val AUTHORIZE_PARAM = "authorize"
        const val SECRET_KEY_PARAM = "client_secret"
        const val REDIRECT_URI = "redirect_uri"
        const val SCOPE_PARAM = "scope"
        const val RESPONSE_TYPE_PARAM = "response_type"
        const val USER_PROFILE = "user_profile"
        const val USER_MEDIA = "user_media"
        const val CODE = "code"
        const val GRANT_TYPE_PARAM = "authorization_code"
        const val REDIRECT_URI_PARAM = "redirect_uri"
        const val BASIC_DETAILS_FIELDS = "id,username"

        val QUESTION_MARK: String? = "?"
        const val AMPERSAND = "&"
        const val EQUALS = "="
        const val SLASH = "/"
        const val COMMA = ","

        //Instagram Constants
        const val CLIENT_ID = "233262804841972"
        const val CLIENT_SECRET_ID = "714f18ed5f5e6baf4562052dfa251315"
        const val REDIRECT_URL = "233262804841972"
        const val redirectURIURLEncoded = "https://www.google.com/"
// const val redirectURI = "https://www.google.com/"
// const val app_secret = "714f18ed5f5e6baf4562052dfa251315"
// const val BASE_URL = "https://api.instagram.com"


        //Intent Constants
        const val INTENT_CODE_FOR_INSTAGRAM = 100
        const val INSTAGRAM_SOCIAL_TOKEN_KEY = "ACCESS_TOKEN"


        /**
         * @param login_type - instagram, facebook, google, apple, phone
         */
        const val LOGIN_TYPE_INSTAGRAM = "instagram"
        const val LOGIN_TYPE_GOOGLE = "google"
        const val LOGIN_TYPE_FACEBOOK = "facebook"
        const val LOGIN_TYPE_PHONE = "phone"


        const val LANGUAGE_TYPE: String = "lang_type"
        const val LANGUAGE_TYPE_ENGLISH: String = "en"
        const val LANGUAGE_TYPE_ARABIC: String = "ar"
    }
}