package fr.flavrei.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.flavrei.naturecollection.PlantRepository
import com.flavrei.naturecollection.R
import com.flavrei.naturecollection.fragments.AddPlantFragment
import com.flavrei.naturecollection.fragments.CollectionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.flavrei.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        // Importer la bottom_navigation_view
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plant_page -> {
                    loadFragment(AddPlantFragment(this), R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        // Charger notre PlantRepository
        val repo = PlantRepository()

        // Actualiser le titre de la page
        // findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        // Mettre à jour la liste de plantes
        repo.updateData {
            // Injecter le fragment dans la boîte
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}