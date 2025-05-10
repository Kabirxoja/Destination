package uz.kabirhoja.destination.ui.adapters

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.kabirhoja.destination.R
import uz.kabirhoja.destination.custom.AnimationButton.animateClick
import uz.kabirhoja.destination.data.data.Vocabulary

class WordPagerAdapter() : PagerAdapter() {

    private var listener: OnItemClickListener? = null

    private val list: MutableList<Vocabulary> = mutableListOf()


    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context).inflate(R.layout.card_item, container, false)

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
        frontText.text = list[position].englishWord
        frontText2.text = list[position].translatedWord
        textUzWord.text = list[position].exampleTranslatedWord
        textEnWord.text = list[position].exampleInEnglish
        textDefinition.text = list[position].definition

        if (list[position].isNoted == 1) addToNotes.setBackgroundResource(R.drawable.ic_note_true)
        else addToNotes.setBackgroundResource(R.drawable.ic_note_false)


        // Set up flip animation
        cardView.setOnClickListener {
            isFront = flipCard(cardView, isFront)
        }
        audioIcon.setOnClickListener {
            listener?.onAudioClickPager(list[position])
            audioIcon.setBackgroundResource(R.drawable.ic_audio_on) // Change icon when clicked
            Handler(Looper.getMainLooper()).postDelayed({
                audioIcon.setBackgroundResource(R.drawable.ic_audio_off) // Reset only this button
            }, 500)
            it.animateClick()
        }
        addToNotes.setOnClickListener {
            listener?.onNoteClickPager(list[position])
            toggleNoteState(list[position], addToNotes)
            it.animateClick()
        }


        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("ResourceType")
    private fun flipCard(cardView: CardView, isFront: Boolean): Boolean {
        val scale = cardView.context.resources.displayMetrics.density
        cardView.cameraDistance = 12000 * scale  // Depth effect

        val flipOut =
            AnimatorInflater.loadAnimator(cardView.context, R.anim.flip_out) as AnimatorSet
        val flipIn = AnimatorInflater.loadAnimator(cardView.context, R.anim.flip_in) as AnimatorSet

        flipOut.setTarget(cardView)
        flipIn.setTarget(cardView)

        val frontText: TextView = cardView.findViewById(R.id.text_front_title)
        val frontText2: TextView = cardView.findViewById(R.id.text_front_subtext)
        val backLayout: LinearLayout = cardView.findViewById(R.id.layout_back_content)
        val addToNotes: ImageButton = cardView.findViewById(R.id.btn_add_to_notes)
        val audioIcon: ImageView = cardView.findViewById(R.id.btn_audio)


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


    interface OnItemClickListener {
        fun onAudioClickPager(word: Vocabulary)
        fun onNoteClickPager(word: Vocabulary)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun getList(list: List<Vocabulary>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private fun updateNoteIcon(button: ImageButton, isNoted: Int) {
        if (isNoted == 1) {
            button.setBackgroundResource(R.drawable.ic_note_true)
        } else {
            button.setBackgroundResource(R.drawable.ic_note_false)
        }
    }

    private fun toggleNoteState(vocabularyItem: Vocabulary, button: ImageButton) {
        val newIsNoted = if (vocabularyItem.isNoted == 1) 0 else 1
        vocabularyItem.isNoted = newIsNoted
        updateNoteIcon(button, newIsNoted) // Update UI immediately
    }


}