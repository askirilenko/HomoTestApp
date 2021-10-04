package com.example.homotestapp.hamster.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homotestapp.hamster.data.HamsterRepository
import com.example.homotestapp.hamster.data.HamsterModel
import java.util.*

class HamsterViewModel: ViewModel() {
    var hamsterModelListLiveData = MutableLiveData<MutableList<HamsterModel>?>()
    var filtered   = MutableLiveData<MutableList<HamsterModel>?>()
    var findString: String? = ""

    init{
        val hamsterRepository = HamsterRepository()
        hamsterModelListLiveData = hamsterRepository.fetchAllPosts()
    }

   fun getHamsters() = hamsterModelListLiveData

   fun getHamsters(string: String?): MutableLiveData<MutableList<HamsterModel>?>{
        val filteredList = hamsterModelListLiveData.value?.filter {
            it.title?.toLowerCase()?.contains(
                string.toString().toLowerCase().trim()
            ) == true
        }
       filtered.value = filteredList as MutableList<HamsterModel>
       return filtered
    }

   fun swapPinned(){
        var newPosition = 0
        hamsterModelListLiveData.value?.forEachIndexed {index, hamsterModel ->
            if (hamsterModel.pinned == true){
                Collections.swap(hamsterModelListLiveData.value, newPosition, index);
                newPosition++
            }
        }
   }

    fun swapHamsterUp(item: HamsterModel?, data: MutableLiveData<MutableList<HamsterModel>?>){
        var newPosition = 0
        var itemPosition = 0
        data.value?.forEachIndexed { index, hamster ->
            if (hamster === item)   itemPosition = index
            if (hamster.pinned == true && hamster != item ) newPosition++
            }
        data.value?.get(itemPosition)?.pinned = true
        data.value?.add(newPosition, item!!)
        data.value?.removeAt(itemPosition + 1)
    }

    fun swapHamsterDown(item: HamsterModel?, data: MutableLiveData<MutableList<HamsterModel>?>){
        var newPosition = 0
        var itemPosition = 0
        data.value?.forEachIndexed {index, hamster ->
            if (hamster === item)   itemPosition = index
            if (hamster.pinned == true  && hamster != item ) newPosition++
        }
        data.value?.get(itemPosition)?.pinned = false
        data.value?.add(newPosition+1, item!!)
        data.value?.removeAt(itemPosition)
    }

    fun addDeveloper(developer: HamsterModel){
        hamsterModelListLiveData.value?.add(0, developer)
    }
}






