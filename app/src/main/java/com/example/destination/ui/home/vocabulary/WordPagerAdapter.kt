package com.example.destination.ui.home.vocabulary

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.example.destination.R
import com.example.destination.ui.notes.Word

class WordPagerAdapter(private val words: List<ParentItem>) : PagerAdapter() {

    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.card_item, container, false)

        val frontText: TextView = view.findViewById(R.id.text_front_title)
        val frontText2: TextView = view.findViewById(R.id.text_front_subtext)
        val backLayout: LinearLayout = view.findViewById(R.id.layout_back_content)
        val addToNotes: ImageButton = view.findViewById(R.id.btn_add_to_notes)
        val audioIcon: ImageButton = view.findViewById(R.id.btn_audio)
        val cardView: CardView = view.findViewById(R.id.card_container)


        val textUzWord: TextView = view.findViewById(R.id.text_uz_word)
        val textEnWord: TextView = view.findViewById(R.id.text_en_word)
        val textDefinition: TextView = view.findViewById(R.id.text_definition)

        var isFront = true

        // Bind data to views
        frontText.text = words[position].enWord
        frontText2.text = words[position].uzWord
        textUzWord.text = words[position].uzExample
        textEnWord.text = words[position].enExample
        textDefinition.text = words[position].definition

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

        val flipOut = AnimatorInflater.loadAnimator(cardView.context, R.animator.flip_out) as AnimatorSet
        val flipIn = AnimatorInflater.loadAnimator(cardView.context, R.animator.flip_in) as AnimatorSet

        flipOut.setTarget(cardView)
        flipIn.setTarget(cardView)

        val frontText: TextView = cardView.findViewById(R.id.text_front_title)
        val frontText2: TextView = cardView.findViewById(R.id.text_front_subtext)
        val backLayout: LinearLayout = cardView.findViewById(R.id.layout_back_content)
        val addToNotes: ImageButton = cardView.findViewById(R.id.btn_add_to_notes)
        val audioIcon: ImageButton = cardView.findViewById(R.id.btn_audio)


        frontText.visibility = View.GONE
        frontText2.visibility = View.GONE
        addToNotes.visibility = View.GONE
        audioIcon.visibility = View.GONE
        backLayout.visibility = View.GONE

        frontText.animate().alpha(if (isFront) 0f else 1f).setDuration(600).start()
        frontText2.animate().alpha(if (isFront) 0f else 1f).setDuration(600).start()
        audioIcon.animate().alpha(if (isFront) 0f else 1f).setDuration(400).start()
        addToNotes.animate().alpha(if (isFront) 0f else 1f).setDuration(400).start()

        backLayout.animate().alpha(if (isFront) 1f else 0f).setDuration(600).start()

        cardView.postDelayed({
            addToNotes.visibility = View.VISIBLE
            audioIcon.visibility = View.VISIBLE

            if (isFront) {
                backLayout.visibility = View.VISIBLE
            } else {
                frontText.visibility = View.VISIBLE
                frontText2.visibility = View.VISIBLE
            }
        }, 300)

        flipOut.start()
        flipIn.start()

        return !isFront
    }
}