package com.ibm.cio.gss.isap_lite.utility;

/**
 * Created by Kabuli on 3/26/2018.
 */

public interface ISAP_Constants {

    //ISAP Services direct Link without IMC - DEV .
   // public static final String BASE_URL="https://isaplitedev.w3ibm.mybluemix.net/services/";//Dev API Base URL.
    //    public static final String IMC_IMAGE_URL="http://unified-profile.w3ibm.mybluemix.net/myw3/unified-profile-photo/v1/image/";

    //ISAP Services direct Link with IMC - DEV .
    public static final String BASE_URL="https://lmc1.watson.ibm.com:15036/isaplite/services/"; //Dev IMC Base Url.
    public static final String IMC_IMAGE_URL="https://lmc1.watson.ibm.com:15036/isaplite/myw3/unified-profile-photo/v1/image/"; //Dev Image IMC Base Url.
    public static final String PROFILE_INFO="https://lmc1.watson.ibm.com:15036/isaplite/myw3/unified-profile/v1/search/";

    //ISAP Services direct Link with IMC - PROD .
    //public static final String BASE_URL="https://mobile.us.ibm.com:15131/services/"; //Prod IMC Base Url.


    // Custom messages should go here.
    public static final String PROGRESSDIALOG_REGISTER="Registering...";
    public static final String PROGRESSDIALOG_AUTHENTICATING="Authenticating...";
    public static final String PROGRESSDIALOG_FETCHING="Fetching Clients...";
    public static final String PROGRESSDIALOG_LOADING="Loading...";
    public static final String PROGRESSDIALOG_VERIFY_CLIENTS="Verifying Clients..";
    public static final String FETCH_CLIENTS_DETAILS="Fetching Clients Details...";
    public static final String FETCH_MARKET_DETAILS="Fetching Markets...";
    public static final String FETCH_COUNTRY_DETAILS="Fetching Countries...";
    public static final String FETCH_GOALS="Fetching Goals...";
    public static final String FETCH_GOALS_Details="Fetching Goal Details...";
    public static final String FETCH_INITIATIVE_Details="Fetching Initiative Details...";
    public static final String FETCH_INITIATIVES="Fetching Initiatives...";
    public static final String FETCH_RELATIONSHIP="Fetching Relationship...";
    public static final String FETCH_LINKEDOPPORTUNITIES="Fetching Linked Opportunities...";

    //Add new Goal messages
    public static final String POPULATE_GOAL_FIELDS="Populating Goal fields , Please hold on..";
    public static final String SAVE_GOAL_INFO="Saving Goal Informations,please wait!";
    public static final String SAVE_GOAL_DONE="Your Goal has created successfully!";
    public static final String SAVE_GOAL_FAILURE="Oops, There is some problem ,please try again to save your goal!";
    public static final String DELETE_GOAL_INPROGRESS="Deleting your goal,please wait!";
    public static final String DELETE_GOAL="Your Goal has deleted successfully!";
    public static final String DELETE_GOAL_FAILURE="Oops, There is some problem ,please try again in-order to delete your goal!";
    public static final String DELETE_GOAL_ALERT="Are you sure you want to remove GOAL?";


    //Add new Initiative messages
    public static final String POPULATE_INITIATIVE_FIELDS="Populating Initiative fields , Please hold on..";
    public static final String SAVE_INITIATIVE_INFO="Saving Initiative Informations,please wait!";
    public static final String SAVE_INITIATIVE_DONE="Your Initiative has created successfully!";
    public static final String FILL_LINKED_GOALS="Please select a linked goal!";
    public static final String FILL_INITIATIVE_LEAD="Please select an initiative lead name!";
    public static final String FILL_STATEGIC_IMPERTIVES="Please select a stratgic imperative!";
    public static final String SAVE_INITIATIVE_FAILURE="Oops, There is some problem ,please try again to save your Initiative!";
    public static final String DELETE_INITIATIVE_INPROGRESS="Deleting your Initiative,please wait!";
    public static final String DELETE_INITIATIVE="Your Initiative has deleted successfully!";
    public static final String DELETE_INITIATIVE_FAILURE="Oops, There is some problem ,please try again in-order to delete your Initiative!";
    public static final String DELETE_INITIATIVE_ALERT="Are you sure you want to remove Initiative?";
    public static final String INITIATIVE_STATUS_KEY="Please select Partner Role";
    public static final String INITIATIVE_SPLIT_SUM_CAN_NOT_EXCEED="Sum of split values can not exceed 100, please adjust!";
    public static final String INITIATIVE_SPLITE_STATUS="You need to allocate 100% splite values, inorder to save initiative!";
    public static final String INITIATIVE_RELEVANT_BU_STATUS="Please select check box or make Relevant Brand Unit sum equal to 100 ";
    public static final String INITIATIVE_SELECT_GEO="Please select Geo again, in order to populate Market!";
    public static final String INITIATIVE_SELECT_MARKET="Please select Market again, in order to populate Country!";
    public static final String SAVE_INI_TO_LINK_OPPO="Please save initiative first, in-order to link opportunities!";


    //Add new Relationship messages
    public static final String POPULATE_RELATIONSHIP_FIELDS="Populating Relationship fields , Please hold on..";
    public static final String SAVE_RELATIONSHIP_INFO="Saving Relationship Informations,please hold on..!";
    public static final String SAVE_RELATIONSHIP_DONE="Your Relationship has created successfully!";
    public static final String SAVE_RELATIONSHIP_FAILURE="Oops, There is some problem, please try again to save your Relationship!";
    public static final String DELETE_RELATIONSHIP_INPROGRESS="Deleting your Relationship, please wait!";
    public static final String DELETE_RELATIONSHIP="Your Relationship has deleted successfully!";
    public static final String DELETE_RELATIONSHIP_FAILURE="Oops, There is some problem, please try again in-order to delete your Relationship!";
    public static final String DELETE_RELATIONSHIP_ALERT="Are you sure you want to remove Relationship?";
    public static final String FILL_CLIENT_EXEC_NAME="Please select an Executive name!";
    public static final String SELECT_REL_POSITION="Please select a position!";
    public static final String SELECT_REL_ASSESMENT="Please select a Assesment!";



    //Android Fingerprint custom messages.
    public static final String FINGERPRINT_BELOW_MARSHMELLOW="Fingerprint scanning not support below Marsmallow version, please update your version.";
    public static final String FINGERPRINT_AUTH_FAILURE="Can not recognize, please try again";
    public static final String FINGERPRINT_NO_HARDWARE="No finger print scaner available in the device.";
    public static final String FINGERPRINT_PLACE_FINGER="Place your registered finger on the fingerprint scanner";
    public static final String FINGERPRINT_NOT_REGISTER="No Finger print registered please enter credentials manually to register";
    public static final String FINGERPRINT_REGISTER_SUCCESS="Registration successful! You can use Touch Id now!.";

    //Error messages should go here.
    public static final String WRONG_CREDENTIALS = "Your w3id or password was entered incorrectly.";

   // public static final String invalid_credentials="Your w3id or password was entered incorrectly.Please register again";
    public static final String ERROR_TRYAGAIN = "Unable to process your request.Please try again.";
    public static final String NO_OPPTY = "There are no linked opportunities associated with this Initiative";
    public static final String NO_GOALS = "Currently no goals are available for this client";

    // public static final String wrong_credentials="Your w3id or password was entered incorrectly.";

//Logging Messages.
    public static final String Myplan_Country_Selection="My Plan data with Country selection:";
    public static final String RELATIONSHIP_SUMMERY="Relationship summery data: ";
    public static final String RELATIONSHIP_EXAPANDLIST="Relationship Exapand list data: ";
    public static final String Myplan_Market_Selection="My Plan data with Market selction:";
    public static final String Myplan_Geo_Selection="My Plan data with Geo selction:";
    //Validation messages should go here.

    public static final String CLIENT_NOT_SELECTED="Please Select a Client.";
    public static final String APP_CRASH_MESSAGE="Oops, there is something wrong,please try again by logging or connect Administrator! ";
    public static final String APP_EXCEPTION_MESSAGE="Oops, there is something wrong,please try again! ";
    public static final String FETCH_SUMMARY="Summary... ";
    public static final String NETWORK_ISSUE="Oops, there is a network issue, please try again!";
    public static final String SERVER_ISSUE="Oops, there is a server issue, please try again!";
    public static final String ERROR_REMAINING_VALUE = "Split value should not be greater than TCV value";
    public static final String ERROR_CLOSE_DATE = "Selected split year can not be greater than close date";
    public static final String ERROR_GEO = "All geos is selected,please change geo location";
}
