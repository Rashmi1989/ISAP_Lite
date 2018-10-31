package com.ibm.cio.gss.isap_lite.remote;

import com.ibm.cio.gss.isap_lite.model.BluepageAuth;
import com.ibm.cio.gss.isap_lite.model.ClientsModel;
import com.ibm.cio.gss.isap_lite.model.CountryMyPlan;
import com.ibm.cio.gss.isap_lite.model.DeleteInitiativeModel;
import com.ibm.cio.gss.isap_lite.model.GOAL_FIELDS;
import com.ibm.cio.gss.isap_lite.model.GeoMyplanModel;
import com.ibm.cio.gss.isap_lite.model.GetCountryModel;
import com.ibm.cio.gss.isap_lite.model.GetCountryResponse;
import com.ibm.cio.gss.isap_lite.model.GetRegionModel;
import com.ibm.cio.gss.isap_lite.model.GoalDeleteModel;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
import com.ibm.cio.gss.isap_lite.model.GoalsModel;
import com.ibm.cio.gss.isap_lite.model.InitiativeFields_Model;
import com.ibm.cio.gss.isap_lite.model.InitiativeModel;
import com.ibm.cio.gss.isap_lite.model.LinkedOpportunitiesModel;
import com.ibm.cio.gss.isap_lite.model.MarketMyPlan;
import com.ibm.cio.gss.isap_lite.model.MyPlanModel;
import com.ibm.cio.gss.isap_lite.model.RegionsResponse;
import com.ibm.cio.gss.isap_lite.model.RelationshipExpandList;
import com.ibm.cio.gss.isap_lite.model.RelationshipModel;
import com.ibm.cio.gss.isap_lite.model.RelationshipMore;
import com.ibm.cio.gss.isap_lite.model.SingletonSession;
import com.ibm.cio.gss.isap_lite.model.authModel;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.NewRelationshipModel;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.NewRelationshipRequestModel;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.NewRelationship_SaveResponseObj;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.Relationship_DelObj;
import com.ibm.cio.gss.isap_lite.viewModel.GoalViewModel;
import com.ibm.cio.gss.isap_lite.viewModel.InitiativeViewModel;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
Class/Interface Name : "ISAPService"
Description :"Interface which describe backend service call with End URL and params.."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/
public interface ISAPService {

    @POST("AuthenticateServletNew")
//    Call<authModel> login(@Header("Content-type") String contentType, @Body BluepageAuth bluepageAuth);
    Call<authModel> login(@Body BluepageAuth bluepageAuth);

    @GET("GetClientsInfoServlet")
    Call<List<ClientsModel>> getClients(@Query("intranetId") String email);

    @GET("GetMyPlan")
//    Call<MyPlanModel> getMyPlan(@o SingletonSession singletonSessionClass);
    Call<MyPlanModel> getMyPlan(@Query("clientId") int clientId,@Query("intranetId") String intranetId,
                                @Query("clientName") String clientName,@Query("geoId") String geoId,
                                @Query("geoKey") String geoKey,@Query("regionId") String regionId,
                                @Query("regionKey") String regionKey,
                                @Query("countryId") String countryId,@Query("countryKey") String countryKey);
    @GET("GetMarketsAndCountry")
//    Call<MyPlanModel> getMyPlan(@Body SingletonSession   singletonSession);
    Call<GeoMyplanModel> getGeoMyPlan(@Query("clientId") int clientId, @Query("geoKey") String geoKey, @Query("geoName") String geoName,
                                      @Query("geoId") String geoId);
    @GET("GetCountry")
    Call<MarketMyPlan> getMarketMyPlan(@Query("clientId") int clientId,@Query("geoId") String geoId, @Query("geoName") String geoName,@Query("marketId") String marketId,@Query("marketName") String marketName,@Query("marketKey") String marketKey );
    @GET("GetMyPlanByCountry")
    Call<CountryMyPlan> getCountryMyPlan(@Query("clientId") int clientId,@Query("geoId") String geoId, @Query("marketId") String marketId,
                                         @Query("countryId") String countryId,@Query("countryKey") String countryKey,@Query("marketKey") String marketKey,
                                         @Query("geoKey") String geoKey);

    @GET("GetGoalsSummary")
    Call<GoalsModel> getMyGoalSummary(@Query("clientId") int clientId, @Query("intranetId") String intranetId,
                                      @Query("clientName") String clientName,
                                      @Query("geoKey") String geoKey,
                                      @Query("regionKey") String regionKey);

    @GET("GetInitiativesSummary")
    Call<InitiativeModel> getInitiativeSummary(@Query("clientId") int clientId,@Query("clientName") String clientName,
                                           @Query("intranetId") String intranetId,
                                           @Query("geoKey") String geoKey,
                                           @Query("regionKey") String regionKey);


    @GET("GetGoalsMore")
    Call<GoalDetails> getGoalsMore(@Query("clientId") int clientId, @Query("clientName") String clientName,
                                   @Query("intranetId") String intranetId,
                                   @Query("goalId") int goalId,
                                   @Query("geoKey") int geoKey,
                                   @Query("regionKey") int regionKey);


    @GET("GetLinkedOpt")
    Call<LinkedOpportunitiesModel> getLinkedOpportunities(@Query("initiativeKey") int initiativeKey,
                                                          @Query("geoKey") int geoKey,
                                                          @Query("regionKey") int regionKey);

    @GET("GetRelationshipServlet")
    Call<RelationshipModel> getRelationshipSummary(@Query("clientId") int clientId,
                                                   @Query("clientName") String clientName, @Query("intranetId") String intranetId
                                                   );
    @GET("GetFirstDetailServlet")
    Call<RelationshipExpandList> getRelationshipExpandList(@Query("clientId") int clientId,
                                                           @Query("clientExecKey") String clientExecKey);
    @GET("GetRelationshipsMoreInfoServlet")
    Call<RelationshipMore> getRelationshipMore(@Query("clientId") int clientId,
                                               @Query("clientName") String clientName, @Query("intranetId") String intranetId,
                                               @Query("relationShip") String relationship
    );
    //MVP2 API for Edit and Delete.
    @GET("GetGoalFieldDetails")
    Call<GOAL_FIELDS> getGoalFields(@Query("clientId") int clientId,@Query("goalId") int goalId);
    @POST("SaveGoalServlet")
    Call<GoalResponseObj> saveGoalData(@Body GoalViewModel goalViewModel);
    @POST("DeleteGoalServlet")
    Call<GoalResponseObj> deleteGoal(@Body GoalDeleteModel goalDeleteModel);
    @GET("GetInitiativeFieldDetails")
    Call<InitiativeFields_Model> getInitiativeFields(@Query("clientId") int clientId, @Query("initiativeId") int initiativeId);
    @GET("user")
    Call<ResponseBody> getProfileInfo(@Query("searchConfig") String searchConfig,@Query("query") String query);
    @POST("GetMarketsAndCountriesByGeos")
    Call<RegionsResponse> getRegions(@Body GetRegionModel getRegionModel);
    @POST("GeoCountriesByRegions")
    Call<GetCountryResponse> getCountries(@Body GetCountryModel getCountryModel);
    @POST("SaveInitiativeServlet")
    Call<GoalResponseObj> saveInitiativeData(@Body InitiativeViewModel initiativeViewModel);
    @POST("DeleteInitiativeServlet")
    Call<GoalResponseObj> deleteInitiative(@Body DeleteInitiativeModel deleteInitiativeModel);

    //Relationship API's
    @GET("GetRelationShipFieldDetails")
    Call<NewRelationshipModel> getRelationshipFields(@Query("clientId") String clientId, @Query("clientExecKey") String clientExecKey);
    @POST("saveRelationshipNew")
    Call<NewRelationship_SaveResponseObj> saveRelationshipData(@Body NewRelationshipRequestModel Rel_ViewModel);
    @POST("DeleteRelationShipServlet")
    Call<GoalResponseObj> deleteRelationship(@Body Relationship_DelObj Rel_DeleteModel);

}
