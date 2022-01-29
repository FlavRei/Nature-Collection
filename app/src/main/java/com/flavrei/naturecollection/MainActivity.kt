package fr.flavrei.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flavrei.naturecollection.R
import fr.flavrei.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Injecter le fragment dans la boîte
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}