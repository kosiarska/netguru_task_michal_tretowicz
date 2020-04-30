package pl.michal.tretowicz.ui.random.cities.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_city.view.*
import pl.michal.tretowicz.R
import pl.michal.tretowicz.data.model.CityColorDate
import pl.michal.tretowicz.injection.ActivityContext
import java.text.Collator
import java.util.*
import javax.inject.Inject


class RandomCitiesAdapter @Inject constructor(@ActivityContext private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private val list = arrayListOf<CityColorDate>()
    var onClickListener : View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.item_city, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClickListener)
    }

    fun addCity(cityColorDate: CityColorDate) {
        list.add(cityColorDate)

        list.sortWith(Comparator { a, b -> Collator.getInstance().compare(a.city, b.city) })

        notifyDataSetChanged()
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: CityColorDate, onClickListener: View.OnClickListener?) {
        itemView.city.text = data.city
        itemView.city.setTextColor(data.color)
        itemView.date.text = data.dateOfEmission.toString("HH:mm:ss dd.MM.yyyy")

        itemView.tag = data
        itemView.setOnClickListener(onClickListener)
    }
}