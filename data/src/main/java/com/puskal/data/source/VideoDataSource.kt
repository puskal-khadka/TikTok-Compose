package com.puskal.data.source

import com.puskal.data.model.VideoModel
import com.puskal.data.source.UsersDataSource.charliePuth
import com.puskal.data.source.UsersDataSource.daniel
import com.puskal.data.source.UsersDataSource.duaLipa
import com.puskal.data.source.UsersDataSource.google
import com.puskal.data.source.UsersDataSource.imagineDragons
import com.puskal.data.source.UsersDataSource.jeremy
import com.puskal.data.source.UsersDataSource.kylieJenner
import com.puskal.data.source.UsersDataSource.shana
import com.puskal.data.source.UsersDataSource.taylor
import com.puskal.data.source.UsersDataSource.zoya
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Puskal Khadka on 3/18/2023.
 */
object VideoDataSource {

    object KylieJennerVideos {
        val kylie_vid1 = VideoModel(
            videoId = "kylie_vid1",
            authorDetails = kylieJenner,
            videoLink = "kylie_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 409876,
                comment = 8356,
                share = 3000,
                favourite = 1500
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid2 = VideoModel(
            videoId = "kylie_vid2",
            authorDetails = kylieJenner,
            videoLink = "kylie_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 564572,
                comment = 8790,
                share = 2000,
                favourite = 1546
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid3 = VideoModel(
            videoId = "kylie_vid3",
            authorDetails = kylieJenner,
            videoLink = "kylie_vid3.mp4",
            videoStats = VideoModel.VideoStats(
                like = 2415164,
                comment = 5145,
                share = 5000,
                favourite = 2000
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid4 = VideoModel(
            videoId = "kylie_vid4",
            authorDetails = kylieJenner,
            videoLink = "kylie_vid4.mp4",
            videoStats = VideoModel.VideoStats(
                like = 51626,
                comment = 1434,
                share = 167,
                favourite = 633
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid5 = VideoModel(
            videoId = "kylie_vid5",
            authorDetails = kylieJenner,
            videoLink = "kylie_vid5.mp4",
            videoStats = VideoModel.VideoStats(
                like = 547819,
                comment = 79131,
                share = 8921,
                favourite = 2901
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid6 = VideoModel(
            videoId = "kylie_vid6",
            authorDetails = kylieJenner,
            videoLink = "kylie_vid6.mp4",
            videoStats = VideoModel.VideoStats(
                like = 4512340,
                comment = 65901,
                share = 8165,
                favourite = 154
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val kylie_vid7 = VideoModel(
            videoId = "kylie_vid7",
            authorDetails = kylieJenner,
            videoLink = "kylie_vid7.mp4",
            videoStats = VideoModel.VideoStats(
                like = 612907,
                comment = 7643,
                share = 1291,
                favourite = 890
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val kylieVideosList = listOf(
            kylie_vid1,
            kylie_vid2,
            kylie_vid3,
            kylie_vid4,
            kylie_vid5,
            kylie_vid6,
            kylie_vid7
        )
    }

    object CharliePuthVideos {
        val charlieputh_vid1 = VideoModel(
            videoId = "charlieputh_vid1",
            authorDetails = charliePuth,
            videoLink = "charlieputh_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 2534000,
                comment = 34039,
                share = 3434,
                favourite = 53244
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val charlieputh_vid2 = VideoModel(
            videoId = "charlieputh_vid2",
            authorDetails = charliePuth,
            videoLink = "charlieputh_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 233253,
                comment = 3524,
                share = 2000,
                favourite = 3000
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val charlieputh_vid3 = VideoModel(
            videoId = "charlieputh_vid3",
            authorDetails = charliePuth,
            videoLink = "charlieputh_vid3.mp4",
            videoStats = VideoModel.VideoStats(
                like = 766383,
                comment = 2000,
                share = 9000,
                favourite = 5483
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )


        val charliePuthVideos =
            arrayListOf(charlieputh_vid1, charlieputh_vid2, charlieputh_vid3)
    }

    object ImagineDragonsVideos {
        val imaginedragons_vid1 = VideoModel(
            videoId = "imaginedragons_vid1",
            authorDetails = imagineDragons,
            videoLink = "imaginedragons_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 878797,
                comment = 7873,
                share = 34834,
                favourite = 3434
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val imaginedragons_vid2 = VideoModel(
            videoId = "imaginedragons_vid2",
            authorDetails = imagineDragons,
            videoLink = "imaginedragons_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 876540,
                comment = 17654,
                share = 2390,
                favourite = 134
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val imageDragonsVideosList = arrayListOf(imaginedragons_vid1, imaginedragons_vid2)

    }


    object TaylorVideos {
        val taylor_vid1 = VideoModel(
            videoId = "taylor_vid1",
            authorDetails = taylor,
            videoLink = "taylor_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 2353,
                comment = 84748,
                share = 4848,
                favourite = 4838
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val taylor_vid2 = VideoModel(
            videoId = "taylor_vid2",
            authorDetails = taylor,
            videoLink = "taylor_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 342,
                comment = 120,
                share = 20,
                favourite = 10
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )


        val taylorVideos = arrayListOf(taylor_vid1, taylor_vid2)
    }

    object DuaLipaVideos {
        val dua_vid1 = VideoModel(
            videoId = "dua_vid1",
            authorDetails = duaLipa,
            videoLink = "dua_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 434567,
                comment = 3234,
                share = 235,
                favourite = 43
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val dua_vid2 = VideoModel(
            videoId = "dua_vid2",
            authorDetails = duaLipa,
            videoLink = "dua_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 249834,
                comment = 98709,
                share = 9009,
                favourite = 898
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val dua_vid3 = VideoModel(
            videoId = "dua_vid3",
            authorDetails = duaLipa,
            videoLink = "dua_vid3.mp4",
            videoStats = VideoModel.VideoStats(
                like = 998798,
                comment = 3409,
                share = 239,
                favourite = 89
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val dualipaVideos = arrayListOf(dua_vid1, dua_vid2, dua_vid3)

    }

    object GoogleVideos {
        val google_vid1 = VideoModel(
            videoId = "google_vid1",
            authorDetails = google,
            videoLink = "google_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 3423,
                comment = 234,
                share = 45,
                favourite = 23
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val google_vid2 = VideoModel(
            videoId = "google_vid2",
            authorDetails = google,
            videoLink = "google_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 2352,
                comment = 23,
                share = 235,
                favourite = 234
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val googleVideosList = arrayListOf(google_vid1, google_vid2)
    }

    object ShanaVideos {
        val shana_vid1 = VideoModel(
            videoId = "shana_vid1",
            authorDetails = shana,
            videoLink = "shana_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 353204,
                comment = 463,
                share = 64,
                favourite = 35
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val shana_vid2 = VideoModel(
            videoId = "shana_vid2",
            authorDetails = shana,
            videoLink = "shana_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 35242,
                comment = 4632,
                share = 362,
                favourite = 3000
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val shana_vid3 = VideoModel(
            videoId = "shana_vid3",
            authorDetails = shana,
            videoLink = "shana_vid3.mp4",
            videoStats = VideoModel.VideoStats(
                like = 36206,
                comment = 2642,
                share = 64,
                favourite = 264
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val shana_vid4 = VideoModel(
            videoId = "shana_vid4",
            authorDetails = shana,
            videoLink = "shana_vid4.mp4",
            videoStats = VideoModel.VideoStats(
                like = 4091230,
                comment = 74323,
                share = 1293,
                favourite = 546
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val shana_vid5 = VideoModel(
            videoId = "shana_vid5",
            authorDetails = shana,
            videoLink = "shana_vid5.mp4",
            videoStats = VideoModel.VideoStats(
                like = 871290,
                comment = 9123,
                share = 349,
                favourite = 172
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )



        val shanVideosList = arrayListOf(shana_vid1, shana_vid2, shana_vid3, shana_vid4, shana_vid5)
    }

    object JeremyVideos {
        val jeremy_vid1 = VideoModel(
            videoId = "jeremy_vid1",
            authorDetails = jeremy,
            videoLink = "jeremy_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 64172,
                comment = 7245,
                share = 46,
                favourite = 754
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val jeremy_vid2 = VideoModel(
            videoId = "jeremy_vid2",
            authorDetails = jeremy,
            videoLink = "jeremy_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 818301,
                comment = 7245,
                share = 46,
                favourite = 754
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val jeremy_vid3 = VideoModel(
            videoId = "jeremy_vid3",
            authorDetails = jeremy,
            videoLink = "jeremy_vid3.mp4",
            videoStats = VideoModel.VideoStats(
                like = 98172,
                comment = 56245,
                share = 46,
                favourite = 754
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val jeremy_vid4 = VideoModel(
            videoId = "jeremy_vid4",
            authorDetails = jeremy,
            videoLink = "jeremy_vid4.mp4",
            videoStats = VideoModel.VideoStats(
                like = 5678,
                comment = 1023,
                share = 87,
                favourite = 12
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )


        val jeremeyVideosList =
            arrayListOf(jeremy_vid1, jeremy_vid2, jeremy_vid3, jeremy_vid4)

    }

    object DanielVideos {
        val daniel_vid1 = VideoModel(
            videoId = "daniel_vid1",
            authorDetails = daniel,
            videoLink = "daniel_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 451,
                comment = 25,
                share = 35,
                favourite = 35
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val daniel_vid2 = VideoModel(
            videoId = "daniel_vid2",
            authorDetails = daniel,
            videoLink = "daniel_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 76865,
                comment = 8754,
                share = 567,
                favourite = 123
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val daniel_vid3 = VideoModel(
            videoId = "daniel_vid3",
            authorDetails = daniel,
            videoLink = "daniel_vid3.mp4",
            videoStats = VideoModel.VideoStats(
                like = 890765,
                comment = 9807,
                share = 321,
                favourite = 123
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val daniel_vid4 = VideoModel(
            videoId = "daniel_vid4",
            authorDetails = daniel,
            videoLink = "daniel_vid4.mp4",
            videoStats = VideoModel.VideoStats(
                like = 347328,
                comment = 343342,
                share = 251,
                favourite = 831
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val daniel_vid5 = VideoModel(
            videoId = "daniel_vid5",
            authorDetails = daniel,
            videoLink = "daniel_vid5.mp4",
            videoStats = VideoModel.VideoStats(
                like = 971413,
                comment = 98751,
                share = 8201,
                favourite = 1239
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val daniel_vid6 = VideoModel(
            videoId = "daniel_vid6",
            authorDetails = daniel,
            videoLink = "daniel_vid6.mp4",
            videoStats = VideoModel.VideoStats(
                like = 3240193,
                comment = 18101,
                share = 8140,
                favourite = 7382
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val daniel_vid7 = VideoModel(
            videoId = "daniel_vid7",
            authorDetails = daniel,
            videoLink = "daniel_vid7.mp4",
            videoStats = VideoModel.VideoStats(
                like = 765490,
                comment = 87510,
                share = 7876,
                favourite = 2012
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )



        val danielVideosList = arrayListOf(
            daniel_vid1,
            daniel_vid2,
            daniel_vid3,
            daniel_vid4,
            daniel_vid5,
            daniel_vid6,
            daniel_vid7,
        )

    }

    object ZoyaVideos {
        val zoya_vid1 = VideoModel(
            videoId = "zoya_vid1",
            authorDetails = zoya,
            videoLink = "zoya_vid1.mp4",
            videoStats = VideoModel.VideoStats(
                like = 94201,
                comment = 362,
                share = 54,
                favourite = 626
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val zoya_vid2 = VideoModel(
            videoId = "zoya_vid2",
            authorDetails = zoya,
            videoLink = "zoya_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 624524,
                comment = 2577,
                share = 98,
                favourite = 78
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val zoya_vid3 = VideoModel(
            videoId = "zoya_vid3",
            authorDetails = zoya,
            videoLink = "zoya_vid3.mp4",
            videoStats = VideoModel.VideoStats(
                like = 563463,
                comment = 4297,
                share = 2113,
                favourite = 1431
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val zoya_vid4 = VideoModel(
            videoId = "zoya_vid4",
            authorDetails = zoya,
            videoLink = "zoya_vid4.mp4",
            videoStats = VideoModel.VideoStats(
                like = 789349,
                comment = 2577,
                share = 797,
                favourite = 13
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val zoya_vid5 = VideoModel(
            videoId = "zoya_vid5",
            authorDetails = zoya,
            videoLink = "zoya_vid5.mp4",
            videoStats = VideoModel.VideoStats(
                like = 682482,
                comment = 7938,
                share = 9821,
                favourite = 78
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val zoya_vid6 = VideoModel(
            videoId = "zoya_vid6",
            authorDetails = zoya,
            videoLink = "zoya_vid6.mp4",
            videoStats = VideoModel.VideoStats(
                like = 45172,
                comment = 1987,
                share = 987,
                favourite = 102
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )


        val zoyaVideosList =
            arrayListOf(zoya_vid1, zoya_vid2, zoya_vid3, zoya_vid4, zoya_vid5, zoya_vid6)

    }


    val videosList = arrayListOf<VideoModel>().apply {
        addAll(KylieJennerVideos.kylieVideosList)
        addAll(CharliePuthVideos.charliePuthVideos)
        addAll(ImagineDragonsVideos.imageDragonsVideosList)
        addAll(DuaLipaVideos.dualipaVideos)
        addAll(GoogleVideos.googleVideosList)
        addAll(DanielVideos.danielVideosList)
        addAll(ShanaVideos.shanVideosList)
        addAll(TaylorVideos.taylorVideos)
        addAll(JeremyVideos.jeremeyVideosList)
        addAll(ZoyaVideos.zoyaVideosList)
    }

    fun fetchVideos(): Flow<List<VideoModel>> {
        return flow {
            emit(videosList.shuffled())
        }
    }

    fun fetchVideosOfParticularUser(userId: Long): Flow<List<VideoModel>> {
        return flow {
            val userVideoList = videosList.filter { it.authorDetails.userId == userId }
            emit(userVideoList)
        }
    }
}