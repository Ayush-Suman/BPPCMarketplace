package a.suman.bppcmarketplace

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("id_token")
    fun authWithBackend(@Field("token") token: String): Observable<ResponseBody>


}