package pl.michal.tretowicz.ui.random.cities.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import pl.michal.tretowicz.R

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TEXT = "text"
        const val EXTRA_COLOR = "color"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)

        toolbar.setBackgroundColor(intent.getIntExtra(EXTRA_COLOR, 0))

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val cityName = intent.getStringExtra(EXTRA_TEXT).orEmpty()
        supportActionBar!!.title = cityName

        toolbar.setNavigationOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction().replace(R.id.frame, DetailsFragment.newInstance(cityName)).commit()
    }
}
