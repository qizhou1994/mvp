package com.zq.android.api;

import com.zq.android.bean.file.FileObjAckBase;
import com.zq.android.bean.file.ImageFileObj;
import com.zq.android.data.MainEntity;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 17:24
 */
public interface MainApi {
    //    pno	int	否	当前页数，默认1
    //    ps	int	否	每页返回条数，最大100，默认20
    //    key	string	是	应用APPKEY(应用详细页查询)
    //    dtype	string	否	返回数据的格式,xml或json，默认json
    @GET("weixin/query")
    Observable<MainEntity.WeiXinNews> getWeiXinNews(@Query("key") String key, @Query("pno") int pno,
                                                    @Query("ps") int ps, @Query("dtype") String dtype);


    @Multipart
    @POST()
    Observable<FileObjAckBase<ImageFileObj>> uploadFileFastDFS(@Url String newUrl, @Part MultipartBody.Part fileName);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFileFastDFS(@Url String fileUrl);
}
