package kg.mirlan.carcheck

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.MobileAds

class App: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}