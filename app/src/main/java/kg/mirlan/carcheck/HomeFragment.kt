package kg.mirlan.carcheck

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private var isClick = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCliсks()
        setToolbar()
        val request = AdRequest.Builder().build()
        adView.loadAd(request)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setupCliсks() {
        carcheck.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_home_screen_to_webview_screen)
        }
        have_car.setOnClickListener {
            val bundle = bundleOf("url" to 1)
            Navigation.findNavController(it)
                .navigate(R.id.action_home_screen_to_webview_screen, bundle)
        }
        search_owner.setOnClickListener {
            val bundle = bundleOf("url" to 2)
            Navigation.findNavController(it)
                .navigate(R.id.action_home_screen_to_webview_screen, bundle)
        }
        check_fine.setOnClickListener {
            val bundle = bundleOf("url" to 3)
            Navigation.findNavController(it)
                .navigate(R.id.action_home_screen_to_webview_screen, bundle)
        }
        purchase.setOnClickListener {
            isClick = !isClick
            if(isClick)
                purchase.text = "0551128383"
            else
                purchase.text = "Купить кофе разработчику"
        }
    }
    private fun setToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayShowCustomEnabled(true)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.webview, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share_button -> {
                val sendIntent = Intent()
                sendIntent.type = "text/plain"
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Смотри какое крутое приложение")
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=kg.mirlan.carcheck")
                startActivity(Intent.createChooser(sendIntent, "Carcheck KG vs Штраф KG"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}