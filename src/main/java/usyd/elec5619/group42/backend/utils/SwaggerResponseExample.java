package usyd.elec5619.group42.backend.utils;

public class SwaggerResponseExample {
    public static final String GET_USER_OK = "{\n" +
            "    \"user\": {\n" +
            "        \"userId\": 1034,\n" +
            "        \"username\": \"userxxxx\",\n" +
            "        \"firstName\": Joe,\n" +
            "        \"lastName\": Biden,\n" +
            "        \"location\": USA,\n" +
            "        \"email\": whitehouse@gov.us,\n" +
            "        \"phone\": +61 123456789,\n" +
            "        \"portrait\": abcd4567.jpg,\n" +
            "        \"favoriteList\": [PetPost_1, PetPost_2],\n" +
            "        \"posts\": [PetPost_2, PetPost_4]\n" +
            "    }\n" +
            "}";

    public static final String GET_USER_LIST_OK = "{\n" +
            "    \"user_list\": [\n" +
            "        {\n" +
            "            \"userId\": 1,\n" +
            "            \"username\": \"user_1\",\n" +
            "            \"firstName\": null,\n" +
            "            \"lastName\": null,\n" +
            "            \"location\": null,\n" +
            "            \"email\": null,\n" +
            "            \"phone\": null,\n" +
            "            \"portrait\": null,\n" +
            "            \"favoriteList\": [],\n" +
            "            \"posts\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"userId\": 2,\n" +
            "            \"username\": \"user_2\",\n" +
            "            \"firstName\": null,\n" +
            "            \"lastName\": null,\n" +
            "            \"location\": null,\n" +
            "            \"email\": null,\n" +
            "            \"phone\": null,\n" +
            "            \"portrait\": null,\n" +
            "            \"favoriteList\": [],\n" +
            "            \"posts\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"userId\": 3,\n" +
            "            \"username\": \"user_3\",\n" +
            "            \"firstName\": null,\n" +
            "            \"lastName\": null,\n" +
            "            \"location\": null,\n" +
            "            \"email\": null,\n" +
            "            \"phone\": null,\n" +
            "            \"portrait\": null,\n" +
            "            \"favoriteList\": [],\n" +
            "            \"posts\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"userId\": 4,\n" +
            "            \"username\": \"user_4\",\n" +
            "            \"firstName\": null,\n" +
            "            \"lastName\": null,\n" +
            "            \"location\": null,\n" +
            "            \"email\": null,\n" +
            "            \"phone\": null,\n" +
            "            \"portrait\": null,\n" +
            "            \"favoriteList\": [],\n" +
            "            \"posts\": []\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static final String LOGIN_OK = "{\n" +
            "    \"user\": {\n" +
            "        \"userId\": 1034,\n" +
            "        \"username\": \"userxxxx\",\n" +
            "        \"firstName\": Biden,\n" +
            "        \"lastName\": Jow,\n" +
            "        \"location\": USA,\n" +
            "        \"email\": whitehouse@gov.us,\n" +
            "        \"phone\": +61 123456789,\n" +
            "        \"portrait\": abcd4567.jpg,\n" +
            "        \"favoriteList\": [],\n" +
            "        \"posts\": []\n" +
            "    },\n" +
            "    \"token\": \"eyJ0eXAiOiJKVciOiJIUzI1NiJ9.eyJzdiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvbG9naW4idGllcyI6WyJhdXRoZW50aWNhdGXJuYW1lIjoiYWRtaW51c2VyIn0.9uvZtG_hQXSe3tvyK5uqLeKi8c14YxLuRI\"\n" +
            "}";

    public static final String LOGOUT_OK = "{\n" +
            "    \"message\": \"Sign out successfully\"\n" +
            "}";

    public static final String CREATE_POST_OK = "{\n" +
            "    \"post\": {\n" +
            "        \"petId\": 2,\n" +
            "        \"title\": \"Hello Pet Trading\",\n" +
            "        \"description\": \"\",\n" +
            "        \"price\": null,\n" +
            "        \"location\": null,\n" +
            "        \"creationTime\": null,\n" +
            "        \"clickCount\": 0,\n" +
            "        \"seller\": null,\n" +
            "        \"thumbnail\": null,\n" +
            "        \"pictures\": null,\n" +
            "        \"category\": null,\n" +
            "        \"sold\": false,\n" +
            "        \"weight\": null,\n" +
            "        \"gender\": null,\n" +
            "        \"ageYears\": null,\n" +
            "        \"ageMonths\": null,\n" +
            "        \"desexed\": null,\n" +
            "        \"microchip\": null,\n" +
            "        \"vaccinated\": null,\n" +
            "        \"wormed\": null\n" +
            "    }\n" +
            "}";

    public static final String GET_POST_LIST_OK = "{\n" +
            "    \"page_number\": 1,\n" +
            "    \"post_list\": [\n" +
            "        {\n" +
            "            \"petId\": 1,\n" +
            "            \"title\": \"test-post\",\n" +
            "            \"description\": null,\n" +
            "            \"price\": null,\n" +
            "            \"location\": null,\n" +
            "            \"creationTime\": null,\n" +
            "            \"clickCount\": null,\n" +
            "            \"seller\": null,\n" +
            "            \"thumbnail\": null,\n" +
            "            \"pictures\": [],\n" +
            "            \"category\": null,\n" +
            "            \"sold\": null,\n" +
            "            \"weight\": null,\n" +
            "            \"gender\": null,\n" +
            "            \"ageYears\": null,\n" +
            "            \"ageMonths\": null,\n" +
            "            \"desexed\": null,\n" +
            "            \"microchip\": null,\n" +
            "            \"vaccinated\": null,\n" +
            "            \"wormed\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"petId\": 2,\n" +
            "            \"title\": \"Hello Pet Trading\",\n" +
            "            \"description\": null,\n" +
            "            \"price\": null,\n" +
            "            \"location\": null,\n" +
            "            \"creationTime\": null,\n" +
            "            \"clickCount\": 0,\n" +
            "            \"seller\": null,\n" +
            "            \"thumbnail\": null,\n" +
            "            \"pictures\": [],\n" +
            "            \"category\": null,\n" +
            "            \"sold\": false,\n" +
            "            \"weight\": null,\n" +
            "            \"gender\": null,\n" +
            "            \"ageYears\": null,\n" +
            "            \"ageMonths\": null,\n" +
            "            \"desexed\": null,\n" +
            "            \"microchip\": null,\n" +
            "            \"vaccinated\": null,\n" +
            "            \"wormed\": null\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static final String GET_POST_OK = "{\n" +
            "    \"post\": {\n" +
            "        \"petId\": 1,\n" +
            "        \"title\": \"test-post\",\n" +
            "        \"description\": null,\n" +
            "        \"price\": null,\n" +
            "        \"location\": null,\n" +
            "        \"creationTime\": null,\n" +
            "        \"clickCount\": null,\n" +
            "        \"seller\": null,\n" +
            "        \"thumbnail\": null,\n" +
            "        \"pictures\": [],\n" +
            "        \"category\": null,\n" +
            "        \"sold\": null,\n" +
            "        \"weight\": null,\n" +
            "        \"gender\": null,\n" +
            "        \"ageYears\": null,\n" +
            "        \"ageMonths\": null,\n" +
            "        \"desexed\": null,\n" +
            "        \"microchip\": null,\n" +
            "        \"vaccinated\": null,\n" +
            "        \"wormed\": null\n" +
            "    }\n" +
            "}";

    public static final String CHANGE_PASSWORD_OK = "{\n" +
            "    \"message\": \"ok\",\n" +
            "    \"user\": {\n" +
            "        \"userId\": 3,\n" +
            "        \"username\": \"adminuser\",\n" +
            "        \"firstName\": \"Super\",\n" +
            "        \"lastName\": \"Admin\",\n" +
            "        \"location\": null,\n" +
            "        \"email\": null,\n" +
            "        \"phone\": null,\n" +
            "        \"portrait\": null,\n" +
            "        \"favoriteList\": [],\n" +
            "        \"posts\": []\n" +
            "    }\n" +
            "}";

    public static final String UPDATE_PROFILE_OK = "{\n" +
            "    \"message\": \"ok\",\n" +
            "    \"user\": {\n" +
            "        \"userId\": 3,\n" +
            "        \"username\": \"adminuser\",\n" +
            "        \"firstName\": \"Super\",\n" +
            "        \"lastName\": \"Admin\",\n" +
            "        \"location\": null,\n" +
            "        \"email\": null,\n" +
            "        \"phone\": null,\n" +
            "        \"portrait\": null,\n" +
            "        \"favoriteList\": [],\n" +
            "        \"posts\": []\n" +
            "    }\n" +
            "}";

    public static final String GET_PREFERENCE_OK = "{\n" +
            "    \"preferenceList\": [\n" +
            "        {\n" +
            "            \"categoryId\": 5,\n" +
            "            \"type\": \"Dogs\",\n" +
            "            \"breed\": \"Corgi\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"categoryId\": 6,\n" +
            "            \"type\": \"Dogs\",\n" +
            "            \"breed\": \"Chihuahua\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"username\": \"adminuser\"\n" +
            "}";

    public static final String UPDATE_PREFERENCE_OK = "{\n" +
            "    \"message\": \"ok\",\n" +
            "    \"user\": {\n" +
            "        \"userId\": 3,\n" +
            "        \"username\": \"adminuser\",\n" +
            "        \"firstName\": \"Super\",\n" +
            "        \"lastName\": \"Admin\",\n" +
            "        \"location\": null,\n" +
            "        \"email\": null,\n" +
            "        \"phone\": null,\n" +
            "        \"portrait\": null,\n" +
            "        \"favoriteList\": [\n" +
            "            {\n" +
            "                \"categoryId\": 6,\n" +
            "                \"type\": \"Dogs\",\n" +
            "                \"breed\": \"Chihuahua\",\n" +
            "                \"hibernateLazyInitializer\": {}\n" +
            "            },\n" +
            "            {\n" +
            "                \"categoryId\": 5,\n" +
            "                \"type\": \"Dogs\",\n" +
            "                \"breed\": \"Corgi\",\n" +
            "                \"hibernateLazyInitializer\": {}\n" +
            "            }\n" +
            "        ],\n" +
            "        \"posts\": []\n" +
            "    }\n" +
            "}";

    public static final String GET_BOOKMARKS_OK = "{\n" +
            "    \"bookmarks\": [\n" +
            "        {\n" +
            "            \"petId\": 1,\n" +
            "            \"title\": \"coco\",\n" +
            "            \"description\": \"good rabbit\",\n" +
            "            \"price\": 100.50,\n" +
            "            \"location\": \"Brisbane\",\n" +
            "            \"creationTime\": 1633106872073,\n" +
            "            \"clickCount\": 0,\n" +
            "            \"seller\": null,\n" +
            "            \"thumbnail\": null,\n" +
            "            \"pictures\": [],\n" +
            "            \"category\": {\n" +
            "                \"categoryId\": 5,\n" +
            "                \"type\": \"Dogs\",\n" +
            "                \"breed\": \"Corgi\"\n" +
            "            },\n" +
            "            \"sold\": false,\n" +
            "            \"weight\": 4.50,\n" +
            "            \"gender\": \"female\",\n" +
            "            \"ageYears\": 3,\n" +
            "            \"ageMonths\": 10,\n" +
            "            \"desexed\": false,\n" +
            "            \"microchip\": true,\n" +
            "            \"vaccinated\": false,\n" +
            "            \"wormed\": false\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
