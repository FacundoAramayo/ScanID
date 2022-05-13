package eth.facundoaramayo.scanid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanIDViewModel: ViewModel() {
    private var idInfo: IDInfo? = null

    private var mutableInfo: MutableLiveData<IDInfo> = MutableLiveData()

    fun observeInfo(): LiveData<IDInfo?> = mutableInfo

    fun processIdData(input: String) {
        var inputText = input.substring(input.indexOf("@") + 1)
        val lastName = inputText.substring(0, inputText.indexOf("@"))

        inputText = inputText.substring(inputText.indexOf("@") + 1)
        val firstName = inputText.substring(0, inputText.indexOf("@"))

        inputText = inputText.substring(inputText.indexOf("@") + 1)
        val gender = inputText.substring(0, inputText.indexOf("@"))

        inputText = inputText.substring(inputText.indexOf("@") + 1)
        val idNumber = inputText.substring(0, inputText.indexOf("@"))

        inputText = inputText.substring(inputText.indexOf("@") + 1)

        val version = inputText.substring(0, inputText.indexOf("@"))

        inputText = inputText.substring(inputText.indexOf("@") + 1)
        val birthDate = inputText.substring(0, inputText.indexOf("@"))

        idInfo = IDInfo(firstName, lastName, gender, idNumber, birthDate).also {
            mutableInfo.postValue(it)
        }
        Log.d("LOG-", "ID Info: $idInfo")
        Log.d("LOG-", "Extra info: $version")
    }
}
