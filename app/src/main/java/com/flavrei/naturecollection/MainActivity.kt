package fr.flavrei.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flavrei.naturecollection.PlantRepository
import com.flavrei.naturecollection.R
import com.flavrei.naturecollection.fragments.CollectionFragment
import fr.flavrei.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Charger notre PlantRepository
        val repo = PlantRepository()

        // Mettre à jour la liste de plantes
        repo.updateData {
            // Injecter le fragment dans la boîte
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, CollectionFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}