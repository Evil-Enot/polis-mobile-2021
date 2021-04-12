package ru.mail.polis.list.of.people

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.mail.polis.R

class PeopleAdapter(
    private val people: List<Person>
) : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(people[position])
    }

    override fun getItemCount(): Int {
        return people.size
    }

    class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPhoto: ImageView = itemView.findViewById(R.id.component_person_header__avatar)
        private val tvName: TextView = itemView.findViewById(R.id.component_person_header__name)
        private val tvAge: TextView = itemView.findViewById(R.id.component_person_header__age)
        private val llIvTags: LinearLayout = itemView.findViewById(R.id.people_item_ll_tags)
        private val ivMetro: ImageView = itemView.findViewById(R.id.people_item_iv_metro)
        private val tvMetro: TextView = itemView.findViewById(R.id.people_item_tv_metro)
        private val ivBranchColor: ImageView = itemView.findViewById(R.id.people_item_iv_branch_color)
        private val ivMoney: ImageView = itemView.findViewById(R.id.people_item_iv_money)
        private val tvMoney: TextView = itemView.findViewById(R.id.people_item_tv_money)
        private val cvRooms: List<CardView> = listOf(
            itemView.findViewById(R.id.people_item_ll_cv_rooms1),
            itemView.findViewById(R.id.people_item_ll_cv_rooms2),
            itemView.findViewById(R.id.people_item_ll_cv_rooms3)
        )
        private val tvRooms: List<TextView> = listOf(
            itemView.findViewById(R.id.people_item_ll_tv_rooms1),
            itemView.findViewById(R.id.people_item_ll_tv_rooms2),
            itemView.findViewById(R.id.people_item_ll_tv_rooms3)
        )
        private val tvDescription: TextView = itemView.findViewById(R.id.people_item_tv_description)

        fun bind(person: Person) {
            if (person.photo != 0)
                ivPhoto.setImageResource(person.photo)

            tvName.text = person.name
            tvAge.text = person.age
            val tags: List<ImageView> = person.tags.map { url ->
                urlToImageView(itemView.context, url)
            }
            tags.forEach(llIvTags::addView)
            ivMetro.setImageResource(R.drawable.ic_train)
            tvMetro.text = person.metro.stationName
            // ivBranchColor.setImageResource(person.branchColor)
            ivMoney.setImageResource(R.drawable.ic_wallet)
            tvMoney.text = "от " + person.money.first + " до " + person.money.second
            for (i in 0..3.coerceAtMost(person.rooms.size - 1)) {
                cvRooms[i].visibility = View.VISIBLE
                tvRooms[i].text = person.rooms[i]
            }
            tvDescription.text = person.description
        }

        private fun urlToImageView (context: Context, url: Int):ImageView{
            val iv = ImageView(context)

            iv.layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )

            iv.adjustViewBounds = true
            iv.setPadding(5, 5, 10, 5)
            Glide.with(itemView).load(url).into(iv)

            return iv
        }
    }
}
