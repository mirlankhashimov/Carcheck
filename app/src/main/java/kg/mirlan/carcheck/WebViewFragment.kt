package kg.mirlan.carcheck

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.web_view_fragment.*


class WebViewFragment : BaseFragment(R.layout.web_view_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.hide()
        //setHasOptionsMenu(true)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() { // Handle the back button event
                    if (mWebView.canGoBack()) mWebView.goBack()
                    else view?.let {
                        Navigation.findNavController(it).popBackStack()
                    }// view?.let { activity?.finish() }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (internetCheck()) {
            mWebView.apply {
                this.isFocusable = true
                this.isFocusableInTouchMode = true
                this.settings.javaScriptEnabled = true
                this.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                this.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
                this.settings.cacheMode = WebSettings.LOAD_DEFAULT
                this.settings.domStorageEnabled = true
                this.settings.setAppCacheEnabled(true)
                this.settings.databaseEnabled = true
                this.settings.javaScriptEnabled = true
            }
            var url = when (arguments?.getInt("url")) {
                1 -> BASE_URL + IS_HAS_CAR
                2 -> SEARCH_CAR
                3 -> BASE_URL + SHTRAF
                else -> BASE_URL + CAR_CHEK
            }
            mWebView.loadUrl(url)
            mWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    if (webview_progress != null)
                        webview_progress.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return super.shouldOverrideUrlLoading(view, url)
                }

            }
        } else {
            if (webview_progress != null)
                webview_progress.visibility = View.GONE
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
    }

    private fun internetCheck(): Boolean {
        var available = false
        val connectivity =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivity.allNetworkInfo
        for (i in networkInfo.indices) {
            if (networkInfo[i].state == NetworkInfo.State.CONNECTED) {
                available = true
                break
            }
        }
        return available
    }

    companion object {
        const val BASE_URL = "https://portal.srs.kg/ru/service/eform/"
        const val CAR_CHEK =
            "d1a29582-8014-4196-ac3b-956c4855f352"//Carcheck
        const val IS_HAS_CAR =
            "62efff9d-4f04-4a36-bc16-d2975946639c"//Проверка на наличие транспортного средства
        const val SEARCH_CAR =
            "https://portal.srs.kg/ru/service/29c72dcb-9229-44c0-aba3-0bf984e611c7"//"Выберите вид владельца транспортного средства"
        const val SHTRAF =
            "26041f73-9ff8-4c19-bbe6-07cd08165845"//Проверка штрафов
    }
}