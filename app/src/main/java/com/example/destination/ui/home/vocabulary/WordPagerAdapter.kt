package com.example.destination.ui.home.vocabulary

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.example.destination.R
import com.example.destination.ui.notes.Word

class WordPagerAdapter(private val words: List<ParentItem>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.card_item, container, false)
        val frontText: TextView = view.findViewById(R.id.your_front_view_id)
        val backText: TextView = view.findViewById(R.id.your_back_view_id)
        val addToNotes: ImageButton = view.findViewById(R.id.add_to_notes)
        val cardView: CardView = view.findViewById(R.id.your_card_view_id)

        var isFront = true

        // Bind data to views
        frontText.text = words[position].enWord
        backText.text = words[position].uzWord

        // Set up flip animation
        cardView.setOnClickListener {
            isFront = flipCard(cardView, isFront)
        }

        addToNotes.setOnClickListener{
            Toast.makeText(view.context, "note is added", Toast.LENGTH_SHORT).show()
        }


        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return words.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    private fun flipCard(cardView: CardView, isFront: Boolean): Boolean {
        val scale = cardView.context.resources.displayMetrics.density
        cardView.cameraDistance = 12000 * scale  // Depth effect

        val flipOut =
            AnimatorInflater.loadAnimator(cardView.context, R.animator.flip_out) as AnimatorSet
        val flipIn =
            AnimatorInflater.loadAnimator(cardView.context, R.animator.flip_in) as AnimatorSet

        flipOut.setTarget(cardView)
        flipIn.setTarget(cardView)

        val frontText: TextView = cardView.findViewById(R.id.your_front_view_id)
        val backText: TextView = cardView.findViewById(R.id.your_back_view_id)
        val addToNotes: ImageButton = cardView.findViewById(R.id.add_to_notes)

        frontText.visibility = View.GONE
        addToNotes.visibility = View.GONE
        backText.visibility = View.GONE

        frontText.animate().alpha(if (isFront) 0f else 1f).setDuration(600).start()
        backText.animate().alpha(if (isFront) 1f else 0f).setDuration(600).start()

        cardView.postDelayed({
            addToNotes.visibility = View.VISIBLE

            if (isFront) {
                backText.visibility = View.VISIBLE
            } else {
                frontText.visibility = View.VISIBLE
            }
        }, 300)

        flipOut.start()
        flipIn.start()

        return !isFront
    }
}