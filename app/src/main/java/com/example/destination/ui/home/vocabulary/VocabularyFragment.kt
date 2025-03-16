package com.example.destination.ui.home.vocabulary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.destination.R
import com.example.destination.databinding.FragmentVocabularyBinding
import com.example.destination.ui.home.vocabulary.ParentItemListCreator.createParentItemList
import com.example.destination.ui.notes.Word
import kotlin.math.abs


class VocabularyFragment : Fragment() {
    private var _binding: FragmentVocabularyBinding? = null
    private val binding get() = _binding!!

    private var cardViewViewPager:Boolean = false

    private lateinit var viewModel: VocabularyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVocabularyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this)[VocabularyViewModel::class.java]


        val args = arguments // arguments is a nullable Bundle
        val topicUnit = args?.getString("topicUnit")
        val topicNumber = args?.getInt("topicNumber")
        Toast.makeText(binding.root.context, "$topicUnit   - $topicNumber", Toast.LENGTH_SHORT).show()

        val parentAdapter = ParentAdapter()
        val parentItemList = createParentItemList()
        parentAdapter.submitList(parentItemList)

        binding.parentRecyclerView.adapter = parentAdapter
        binding.parentRecyclerView.layoutManager = LinearLayoutManager(root.context)


        binding.btnChange.setOnClickListener {
            if( binding.viewPager.visibility.equals(View.GONE)){
                binding.viewPager.visibility = View.VISIBLE
                binding.parentRecyclerView.visibility = View.GONE
                binding.btnChangeIcon.background = resources.getDrawable(R.drawable.ic_table)
            }else{
                binding.viewPager.visibility = View.GONE
                binding.parentRecyclerView.visibility = View.VISIBLE
                binding.btnChangeIcon.background = resources.getDrawable(R.drawable.ic_card_rectangle)

            }
        }

        binding.viewPager.setPageTransformer(false) { page, position ->
            // Adjust the scale based on the position
            val scale = if (position < -1 || position > 1) 0.75f else 1 - abs(position) * 0.25f
            page.scaleX = scale
            page.scaleY = scale

            // Adjust the translation to ensure the next item's corner is visible
            val offset = position * page.width // Adjust the offset based on the page width
            page.translationX = -offset * 0.166f // Adjust the translation to control overlap
        }
        val adapter = WordPagerAdapter(parentItemList)
        binding.viewPager.adapter = adapter



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

object ParentItemListCreator {
    fun createParentItemList(): List<ParentItem> {
        return listOf(
            ParentItem(
                unit = "3",
                type = "topic_vocabulary",
                enWord = "Apple",
                uzWord = "Olma",
                definition = "A round fruit with red or green skin and a white flesh.",
                enExample = "I ate an apple.",
                uzExample = "Men olma yedim."
            ),
            ParentItem(
                unit = "3",
                type = "topic_vocabulary",
                enWord = "Book",
                uzWord = "Kitob",
                definition = "A written or printed work consisting of pages glued or sewn together along one side and bound in covers.",
                enExample = "I read a book.",
                uzExample = "Men kitob o'qidim."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Insect (n)",
                uzWord = "hashorat",
                definition = "A small animal that has six legs and often has wings.",
                enExample = "Flies and mosquitoes are insects.",
                uzExample = "Chivinlar va qong'izoqlar hasharotlar."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Lightning (n)",
                uzWord = "chaqmoq",
                definition = "The bright flashes of light that you see in the sky during a storm.",
                enExample = "The ship was struck by lightning soon after it left the port.",
                uzExample = "Kema portdan chiqib ketgandan so'ng tezda chaqmoq urdi."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Litter (v)",
                uzWord = "axlat tashlamoq",
                definition = "To drop litter.",
                enExample = "The sign said \"\"No littering.\"\"",
                uzExample = "Belgida \"\"Axlat tashlash taqiqlangan\"\" deb yozilgan edi."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Litter (n)",
                uzWord = "axlat",
                definition = "Things that people have dropped on the ground in a public place, making it untidy.",
                enExample = "Pick up that litter and put it in the bin.",
                uzExample = "O'sha axlatni olib, qutiga tashlang."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Local (adj)",
                uzWord = "mahalliy",
                definition = "In or related to a particular area, especially the place where you live.",
                enExample = "Ask for the book in your local library.",
                uzExample = "Mahalliy kutubxonada kitobni so'rang."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Locate (v)",
                uzWord = "joylashmoq/joylashtirmoq",
                definition = "To find out the exact place where someone or something is, or to exist in a particular place.",
                enExample = "The hotel is located in Wolverhampton town centre.",
                uzExample = "Mehmonxona Wolverhampton shahar markazida joylashgan."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Mammal (n)",
                uzWord = "sut emizuvchi",
                definition = "An animal that is born from its mother's body, not from an egg, and drinks its mother's milk as a baby.",
                enExample = "Humans and monkeys are mammals.",
                uzExample = "Odamlar va maymunlar sutemizuvchilar."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Mild (adj)",
                uzWord = "mo’tadil",
                definition = "Mild weather is warm and pleasant.",
                enExample = "It was a mild winter.",
                uzExample = "Qish yengil o'tdi."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Name (v)",
                uzWord = "nomlamoq",
                definition = "To know and say what the name of someone or something is, or to give someone or something a name.",
                enExample = "How many world capitals can you name?",
                uzExample = "Nechta jahon poytaxtlarini ayta olasiz?"
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Name (n)",
                uzWord = "ism, nom",
                definition = "A word or set of words used for referring to a person or thing.",
                enExample = "What's the name of this flower?",
                uzExample = "Ushbu gulning nomi nima?"
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Origin (n)",
                uzWord = "kelib chiqish/paydo bo’lish",
                definition = "The place or moment at which something begins to exist.",
                enExample = "Meteorites may hold clues about the origin of life on Earth.",
                uzExample = "Meteoritlar Yer yuzidagi hayotning kelib chiqishi haqida izlar saqlashi mumkin."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Planet (n)",
                uzWord = "sayyora",
                definition = "A very large round object that moves around the Sun or around another star.",
                enExample = "Mars is sometimes known as the red planet.",
                uzExample = "Mars ba'zan qizil sayyora deb ataladi."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Preserve (v)",
                uzWord = "saqlamoq/muhofaza qilmoq",
                definition = "To take care of something in order to prevent it from being harmed or destroyed.",
                enExample = "We work hard to preserve historic buildings.",
                uzExample = "Tarixiy binolarni saqlash uchun qattiq ishlaymiz."
            ),
            ParentItem(
                unit = "36",
                type = "topic_vocabulary",
                enWord = "Recycle (v)",
                uzWord = "qayta ishlamoq",
                definition = "To treat waste materials so that they can be used again.",
                enExample = "Let's recycle those old bottles.",
                uzExample = "O'sha eski shishalarni qayta ishlaylik."
            )
        )
    }
}