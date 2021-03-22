package com.spacex.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import java.security.cert.CertificateException
import javax.net.ssl.*


object NetworkClient {
    // private const val BASE_URL = "http://52.71.96.220:5000"
    private const val BASE_URL = "https://api.spacexdata.com/v4"

    private var retrofit: Retrofit? = null

    /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */
    //If condition to ensure we don't create multiple retrofit instances in a single application
    //Defining the Retrofit using Builder
    //This is the only mandatory call on Builder object.
    // Convertor library used to convert response into POJO
    val retrofitClient: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient())
                    .build()
            }
            return retrofit
        }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier { hostname, session -> true }

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}

/*
* public class NetworkClient {
    public static final String BASE_URL = "http://api.openweathermap.org";
    public static Retrofit retrofit;
    /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */
    public static Retrofit getRetrofitClient() {
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit == null) {
            //Defining the Retrofit using Builder
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
                .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
                .build();
        }
        return retrofit;
    }
}
* */