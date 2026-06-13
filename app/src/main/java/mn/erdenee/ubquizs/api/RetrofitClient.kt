package mn.erdenee.ubquizs.api

import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import mn.erdenee.ubquizs.LocalStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.6.144:3000/api/"
    private var retrofit: Retrofit? = null

    private fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            val localStore = LocalStore(context.applicationContext)

            val authInterceptor = Interceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()

                val token = runBlocking {
                    localStore.token.first()
                }

                if (!token.isNullOrBlank()) {
                    requestBuilder.header("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    // MainActivity-ээс дуудагдах функц
    fun apiService(context: Context): APIService {
        return getClient(context).create(APIService::class.java)
    }
}

