package com.example.trubbi.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.trubbi.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class OpinionSurveyFragment : Fragment() {

    private val eventTitle: String = "Cargar el Título del Evento"
    private lateinit var thisView: View
    private lateinit var fab: ExtendedFloatingActionButton
    private lateinit var containerFrame: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_opinion_survey, container, false)
        fab = thisView.findViewById(R.id.enviar)
        containerFrame = thisView.findViewById(R.id.contenedor)
        return thisView
    }

    override fun onStart() {
        super.onStart()
        containerFrame.isVisible=false;
        showAlertDialog(thisView)

        fab.setOnClickListener{
            val action = OpinionSurveyFragmentDirections.actionOpinionSurveyFragmentToMainFragment()
            thisView.findNavController().navigate(action)
        }
    }



    fun showAlertDialog(view:View){
         MaterialAlertDialogBuilder(requireContext())
             .setTitle("Tu opinión es importante!")
             . setMessage("¿Asististe al evento " + eventTitle +"?")
             .setNegativeButton("No, no asistí.",object : DialogInterface.OnClickListener{
                 override fun onClick(p0: DialogInterface?, p1: Int) {
                    showSnackbar("Gracias! Quitaremos ese evento de tu Historial.")
                    val action = OpinionSurveyFragmentDirections.actionOpinionSurveyFragmentToMainFragment()
                     thisView.findNavController().navigate(action)
                 }
             }).setPositiveButton("Sí, asistí al evento.",object :DialogInterface.OnClickListener{
                 override fun onClick(p0: DialogInterface?, p1: Int) {
                     showSnackbar("Genial! Te pedimos que respondas unas preguntas.")
                     containerFrame.isVisible=true;
                 }
             }).show()
    }

    fun showSnackbar(msg:String){
        Snackbar.make(thisView, msg, Snackbar.LENGTH_SHORT).show()
    }
}