package com.dima_shpiklirnyi.testingwork.Screens.NotesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dima_shpiklirnyi.testingwork.Adapter.AdapterListNotes
import com.dima_shpiklirnyi.testingwork.MAIN
import com.dima_shpiklirnyi.testingwork.Models.NotesModel
import com.dima_shpiklirnyi.testingwork.R
import com.dima_shpiklirnyi.testingwork.databinding.FragmentNotesListBinding
import com.dima_shpiklirnyi.testingwork.domain.Interfaces.NoInternetFunc
import com.dima_shpiklirnyi.testingwork.domain.UseCase.ChekInternetConnection
import com.dima_shpiklirnyi.testingwork.domain.ViewModel.NotesListViewModel
import com.dima_shpiklirnyi.testingwork.domain.ViewModel.NotesListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class NotesListFragment : Fragment(), NoInternetFunc {
    private lateinit var mBinding: FragmentNotesListBinding
    private lateinit var viewModel: NotesListViewModel
    private lateinit var mAdapter: AdapterListNotes
    private lateinit var chekInternet: ChekInternetConnection
    private var isCompleteServerGetData = false
    private var listEmpty = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNotesListBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        mBinding.addNotesButton.setOnClickListener {
            viewModel.addNewNotes()
            mBinding.rvNotesList.smoothScrollToPosition(mAdapter.itemCount)
        }
    }

    private fun init() {
        //Проверка интернета
        chekInternet = ChekInternetConnection(MAIN, this)
        chekInternet.isChangeInternet()
        //Инициализация адаптера
        mAdapter = AdapterListNotes()
        mBinding.rvNotesList.adapter = mAdapter
        //Подключаем viewModel
        viewModel =
            ViewModelProvider(MAIN, NotesListViewModelFactory(MAIN)).get(
                NotesListViewModel::class.java
            )
        //Узнаем о наличии данных в базе данных.
        viewModel.isEmpty.observe(MAIN) {
            if (it) {
                mBinding.emptyListTextView.visibility = View.VISIBLE
                mBinding.firstProgressBar.visibility = View.GONE
            }
        }
        //Передаем данные в адаптер.
        viewModel.liveDataList.observe(MAIN) { list ->
            if (!list.isEmpty()) {
                listEmpty = false
                mBinding.firstProgressBar.visibility = View.GONE
                mBinding.emptyListTextView.visibility = View.GONE
            }
            mAdapter.submitList(list) {}
            mBinding.rvNotesList.smoothScrollToPosition(mAdapter.itemCount)
        }
        //Инициализация прогресс бара
        initProgressBar()
        touchHelper()
    }
//Функции для мониторинга состояния интернета
    override fun internet(status: Boolean) {
        if (status) {
            MAIN.runOnUiThread(Runnable {
                internetView()
                // Запускаем получения данных с сервера при наличии интернета
                viewModel.startReciveData(false)
            })
        } else {
            MAIN.runOnUiThread(Runnable {
                noInternetView()
                //Остановка получения данных при отключении интернета
                viewModel.stopReciveData(true)
                view?.let { Snackbar.make(it, getString(R.string.no_internet), 10000).show() }
            })

        }
    }

    override fun onResume() {
        super.onResume()
        if (chekInternet.isOnline()) internetView()
        else noInternetView()
    }

    fun initProgressBar() {
        //Функции состояния прогресс бара
        viewModel.itemCountServer.observe(MAIN) {
            if (it.toInt() != 0) {
                MAIN.mBinding.secondProgressBar.max = it.toInt()
            }
        }

        viewModel.getItemServer.observe(MAIN) {
            MAIN.mBinding.secondProgressBar.progress = it
            if (MAIN.secondProgressBar.max == it) {
                MAIN.mBinding.secondProgressBar.visibility = View.GONE
                isCompleteServerGetData = true
            }
        }
        viewModel.stopGetServerData.observe(MAIN) {
            if (it || isCompleteServerGetData) MAIN.mBinding.secondProgressBar.visibility =
                View.GONE
            else MAIN.mBinding.secondProgressBar.visibility = View.VISIBLE
        }
    }
// Удаление заметок свайпом
    fun touchHelper(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteItem(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(mBinding.rvNotesList)
    }

    private fun noInternetView() {
        if (listEmpty) {
            mBinding.internetText.visibility = View.VISIBLE
            mBinding.addNotesButton.visibility = View.GONE
            mBinding.firstProgressBar.visibility = View.GONE
            mBinding.rvNotesList.visibility = View.GONE
        }
    }

    private fun internetView() {
        mBinding.internetText.visibility = View.GONE
        mBinding.addNotesButton.visibility = View.VISIBLE
        mBinding.rvNotesList.visibility = View.VISIBLE
    }
//Перехват нажатий из адаптера
    companion object {
        fun onClick(notes: NotesModel) {
            val bundle = Bundle()
            bundle.putSerializable("notes", notes)
            MAIN.navController.navigate(
                R.id.action_notesListFragment2_to_viewAndEditNotesFragment,
                bundle
            )
        }
    }
}