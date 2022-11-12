package com.dima_shpiklirnyi.testingwork.Screens.ViewAndEditNotes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.dima_shpiklirnyi.testingwork.MAIN
import com.dima_shpiklirnyi.testingwork.MainActivity
import com.dima_shpiklirnyi.testingwork.Models.NotesModel
import com.dima_shpiklirnyi.testingwork.R
import com.dima_shpiklirnyi.testingwork.appCompanent
import com.dima_shpiklirnyi.testingwork.domain.ViewModel.NotesListViewModel
import com.dima_shpiklirnyi.testingwork.domain.ViewModel.NotesListViewModelFactory
import com.dima_shpiklirnyi.testingwork.domain.UseCase.Time
import com.dima_shpiklirnyi.testingwork.databinding.FragmentViewAndEditNotesBinding
import javax.inject.Inject


class ViewAndEditNotesFragment : Fragment(), MenuProvider {

    private lateinit var mBinding: FragmentViewAndEditNotesBinding
    private lateinit var currentNote: NotesModel
    private lateinit var saveBut: MenuItem
    private lateinit var time: Time
    private lateinit var viewModel: NotesListViewModel
    @Inject
    lateinit var vmFactoryEdit: NotesListViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appCompanent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentViewAndEditNotesBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = MAIN
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        currentNote = arguments?.getSerializable("notes") as NotesModel
        return mBinding.root
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_view_and_edit, menu)
        saveBut = menu.findItem(R.id.item_save)
        saveBut.isVisible = false
        textWatcher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.item_save -> {
                if (currentNote.description != mBinding.descriptionEdText.text.toString()
                    || currentNote.title != mBinding.titleEdText.text.toString()
                ) {
                    viewModel.editNotes(
                        NotesModel(
                            mBinding.titleEdText.text.toString(), time.getTime(),
                            mBinding.descriptionEdText.text.toString(), currentNote.id
                        )
                    )
                    MAIN.navController.popBackStack()
                } else MAIN.navController.popBackStack()
                true
            }
            else -> false
        }
    }
//Инициализация viewModel и отображение информации в заметках
    private fun init() {
        viewModel =
            ViewModelProvider(MAIN, vmFactoryEdit).get(
                NotesListViewModel::class.java
            )
        time = Time()
        mBinding.titleEdText.setText(currentNote.title)
        mBinding.descriptionEdText.setText(currentNote.description)

    }
    //Отслеживание изменения текста и отображение кнопки сохранения при его изменении. Конечно надо было зделать функции расширения но не успел (
    fun textWatcher() {
        mBinding.titleEdText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                saveBut.isVisible = true
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        mBinding.descriptionEdText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                saveBut.isVisible = true
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }


}