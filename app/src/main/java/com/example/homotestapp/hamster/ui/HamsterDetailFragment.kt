package com.example.homotestapp.hamster.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.homotestapp.R

class HamsterDetailFragment: DialogFragment(){
    var description : String? = null

    companion object {
        fun newInstance(hamsterDescription : String?) = HamsterDetailFragment().apply {
            if (hamsterDescription!= null)
            arguments = Bundle().apply {
                putString("description", hamsterDescription)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) dismiss();
        val args = arguments
        if (args != null) {
            description = args.getString("description").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homo_details, container, false)
        view.findViewById<TextView>(R.id.description).text = description
        return view
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        val nullHamsterDescription = activity as NullHamsterDescription
        nullHamsterDescription.setNullHamsterDescription()
    }

    interface NullHamsterDescription{
        fun setNullHamsterDescription()
    }
}