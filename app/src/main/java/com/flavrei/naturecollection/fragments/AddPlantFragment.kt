package com.flavrei.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.flavrei.naturecollection.PlantModel
import com.flavrei.naturecollection.PlantRepository
import com.flavrei.naturecollection.PlantRepository.Singleton.downloadUri
import com.flavrei.naturecollection.R
import fr.flavrei.naturecollection.MainActivity
import java.util.*

class AddPlantFragment(
    private val context: MainActivity
) : Fragment() {

    private var file: Uri? = null
    private var uploadedImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)

        // Récupérer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        // Récupérer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        // Ouvrir images du téléphone au moment du clic
        pickupImageButton.setOnClickListener { pickupImage() }

        // Récupérer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener { sendForm(view) }

        return view
    }

    private fun sendForm(view: View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!)
        val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
        val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
        val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
        val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
        val downloadImageUrl = downloadUri

        // Créer un nouvel objet PlantModel
        val plant = PlantModel(
            UUID.randomUUID().toString(),
            plantName,
            plantDescription,
            downloadImageUrl.toString(),
            grow,
            water
        )
        // Envoyer en BD
        repo.insertPlant(plant)
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        getResult.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            // Vérifier si les données sont nulles
            if (!(it.data == null || it.data?.data == null)) {
                // Récupérer l'image sélectionnée
                file = it.data?.data

                // Mettre à jour l'aperçu de l'image
                uploadedImage?.setImageURI(file)
            }
        }
    }
}