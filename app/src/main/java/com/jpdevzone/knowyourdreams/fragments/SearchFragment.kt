package com.jpdevzone.knowyourdreams.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.adapters.AlphabetAdapter
import com.jpdevzone.knowyourdreams.adapters.RecyclerViewAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), RecyclerViewAdapter.OnItemClickListener, AlphabetAdapter.OnLetterClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var mRecyclerView1: RecyclerView
    private lateinit var mRecyclerView2: RecyclerView
    private lateinit var mLayoutManager1: RecyclerView.LayoutManager
    private lateinit var mLayoutManager2: RecyclerView.LayoutManager
    private lateinit var mAdapter1: RecyclerView.Adapter<*>
    private lateinit var mAdapter2: RecyclerView.Adapter<*>
    private val dreams = Constants.getDreams()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val searchView = activity?.findViewById<SearchView>(R.id.searchView)
        searchView?.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val searchView = activity?.findViewById<SearchView>(R.id.searchView)
        searchView?.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mAdapter2 as RecyclerViewAdapter).filter.filter(newText)
                return false
            }
        })

        mRecyclerView1 = binding.alphabet
        mRecyclerView1.setHasFixedSize(true)
        mLayoutManager1 = LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL, false
        )
        mAdapter1 = AlphabetAdapter(alphabet(), this)

        mRecyclerView1.layoutManager = mLayoutManager1
        mRecyclerView1.adapter = mAdapter1

        mRecyclerView2 = binding.searchRecyclerView
        mRecyclerView2.setHasFixedSize(true)
        mRecyclerView2.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
        mLayoutManager2 = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAdapter2 = RecyclerViewAdapter(dreams, this)

        mRecyclerView2.layoutManager = mLayoutManager2
        mRecyclerView2.adapter = mAdapter2

        return binding.root
    }

    private fun alphabet(): ArrayList<String> {
        val list = ArrayList<String>()
        val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЮЯ".toCharArray()

        for (i in alphabet) {
            list.add("$i")
        }
        return list
    }

    override fun onItemClick(position: Int, item: String, definition: String) {
        val args = Bundle()
        args.putString("Item", item)
        args.putString("Definition", definition)

        val inflatedFragment = InflatedItemFragment()
        inflatedFragment.arguments = args
        val fm = requireActivity().supportFragmentManager

        inflatedFragment.show(fm, "inflatedItem")
    }

    override fun onLetterClick(letter: String) {
        when (letter) {
            "А" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0,0)}
            "Б" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(140,0)}
            "В" -> {}
            "Г" -> {}
            "Д" -> {}
            "Е" -> {}
            "Ж" -> {}
            "З" -> {}
            "И" -> {}
            "Й" -> {}
            "К" -> {}
            "Л" -> {}
            "М" -> {}
            "Н" -> {}
            "О" -> {}
            "П" -> {}
            "Р" -> {}
            "С" -> {}
            "Т" -> {}
            "У" -> {}
            "Ф" -> {}
            "Х" -> {}
            "Ц" -> {}
            "Ч" -> {}
            "Ш" -> {}
            "Щ" -> {}
            "Ъ" -> {}
            "Ю" -> {}
            "Я" -> {}
        }

    }

}
