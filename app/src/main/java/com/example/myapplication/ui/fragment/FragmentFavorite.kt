package com.example.myapplication.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.callbacks.Callbacks
import com.example.myapplication.callbacks.CocktailDao
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.models.Fav
import com.example.myapplication.ui.activities.CocktailActivity
import com.example.myapplication.utils.Commons
import com.revosleap.simpleadapter.SimpleAdapter
import com.revosleap.simpleadapter.SimpleCallbacks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_fav.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentFavorite : androidx.fragment.app.Fragment(), Callbacks {

    private var favsList = mutableListOf<Any>()
    private var favs: RecyclerView? = null
    private var favsAdapter: SimpleAdapter? = null
    private var favoritesDb: AppDatabase? = null
    private var favoritesDao: CocktailDao? = null

    private val callbacks = object : SimpleCallbacks {
        override fun bindView(view: View, item: Any, position: Int) {
            item as Fav
            val drinkImage = view.fav_image
            val drinkName = view.fav_name

            Picasso.get()
                .load(item.drinkPhoto)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(drinkImage)
            drinkName.text = item.drinkName
        }

        override fun onViewClicked(view: View, item: Any, position: Int) {
            item as Fav
            val i = Intent(context, CocktailActivity::class.java).apply {
                putExtra(Commons.DRINK_ID, item.drinkId)
            }
            context!!.startActivity(i)
        }

        override fun onViewLongClicked(it: View?, item: Any, position: Int) {
            TODO("Not yet implemented")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favs, container, false)
        favoritesDb = AppDatabase.getInstance(context)
        favs = rootView.findViewById(R.id.favs_list)
        favsAdapter = SimpleAdapter(R.layout.item_fav, callbacks)
        setHasOptionsMenu(true)
        refreshFavs()
        favs!!.apply {
            adapter = favsAdapter
            hasFixedSize()
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        }
        favsAdapter!!.changeItems(favsList)
        return rootView
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val clearItem: MenuItem? = menu.findItem(R.id.clear_favorites)
        if (favsList.isEmpty()) clearItem!!.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.favorite_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_favorites -> {
                favoritesDao!!.clearFavorites()
                return true
            }
        }
        requireActivity().invalidateOptionsMenu()
        return super.onOptionsItemSelected(item)
    }

    override fun onRemoveClicked(name: String?) {
        val f: Fav = (favoritesDao!!.getOne(drinkName = name))[0]!!
        Toast.makeText(context, "Removing", Toast.LENGTH_SHORT).show()
        favoritesDao!!.removeFromFavs(f)
        refreshFavs()
    }

    override fun onTitleFound(name: String?) {}

    private fun refreshFavs() {
        GlobalScope.launch {
            favoritesDao = favoritesDb!!.coctailDao()
            favsList = favoritesDao!!.getFavs() as MutableList<Any>
        }
        favsAdapter!!.changeItems(favsList)
    }
}