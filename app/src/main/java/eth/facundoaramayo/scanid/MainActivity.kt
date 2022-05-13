package eth.facundoaramayo.scanid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import eth.facundoaramayo.scanid.databinding.ActivityMainBinding
import java.util.*

// NOTE: This is a section of an old project. See all updated documentation at this link: https://github.com/journeyapps/zxing-android-embedded

class MainActivity : AppCompatActivity() {

    private val viewModel: ScanIDViewModel by viewModels()
    private lateinit var view: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        viewModel.observeInfo().observe(this) {
            it?.let {
                view.apply {
                    info.visibility = View.VISIBLE
                    name.text = resources.getString(R.string.id_info_name, it.firstName)
                    lastName.text = resources.getString(R.string.id_info_last_name, it.lastName)
                    number.text = resources.getString(R.string.id_info_number, it.idNumber)
                    birthDate.text = resources.getString(R.string.id_info_birth_date, it.birthDate)
                    gender.text = resources.getString(R.string.id_info_gender, it.gender)

                    name.show()
                    lastName.show()
                    number.show()
                    birthDate.show()
                    gender.show()
                }
            }
        }

        // Define desired formats to be scanned
        val oDesiredFormats = Arrays.asList(*ID_QR_CODE.split(",").toTypedArray())
        val integrator = IntentIntegrator(this)

        view.btnScan.setOnClickListener {
            integrator.initiateScan(oDesiredFormats)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        result?.let {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                viewModel.processIdData(result.contents)
            }
        }
    }

    companion object {
        const val ID_QR_CODE = "PDF_417"
    }

    private fun TextView.show() {
        this.visibility = View.VISIBLE
    }
}
