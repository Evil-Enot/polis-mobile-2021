package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mail.polis.dao.IPhotoUriService
import ru.mail.polis.dao.PhotoUriService
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import java.io.ByteArrayOutputStream

class AddApartmentViewModel : ViewModel() {

    companion object {
        private const val BITMAP_QUALITY = 100
    }

    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val photoUriService: IPhotoUriService = PhotoUriService()
    val list = LinkedHashSet<Bitmap>()

    fun addApartment(apartmentED: ApartmentED) {

        viewModelScope.launch(Dispatchers.IO) {
            apartmentED.photosUrls = getUrlList()
            apartmentService.addApartment(apartmentED)
        }
    }

    private fun bitmapToInputStream(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, baos)
        return baos.toByteArray()
    }

    private suspend fun getUrlList(): List<String> {
        val urlList = ArrayList<String>()

        list.forEach {
            val url = photoUriService.saveImage(bitmapToInputStream(it))
            urlList.add(url.toString())
        }

        return urlList
    }
}
