package com.example.trubbi.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.compose.runtime.rememberUpdatedState
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.trubbi.R
import com.example.trubbi.viewmodel.EventViewModel
import com.example.trubbi.viewmodel.SurveyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class OpinionSurveyFragment : Fragment() {

    private val eventTitle: String = "Cargar el Título del Evento"
    private lateinit var thisView: View
    private lateinit var fab: ExtendedFloatingActionButton
    private lateinit var containerFrame: ViewGroup
    private lateinit var viewModel : SurveyViewModel

    private lateinit var chk1: CheckBox
    private lateinit var chk2: CheckBox
    private lateinit var chk3: CheckBox
    private lateinit var txt: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_opinion_survey, container, false)
        fab = thisView.findViewById(R.id.enviar)
        containerFrame = thisView.findViewById(R.id.contenedor)

        chk1 = thisView.findViewById(R.id.chk1)
        chk2 = thisView.findViewById(R.id.chk2)
        chk3 = thisView.findViewById(R.id.chk3)
        txt = thisView.findViewById(R.id.openQuestion)

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel =ViewModelProvider(this).get(SurveyViewModel::class.java)

        viewModel.surveyModel.observe(viewLifecycleOwner, Observer { result->
            chk1.isChecked = result.question1
            chk2.isChecked = result.question2
            chk3.isChecked = result.question3
            txt.setText(result.openQuestion + result.question1 +result.question2 + result.question3)
        })
        if(viewModel.dialogOpened.value == false){
            containerFrame.isVisible=false;
            showAlertDialog(thisView)
            viewModel.setDialogOpened(true)
        }

    }

    override fun onStart() {
        super.onStart()
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
             }).show().setCanceledOnTouchOutside(false)
    }

    fun showSnackbar(msg:String){
        Snackbar.make(thisView, msg, Snackbar.LENGTH_SHORT).show()
    }
}