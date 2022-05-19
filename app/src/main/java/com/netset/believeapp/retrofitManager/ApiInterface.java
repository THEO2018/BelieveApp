package com.netset.believeapp.retrofitManager;


import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;

/**
 * Created by Neeraj Narwal on 5/5/17.
 */
public interface ApiInterface {

    // If API call is in multipart reqeust (key-value in general words) then define mutipart request parts here

    //  @POST
    //   Call<LoginResponse> demoAPIParam(@Query("ParamName") String method, @Query("paramName2") String userid);
    // If API call is in type of Data object (RequestBody in general words) then send whole serialized model class in post
//    @POST("/api/fuel/RegisterUser")
//    Call<UserData> signup(@Body SignupData signupData);
    @FormUrlEncoded
    @POST("/api/sign_up")
    Call<JsonObject> Signup(@FieldMap HashMap<String, String> data);

   @FormUrlEncoded
    @POST("/api/create_profile")
    Call<JsonObject> CreateProfile1(@FieldMap HashMap<String, String> data);


    @Multipart
    @POST("/api/create_profile")
    Call<JsonObject> CreateProfile(@PartMap HashMap<String, RequestBody> map);

  /*  @Multipart
    @POST
    Call<JsonObject> multipleImage(@Url String url,
                                   @Part MultipartBody.Part[] surveyImage,
                                   @PartMap HashMap<String, RequestBody> map);
*/
    @FormUrlEncoded
    @POST("/api/login")
    Call<JsonObject> Login(@FieldMap HashMap<String, String> data);

  @FormUrlEncoded
    @POST("/api/get_groups")
    Call<JsonObject> GetGroup(@FieldMap HashMap<String, String> data);

  @FormUrlEncoded
    @POST("/api/detail_group_with_about")
    Call<JsonObject> GroupDetail_About(@FieldMap HashMap<String, String> data);

  @FormUrlEncoded
    @POST("/api/join_group")
    Call<JsonObject> JoinGroup(@FieldMap HashMap<String, String> data);


 @FormUrlEncoded
 @POST("/api/add_group_post")
 Call<JsonObject> AddGroupPost1(@FieldMap HashMap<String, String> data);


 @Multipart
 @POST("/api/add_group_post")
 Call<JsonObject> AddGroupPost(@PartMap HashMap<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/api/add_comment_group_post")
    Call<JsonObject> AddGroupPostComment(@FieldMap HashMap<String, String> data);

    @Multipart
    @POST("/api/add_comment_group_post")
    Call<JsonObject> AddGroupPostComment2(@PartMap HashMap<String, RequestBody> map);

 @FormUrlEncoded
    @POST("/api/add_like_group_post")
    Call<JsonObject> AddGroupPostLike(@FieldMap HashMap<String, String> data);

   @FormUrlEncoded
    @POST("/api/detail_group_discussion")
    Call<JsonObject> GroupDetail_Discussion(@FieldMap HashMap<String, String> data);

    @Multipart
    @POST("/api/add_photo_to_group")
    Call<JsonObject> AddGroup_Photo(@PartMap HashMap<String, RequestBody> map);

 @FormUrlEncoded
    @POST("/api/get_group_photos")
    Call<JsonObject> GetGroup_Photos(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_events_of_group")
    Call<JsonObject> GetGroup_Events(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_group_post_detail")
    Call<JsonObject> GetGroupPost_Detail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_small_groups")
    Call<JsonObject> GetSmallGroups(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/search_small_groups")
    Call<JsonObject> GetSmallGroupsSearch(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/detail_small_group")
    Call<JsonObject> GetSmallGroups_Detail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/join_small_group")
    Call<JsonObject> JoinSmallGroups(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/add_wall_post")
    Call<JsonObject> AddWallPost1(@FieldMap HashMap<String, String> data);


    @Multipart
    @POST("/api/add_wall_post")
    Call<JsonObject> AddWallPost(@PartMap HashMap<String, RequestBody> map);


    @FormUrlEncoded
    @POST("/api/add_comment_wall_post")
    Call<JsonObject> AddWallPost_Comment(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/add_like_wall_post")
    Call<JsonObject> WallPost_Like(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_wall_posts")
    Call<JsonObject> GetWallPost(@FieldMap HashMap<String, String> data);

    @POST("/api/get_wall_post_detail")
    Call<JsonObject> GetWallPost_Detail(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("/api/logout")
    Call<JsonObject> Logout(@FieldMap HashMap<String, String> data);



    @FormUrlEncoded
    @POST("/api/remove_account")
    Call<JsonObject> RemoveAccount(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_my_profile")
    Call<JsonObject> GetMyProfile(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/edit_profile")
    Call<JsonObject> Edit_MyProfile(@FieldMap HashMap<String, String> data);


    @Multipart
    @POST("/api/edit_profile")
    Call<JsonObject> Edit_MyProfile2(@PartMap HashMap<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/api/get_other_profile")
    Call<JsonObject> GetOtherProfile(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/change_password")
    Call<JsonObject> Change_Password(@FieldMap HashMap<String, String> data);



    @FormUrlEncoded
    @POST("/api/get_marriages")
    Call<JsonObject> GetMarriages(@FieldMap HashMap<String, String> data);

   /* @FormUrlEncoded
    @POST("/api/get_users")
    Call<JsonObject> GetUsers(@FieldMap HashMap<String, String> data);
*/
    @FormUrlEncoded
    @POST("/api/get_users_for_engagement")
    Call<JsonObject> GetUsers(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/search_users")
    Call<JsonObject> SearchUser(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_other_users")
    Call<JsonObject> GetOtherUsers(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/add_betrothed")
    Call<JsonObject> Add_Betrothed(@FieldMap HashMap<String, String> data);




    @FormUrlEncoded
    @POST("/api/get_alumni")
    Call<JsonObject> Get_Alumni(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_birthdays")
    Call<JsonObject> Get_Birthdays(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_new_members")
    Call<JsonObject> GetNew_Members(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_events")
    Call<JsonObject> GetEvents(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_event_detail")
    Call<JsonObject> GetEvents_Detail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/recently_added_music")
    Call<JsonObject> Get_RecentList(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_music_albums")
    Call<JsonObject> Get_MusicAlbums(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_songs_album")
    Call<JsonObject> Get_SongsAlbums(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_all_songs")
    Call<JsonObject> Get_AllSongs(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_albums_of_artist")
    Call<JsonObject> Get_ArtistsAlbum(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_artists")
    Call<JsonObject> Get_Artists(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_all_categories")
    Call<JsonObject> Get_Categories(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_album_of_category")
    Call<JsonObject> Get_AllGallaries(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_media_of_gallary")
    Call<JsonObject> Get_MediaOfGallary(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/make_appointment_request")
    Call<JsonObject> MakeAppointment_Request(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_today_appointments")
    Call<JsonObject> GetToday_Appointments(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_upcoming_appointments")
    Call<JsonObject> Upcoming_Appointments(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_all_blogs")
    Call<JsonObject> GetAllBlogs(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_blog_detail")
    Call<JsonObject> GetBlogDetail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_classifieds_categories")
    Call<JsonObject> Get_Classifieds_Categories(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_classifieds_of_category")
    Call<JsonObject> Get_Classifieds_Of_Categories(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_classified_detail")
    Call<JsonObject> Get_ClassifiedDetail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/ask_for_recommendation")
    Call<JsonObject> AskFor_Recommendation(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/add_comment_poll")
    Call<JsonObject> add_CommentPoll(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/add_vote_poll")
    Call<JsonObject> add_VotePoll(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_active_polls")
    Call<JsonObject> Get_ActivePolls(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_poll_detail")
    Call<JsonObject> Get_PollDetail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_closed_polls")
    Call<JsonObject> Get_ClosedPolls(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_contact_detail")
    Call<JsonObject> Get_Contacts_Detail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_service_hours")
    Call<JsonObject> Get_ServiceTime(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_about_pages")
    Call<JsonObject> Get_aboutUs(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_about_detail")
    Call<JsonObject> Get_About_Detail(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_news_urls")
    Call<JsonObject> Get_News(@FieldMap HashMap<String, String> data);

   @FormUrlEncoded
   @POST("/api/get_store_urls")
   Call<JsonObject> Get_Store(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_bible")
    Call<JsonObject> Get_Bible(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
   @POST("/api/add_note")
    Call<JsonObject> Add_Note(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_notes")
    Call<JsonObject> Get_Notes(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @PUT("/api/update_note")
    Call<JsonObject> update_note(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/ask_for_recommendation")
    Call<JsonObject> ask_for_recommendation(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/check_if_recommendation_exist")
    Call<JsonObject> check_if_recommendation_exist(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_list_of_recommended_people")
    Call<JsonObject> get_list_of_recommended_people(@FieldMap HashMap<String, String> data);



    @FormUrlEncoded
    @POST("/api/get_giving_section")
    Call<JsonObject> Get_DonationList(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_prayer_categories")
    Call<JsonObject> Get_PrayerCategory(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/add_prayer")
    Call<JsonObject> Add_Prayer(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/add_pray_to_prayer")
    Call<JsonObject> AddPrayer_toPrayer(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/add_comment_prayer")
    Call<JsonObject> AddComment_toPrayer(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/answer_a_prayer")
    Call<JsonObject> Answer_Prayer(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_prayer_categories")
    Call<JsonObject> GetPrayer_Category(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_prayers")
    Call<JsonObject> Get_Prayers(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_detail_of_prayer")
    Call<JsonObject> Get_Prayers_Detail(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/get_group_notification_settings")
    Call<JsonObject> Get_NotificationGroup(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/notification_settings")
    Call<JsonObject> Get_GroupNotifyChange(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/change_sound_status")
    Call<JsonObject> Get_NotifySound(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("/api/forgot_password")
    Call<JsonObject> Forgot_Password(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("/api/get_social_content")
    Call<JsonObject> GetSocialContent(@FieldMap HashMap<String, String> data);

}
