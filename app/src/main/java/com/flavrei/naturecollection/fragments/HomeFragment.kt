package fr.flavrei.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.flavrei.naturecollection.PlantModel
import com.flavrei.naturecollection.R
import fr.flavrei.naturecollection.MainActivity
import fr.flavrei.naturecollection.adapter.PlantAdapter
import fr.flavrei.naturecollection.adapter.PlantItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        // Créer une liste qui va stocker les plantes
        val plantList = arrayListOf<PlantModel>()

        // Enregistrer une 1ère plante dans notre liste (Pissenlit)
        plantList.add(PlantModel(
            "Pissenlit",
            "Jaune Soleil",
            "https://cdn.pixabay.com/photo/2014/05/20/14/36/flower-348814_960_720.jpg",
            false))

        // Enregistrer une 2e plante dans notre liste (Rose)
        plantList.add(PlantModel(
            "Rose",
            "Ça pique un peu",
            "https://cdn.pixabay.com/photo/2014/04/10/11/24/rose-320868_960_720.jpg",
            false))

        // Enregistrer une 3e plante dans notre liste (Cactus)
        plantList.add(PlantModel(
            "Cactus",
            "Ça pique beaucoup",
            "https://cdn.pixabay.com/photo/2016/05/17/05/57/cyprus-1397510_960_720.jpg",
            false))

        // Enregistrer une 4e plante dans notre liste (Tulipe)
        plantList.add(PlantModel(
            "Tulipe",
            "C'est beau",
            "https://cdn.pixabay.com/photo/2018/03/26/16/38/nature-3263198_960_720.jpg",
            false))


        // Récupérer le 1er RecyclerView
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_horizontal_plant)

        // Récupérer le 2e RecyclerView
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}