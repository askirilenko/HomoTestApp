package com.example.homotestapp.hamster.ui


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homotestapp.R
import com.example.homotestapp.hamster.data.HamsterModel
import com.example.homotestapp.hamster.viewmodel.HamsterViewModel
import java.util.*


lateinit var recyclerView: RecyclerView
private lateinit var adapter: HamsterAdapter

class HomoRecyclerFragment : Fragment(), HamsterAdapter.OnItemClickListener {

    var clickedHamster: HamsterModel? = null

    companion object {
        fun newInstance(): HomoRecyclerFragment {
            return HomoRecyclerFragment()
        }

    }

   private val vm by lazy {
        ViewModelProvider(this)[HamsterViewModel::class.java]
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homo_recycler, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        val progress: ImageView
        progress = view.findViewById(R.id.progressView)

        Glide.with(requireContext())
            .load(R.drawable.progress)
            .circleCrop()
            .into(progress);

        val developer = HamsterModel(
            resources.getString(R.string.developer_name),
            resources.getString(R.string.developer_description),
            true,
            resources.getString(R.string.developer_image)
        )

        initAdapter()

        if (savedInstanceState == null)
            vm.getHamsters().observe(viewLifecycleOwner, Observer
            {
                if (it != null) {
                    recyclerView.visibility = View.VISIBLE
                    vm.addDeveloper(developer)
                    vm.swapPinned()
                    adapter.setData(it as ArrayList<HamsterModel>)
                } else {
                    showToast(resources.getString(R.string.warning))
                }
                progress.visibility = View.GONE
            })

        else
            vm.getHamsters(vm.findString).observe(viewLifecycleOwner,{
                adapter.setData(it as ArrayList<HamsterModel>)
                progress.visibility = View.GONE
            })


        view.findViewById<EditText>(R.id.findHamster)?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                vm.findString = editable.toString()
                findHamster(editable.toString())
            }
        })
        return view
    }


    private fun initAdapter() {
        adapter = HamsterAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(item: HamsterModel?) {
        clickedHamster = item
        val showFragmentDetail = activity as ShowFragmentDetail
        showFragmentDetail.showFragmentDetail(item)
    }

    fun findHamster(string: String) {
       vm.getHamsters(string).observe(this, {
            adapter.setData(it as ArrayList<HamsterModel>)
        })
    }

    override fun shareHamster(view: View) {
        val shareView = view.findViewById<View>(R.id.share_layout)
        val bitmap: Bitmap = shareView.drawToBitmap(Bitmap.Config.ARGB_8888)

        val url = MediaStore.Images.Media.insertImage(
            context?.getContentResolver(),
            bitmap,
            "title",
            null
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url))
        shareIntent.type = "image/jpeg"
        startActivity(Intent.createChooser(shareIntent, "Share"))
    }

    override fun swapHamsterUp(item: HamsterModel?) {
        vm.swapHamsterUp(item, vm.hamsterModelListLiveData)
        vm.swapHamsterUp(item, vm.filtered)
        adapter.notifyDataSetChanged()
    }

    override fun swapHamsterDown(item: HamsterModel?) {
        vm.swapHamsterDown(item, vm.hamsterModelListLiveData)
        vm.swapHamsterDown(item, vm.filtered)
        adapter.notifyDataSetChanged()
    }

    interface ShowFragmentDetail{
        fun showFragmentDetail(item: HamsterModel?)
    }
}