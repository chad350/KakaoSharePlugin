package com.example.kakaotest

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.kakaotest.databinding.ActivityMainBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        KakaoSdk.init(this, "8b73348202dc4a0daca7f1f89da7f19b")


        var keyHash = Utility.getKeyHash(this);
        Log.d("KeyHash: ",keyHash);

        if (ShareClient.instance.isKakaoTalkSharingAvailable(this)) {

            val defaultFeed = FeedTemplate(
                content = Content(
                    title = "오늘의 디저트",
                    description = "#케익 #딸기 #삼평동 #카페 #분위기 #소개팅",
                    imageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                    link = Link(
                        webUrl = "https://developers.kakao.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                itemContent = ItemContent(
                    profileText = "Kakao",
                    profileImageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                    titleImageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                    titleImageText = "Cheese cake",
                    titleImageCategory = "Cake",
                    items = listOf(
                        ItemInfo(item = "cake1", itemOp = "1000원"),
                        ItemInfo(item = "cake2", itemOp = "2000원"),
                        ItemInfo(item = "cake3", itemOp = "3000원"),
                        ItemInfo(item = "cake4", itemOp = "4000원"),
                        ItemInfo(item = "cake5", itemOp = "5000원")
                    ),
                    sum = "Total",
                    sumOp = "15000원"
                ),
                social = Social(
                    likeCount = 286,
                    commentCount = 45,
                    sharedCount = 845
                ),
                buttons = listOf(
                    Button(
                        "웹으로 보기",
                        Link(
                            webUrl = "https://developers.kakao.com",
                            mobileWebUrl = "https://developers.kakao.com"
                        )
                    ),
                    Button(
                        "앱으로 보기",
                        Link(
                            androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
                            iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                        )
                    )
                )
            )

            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(this, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.e("Kakao Test", "카카오톡 공유 실패", error)
                }
                else if (sharingResult != null) {
                    Log.d("Kakao Test", "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w("Kakao Test", "Warning Msg: ${sharingResult.warningMsg}")
                    Log.w("Kakao Test", "Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        }

//        else {
//            // 카카오톡 미설치: 웹 공유 사용 권장
//            // 웹 공유 예시 코드
//            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)
//
//            // CustomTabs으로 웹 브라우저 열기
//
//            // 1. CustomTabsServiceConnection 지원 브라우저 열기
//            // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
//            try {
//                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
//            } catch(e: UnsupportedOperationException) {
//                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
//            }
//
//            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
//            // ex) 다음, 네이버 등
//            try {
//                KakaoCustomTabsClient.open(context, sharerUrl)
//            } catch (e: ActivityNotFoundException) {
//                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
//            }
//        }


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}