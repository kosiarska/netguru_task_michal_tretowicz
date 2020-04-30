package pl.michal.tretowicz.ui.random.cities.details

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        val extraColor = intent.getIntExtra(EXTRA_COLOR, 0)
        toolbar.setBackgroundColor(extraColor)



        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        if(extraColor == Color.WHITE || extraColor == Color.YELLOW) {
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.gray))
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.gray), PorterDuff.Mode.SRC_ATOP)
        }

        val cityName = intent.getStringExtra(EXTRA_TEXT).orEmpty()
        supportActionBar!!.title = cityName

        toolbar.setNavigationOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction().replace(R.id.frame, DetailsFragment.newInstance(cityName)).commit()
    }
}
