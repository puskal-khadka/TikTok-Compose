package com.puskal.data.source

import com.puskal.data.model.SocialMediaType
import com.puskal.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Puskal Khadka on 3/18/2023.
 */
object UsersDataSource {
    val kylieJenner = UserModel(
        userId = 1,
        uniqueUserName = "keyliejenner",
        fullName = "Kylie jenner",
        following = 23,
        followers = 52000000,
        likes = 1100000000,
        bio = "Kylie",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/887/659/808/kylie-jenner-2018-wallpaper-preview.jpg",
        isVerified = true,
        pinSocialMedia = UserModel.SocialMedia(
            type = SocialMediaType.YOUTUBE,
            link = "https://www.youtube.com/@kyliejenner"
        )
    )

    val charliePuth = UserModel(
        userId = 2,
        uniqueUserName = "charlieputh",
        fullName = "Charlie Puth",
        following = 35,
        followers = 21000000,
        likes = 45400000,
        bio = "All about music",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/240/757/317/singers-charlie-puth-wallpaper-preview.jpg",
        isVerified = true,
        pinSocialMedia = UserModel.SocialMedia(
            type = SocialMediaType.INSTAGRAM,
            link = "https://www.instagram.com/charlieputh"
        )
    )

    val taylor = UserModel(
        userId = 3,
        uniqueUserName = "taylor001",
        fullName = "Taylor collins",
        following = 1200,
        followers = 21000,
        likes = 32000,
        bio = "Joe bio ",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/328/810/463/digital-digital-art-artwork-illustration-simple-hd-wallpaper-preview.jpg",
        isVerified = false,
    )

    val duaLipa = UserModel(
        userId = 4,
        uniqueUserName = "dualipaoffical",
        fullName = "Dua Lipa",
        following = 23,
        followers = 87000000,
        likes = 3300000000,
        bio = "Dua Lipa",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/702/297/121/dua-lipa-2018-4k-wallpaper-preview.jpg",
        isVerified = true,
        pinSocialMedia = UserModel.SocialMedia(
            type = SocialMediaType.YOUTUBE,
            link = "https://www.youtube.com/@dualipa/"
        )
    )

    val imagineDragons = UserModel(
        userId = 5,
        uniqueUserName = "imageinedragons",
        fullName = "Imagine Dragons",
        following = 400,
        followers = 32000000,
        likes = 4300000000,
        bio = "image dragon",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/516/72/957/imagine-dragons-4k-pc-full-hd-wallpaper-preview.jpg",
        isVerified = true,
        pinSocialMedia = UserModel.SocialMedia(
            type = SocialMediaType.YOUTUBE,
            link = "https://www.youtube.com/@ImagineDragons"
        )
    )

    val google = UserModel(
        userId = 6,
        uniqueUserName = "google",
        fullName = "Google",
        following = 300,
        followers = 8970000,
        likes = 54000000,
        bio = "google",
        profilePic = "https://cdn1.iconfinder.com/data/icons/google-s-logo/150/Google_Icons-09-512.png",
        isVerified = true,
    )

    val daniel = UserModel(
        userId = 7,
        uniqueUserName = "daniel",
        fullName = "Daniel Paul",
        following = 1400,
        followers = 1200,
        likes = 14000,
        bio = "daniel",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/429/125/144/shingeki-no-kyojin-levi-erwin-smith-sword-wallpaper-preview.jpg",
        isVerified = false,
    )

    val shana = UserModel(
        userId = 8,
        uniqueUserName = "its_shana",
        fullName = "Shana",
        following = 400,
        followers = 9870008,
        likes = 98700000,
        bio = "shana",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/178/256/912/face-blue-eyes-women-model-wallpaper-preview.jpg",
        isVerified = false,
    )
    val jeremy = UserModel(
        userId = 9,
        uniqueUserName = "jeremy2000",
        fullName = "Jeremy",
        following = 1200,
        followers = 800000,
        likes = 3900000,
        bio = "jeremy",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/549/801/215/linkin-park-mike-shinoda-chester-bennington-photo-wallpaper-preview.jpg",
        isVerified = false,
    )

    val zoya = UserModel(
        userId = 10,
        uniqueUserName = "me_zoya",
        fullName = "Zoya miles",
        following = 200,
        followers = 40000,
        likes = 670000,
        bio = "zoya",
        profilePic = "https://c0.wallpaperflare.com/preview/781/436/846/saipan-northern-mariana-islands-island-ocean.jpg",
        isVerified = false,
    )

    val userList = listOf(
        kylieJenner,
        charliePuth,
        taylor,
        duaLipa,
        imagineDragons,
        google,
        daniel,
        shana,
        jeremy,
        zoya
    )

    fun fetchSpecificUser(userId: Long): Flow<UserModel?> {
        return flow {
            val user = userList.firstOrNull { it.userId == userId }
            emit(user)
        }
    }

}


