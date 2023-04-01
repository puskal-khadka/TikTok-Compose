package com.puskal.data.source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.puskal.data.model.CommentList
import com.puskal.data.model.CommentText
import com.puskal.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

/**
 * Created by Puskal Khadka on 3/21/2023.
 */
object CommentDataSource {
    private val bio = arrayListOf("Life is beautiful")
    private val isVerified = listOf(true, false)
    private const val randomProfilePic = "https://xsgames.co/randomusers/avatar.php"
    private val gender = arrayListOf("male", "female")


    private val followers = 30L..450000L
    private val likesInComment = 0L..500L


    private val commentList = Gson().fromJson<List<CommentText>>(
        commentJson,
        object : TypeToken<List<CommentText>>() {}.type
    )


    fun fetchComment(videoId: String): Flow<CommentList> {
        return flow {
            val comments =
                commentList.take((20..commentList.size).random()).run {
                    CommentList(
                        videoId = videoId,
                        totalComment = this.size,
                        comments = this.shuffled().map {
                            CommentList.Comment(
                                commentBy = UserModel(
                                    userId = System.currentTimeMillis(),
                                    fullName = userName.random(),
                                    uniqueUserName = userName.random(),
                                    following = followers.random(),
                                    followers = followers.random(),
                                    likes = followers.random(),
                                    bio = bio.random(),
                                    profilePic = "$randomProfilePic?g=${gender.random()}&id=${
                                        UUID.randomUUID().toString().take(4)
                                    }",
                                    isVerified = isVerified.random(),
                                ),
                                comment = it.comment,
                                createdAt = "${(1..23).random()}h",
                                totalLike = likesInComment.random(),
                                totalDisLike = 0,
                                threadCount = 0,
                                thread = listOf()
                            )
                        },
                        isCommentPrivate = false
                    )


                }
            emit(comments)

        }


    }
}


private val commentJson = """
           [
          {
            "comment": "This is amazing! \uD83D\uDE0D"
          },
          {
            "comment": "I can't stop watching! \uD83D\uDC40"
          },
          {
            "comment": "This video is straight \uD83D\uDD25 fire! \uD83D\uDD25"
          },
          {
            "comment": "You're so talented! \uD83D\uDE0E"
          },
          {
            "comment": "This made my day! \uD83D\uDE0A"
          },
          {
            "comment": "I can't believe how creative you are! \uD83C\uDFA8"
          },
          {
            "comment": "You just won TikTok with this video! \uD83D\uDC4F"
          },
          {
            "comment": "This is hilarious! \uD83D\uDE02"
          },
          {
            "comment": "You're a true artist! \uD83D\uDC4D"
          },
          {
            "comment": "I love this so much! \uD83D\uDE0D"
          },
          {
            "comment": "You have so much talent! \uD83D\uDC4F"
          },
          {
            "comment": "This is too cute for words! \uD83D\uDE0D"
          },
          {
            "comment": "I'm in awe of your skills! \uD83E\uDD29"
          },
          {
            "comment": "This video is insane! \uD83D\uDE31"
          },
          {
            "comment": "I'm so impressed! \uD83D\uDE0E"
          },
          {
            "comment": "You're a genius! \uD83D\uDE4C"
          },
          {
            "comment": "This is the best thing I've seen all day! \uD83D\uDC4C"
          },
          {
            "comment": "You're so talented, keep it up! \u2728"
          },
          {
            "comment": "This video is so cool! \uD83D\uDE0E"
          },
          {
            "comment": "I'm obsessed with this video! \uD83D\uDE0D"
          },
          {
            "comment": "You just made my day! \uD83D\uDE0A"
          },
          {
            "comment": "This video is legendary! \uD83D\uDE0E"
          },
          {
            "comment": "You're a true inspiration! \uD83D\uDC4F"
          },
          {
            "comment": "This is incredible! \uD83E\uDD29"
          },
          {
            "comment": "You're so talented, it's ridiculous! \uD83D\uDE4C"
          },
          {
            "comment": "This video just made me so happy! \uD83D\uDE0A"
          },
          {
            "comment": "You're a genius, keep it up! \uD83D\uDE4C"
          },
          {
            "comment": "This video is the highlight of my day! \uD83C\uDF1E"
          },
          {
            "comment": "You're amazing! \uD83D\uDC4F"
          },
          {
            "comment": "This is so creative! \uD83C\uDFA8"
          },
          {
            "comment": "You're a natural! \\uD83"
          },
          {
            "comment": "This is amazing! \uD83E\uDD29"
          },
          {
            "comment": "I can't stop watching this! \uD83D\uDE0D"
          },
          {
            "comment": "This video is straight \uD83D\uDD25 fire!"
          },
          {
            "comment": "I'm in love with this video! \uD83D\uDE0D"
          },
          {
            "comment": "You're such a talented creator! \uD83D\uDE4C"
          },
          {
            "comment": "This is the best video I've seen all day! \uD83D\uDC4C"
          },
          {
            "comment": "You have so much creativity, keep it up! \uD83C\uDFA8"
          },
          {
            "comment": "This is too cute for words! \uD83D\uDE0A"
          },
          {
            "comment": "This video just made my day! \uD83D\uDE0D"
          },
          {
            "comment": "You just won TikTok with this amazing video! \uD83D\uDCAF"
          },
          {
            "comment": "This is the most creative video I've seen in a while! \uD83E\uDD2F"
          },
          {
            "comment": "I'm so impressed by your talent! \uD83D\uDE0D"
          },
          {
            "comment": "This video is hilarious! \uD83D\uDE02"
          },
          {
            "comment": "You're such an inspiration! \uD83D\uDE4C"
          },
          {
            "comment": "This video is too funny! \uD83D\uDE02"
          },
          {
            "comment": "I can't believe how much this video made me laugh! \uD83D\uDE02"
          },
          {
            "comment": "Your creativity is out of this world! \uD83D\uDC4F"
          },
          {
            "comment": "You're such a great creator, keep it up! \uD83D\uDCAF"
          },
          {
            "comment": "This is pure genius, you're a true artist! \uD83D\uDC4D"
          },
          {
            "comment": "Wow, you're amazing! \uD83D\uDE0D"
          },
          {
            "comment": "I can't stop replaying this video! \uD83E\uDD24"
          },
          {
            "comment": "This video is the highlight of my day! \uD83C\uDF1E"
          },
          {
            "comment": "You have so much talent, keep it up! \uD83D\uDE4C"
          },
          {
            "comment": "I'm literally in tears from laughing so hard! \uD83D\uDE02"
          },
          {
            "comment": "This is the most amazing video I've seen in a while! \uD83D\uDE4F"
          },
          {
            "comment": "You just made my day with this video! \uD83D\uDE0D"
          },
          {
            "comment": "I'm in awe of your talent! \uD83D\uDE0D"
          },
          {
            "comment": "You just gained a new follower, your content is amazing! \uD83D\uDE4F"
          },
          {
            "comment": "This video just made me so happy"
          },
          {
            "comment": "This video is \u2705 awesome! Keep up the great work!"
          },
          {
            "comment": "OMG, this is hilarious! \uD83D\uDE02"
          },
          {
            "comment": "I'm obsessed with this video! \uD83D\uDE0D"
          },
          {
            "comment": "You're so talented! \uD83D\uDC4F"
          },
          {
            "comment": "This video made my day! \uD83D\uDE0A"
          },
          {
            "comment": "I can't stop watching this! \uD83E\uDD24"
          },
          {
            "comment": "Your creativity is \u2B50 out of this world!"
          },
          {
            "comment": "This is pure genius! \uD83D\uDC4D"
          },
          {
            "comment": "You just won TikTok with this amazing video! \uD83D\uDCAF"
          },
          {
            "comment": "I can't believe how much this video made me laugh! \uD83E\uDD23"
          },
          {
            "comment": "This is the best video I've seen all day! \uD83D\uDC4C"
          },
          {
            "comment": "You are such an inspiration! \uD83D\uDE4C"
          },
          {
            "comment": "I'm in awe of your talent! \uD83D\uDE0D"
          },
          {
            "comment": "This video is too cute for words! \uD83D\uDE0D"
          },
          {
            "comment": "You have so much talent, keep it up! \uD83D\uDC4D"
          },
          {
            "comment": "I'm literally in tears from laughing so hard! \uD83D\uDE02"
          },
          {
            "comment": "This video just made me so happy! \uD83D\uDE0A"
          },
          {
            "comment": "You just gained a new follower, your content is amazing! \uD83D\uDE4F"
          },
          {
            "comment": "My mind is officially blown by this incredible video! \uD83E\uDD2F"
          },
          {
            "comment": "I can't wait to see what you come up with next! \uD83D\uDE0E"
          },
          {
            "comment": "This is the most creative video I've seen in a while! \uD83C\uDFA8"
          },
          {
            "comment": "You're a true artist! \uD83D\uDC4F"
          },
          {
            "comment": "This video is the highlight of my day! \uD83C\uDF1E"
          },
          {
            "comment": "You are so talented, keep shining like a \u2600\uFE0F!"
          },
          {
            "comment": "This video is straight \uD83D\uDD25 fire!"
          },
          {
            "comment": "You're amazing, never stop creating! \uD83D\uDE4C"
          },
          {
            "comment": "I'm so impressed by your talent! \uD83D\uDE0D"
          },
          {
            "comment": "I can't get enough of this video! \uD83D\uDE0A"
          },
          {
            "comment": "You just made my day with this video!"
          },
          {
            "text": "Wow, this video just made my day! \ud83d\ude0d"
          },
          {
            "text": "This is so creative, I'm blown away! \ud83d\udc4f"
          },
          {
            "text": "I can't stop watching this, it's mesmerizing! \ud83d\ude32"
          },
          {
            "text": "You have serious talent, keep it up! \ud83d\udc4d"
          },
          {
            "text": "I've never seen anything like this before, it's amazing! \ud83d\ude2e"
          },
          {
            "text": "This is the best thing I've seen all day, thank you for sharing! \ud83d\ude0a"
          },
          {
            "text": "You're a natural on camera, I could watch you all day! \ud83c\udfa5"
          },
          {
            "text": "I'm definitely trying this out, thanks for the inspiration! \ud83d\udc4a"
          },
          {
            "text": "You have a great sense of humor, this made me laugh so hard! \ud83e\udd23"
          },
          {
            "text": "I'm speechless, you're so talented! \ud83e\udd29"
          },
          {
            "text": "This is pure art, I'm in awe! \ud83c\udfa8"
          },
          {
            "text": "This video deserves all the likes, it's incredible! \u2764\ufe0f"
          },
          {
            "text": "You have such a positive energy, it's contagious! \ud83c\udf1f"
          },
          {
            "text": "I'm obsessed with this, I can't stop watching! \ud83e\udd2f"
          },
          {
            "text": "You're a genius, keep up the amazing work! \ud83e\udd13"
          },
          {
            "text": "I feel so inspired after watching this, thank you! \u2728"
          },
          {
            "text": "I'm blown away by your talent, keep shining! \u2728"
          },
          {
            "text": "This is the kind of content we need more of, thank you! \ud83d\udc4f\ud83c\udffb"
          },
          {
            "text": "This is so wholesome, it made my day! \ud83d\ude0b"
          },
          {
            "text": "I've watched this so many times, it's addicting! \ud83e\udd29"
          },
          {
            "text": "This is amazing! I can't believe how talented you are! \u2728"
          },
          {
            "text": "I just found my new favorite TikToker! \ud83d\ude0d"
          },
          {
            "text": "You make everything look so easy! \ud83d\udc4f"
          },
          {
            "text": "This is the kind of content that deserves to go viral! \ud83d\udc4f\ud83c\udffb"
          },
          {
            "text": "I can't wait to see what you come up with next! \ud83d\ude0a"
          },
          {
            "text": "Your creativity knows no bounds! \ud83d\ude2e"
          },
          {
            "text": "I don't know how you come up with these ideas, but keep them coming! \ud83d\ude02"
          },
          {
            "text": "This is so impressive, you should be proud! \ud83d\udc4f\ud83c\udffb"
          },
          {
            "text": "You are a true artist, this is stunning! \ud83c\udfa8"
          },
          {
            "text": "I'm in love with your style, it's so unique! \ud83e\udd29"
          },
          {
            "text": "I never knew I needed this in my life, thank you for sharing! \ud83d\ude0a"
          },
          {
            "text": "You are so talented, I'm blown away! \ud83d\ude32"
          },
          {
            "text": "This is the kind of content that makes TikTok great! \ud83d\udc4f\ud83c\udffb"
          },
          {
            "text": "You have such a great sense of humor, keep the laughs coming! \ud83d\ude02"
          },
          {
            "text": "I'm inspired to try this out myself, thank you for the idea! \ud83d\udc4a"
          },
          {
            "text": "This video is a work of art, you should be proud! \ud83c\udfa8"
          },
          {
            "text": "I can't get enough of your content, keep it coming! \ud83d\ude0d"
          },
          {
            "text": "This is the kind of content that brightens my day! \ud83d\ude0b"
          },
          {
            "text": "You have such a great energy on camera, I'm hooked! \ud83c\udfa5"
          },
          {
            "text": "I love how you always think outside the box! \ud83d\ude0a"
          },
          {
            "text": "This is so creative, I can't stop watching! \ud83d\ude2e"
          },
          {
            "text": "You are a true master of your craft, keep up the great work! \ud83d\udc4d"
          },
          {
            "text": "I'm always excited to see what you'll post next! \ud83d\ude0d"
          },
          {
            "text": "You have such a great personality on camera, it's infectious! \ud83c\udfa5"
          },
          {
            "text": "This video is"
          }
        ]
    """.trimIndent()


private val userName =
    arrayListOf(
        "John",
        "Max",
        "Tarot",
        "MB",
        "Jeremy",
        "freaky",
        "Ahlam",
        "joe ma",
        "Tracey",
        "user89081",
        "Leo",
        "hebe",
        "mizzy",
        "drunk",
        "jake",
        "Scobby doo",
        "stephan012",
        "Alisha",
        "Rubi_me",
        "your dad",
        "cat",
        "Zoya",
        "Larry",
        "snuffy",
        "flynn",
        "Ema",
        "user9870",
        "mars"
    )