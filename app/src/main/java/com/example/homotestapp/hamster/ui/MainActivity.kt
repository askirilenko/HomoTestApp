package com.example.homotestapp.hamster.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homotestapp.R
import com.example.homotestapp.hamster.data.HamsterModel
import com.example.homotestapp.network.ApiClient

class MainActivity : AppCompatActivity(), HomoRecyclerFragment.ShowFragmentDetail, HamsterDetailFragment.NullHamsterDescription {
    companion object {
        var hamsterDescription: String? = null
        var homoRecyclerFragment = HomoRecyclerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            homoRecyclerFragment = HomoRecyclerFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frgm_recycler, homoRecyclerFragment)
                .commit()
        }

        ApiClient.setContext(this)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("description", hamsterDescription)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("description").let{
            hamsterDescription
        }
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (hamsterDescription!=null)
            HamsterDetailFragment.newInstance(hamsterDescription).show(supportFragmentManager.beginTransaction(), "Tag")
        } else {
           if (hamsterDescription!=null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frgm_description, HamsterDetailFragment.newInstance(hamsterDescription))
                .commit()
        }
    }

    override fun showFragmentDetail(item: HamsterModel?) {
        hamsterDescription = item?.description.toString()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
             HamsterDetailFragment.newInstance(item?.description).show(supportFragmentManager.beginTransaction(), "Tag")
        }
        else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.frgm_description, HamsterDetailFragment.newInstance(item?.description))
                .commit()
        }
    }

    override fun setNullHamsterDescription() {
        hamsterDescription = null
    }
}


