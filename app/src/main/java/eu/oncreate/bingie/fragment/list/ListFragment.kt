package eu.oncreate.bingie.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import eu.oncreate.bingie.R
import eu.oncreate.bingie.fragment.base.BaseFragment
import eu.oncreate.bingie.fragment.details.DetailsFragment
import eu.oncreate.bingie.fragment.details.InitialDetailsState
import eu.oncreate.bingie.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ListViewModel.Factory

    private val viewModel: ListViewModel by fragmentViewModel()

    private lateinit var controller: ListController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        controller = ListController { searchResultItem, view, view2 ->
            val show = searchResultItem.searchResultItem.show
            val extras = FragmentNavigatorExtras(
                view to DetailsFragment.getTransitionNamePicture(show.traktId),
                view2 to DetailsFragment.getTransitionNameRatingBar(show.traktId)
            )
            navigator
                .navigate(
                    R.id.action_list_to_details,
                    bundleOf(MvRx.KEY_ARG to InitialDetailsState(searchResultItem.searchResultItem.show.traktId.toString())),
                    null,
                    extras
                )
        }
        searchList.layoutManager = LinearLayoutManager(requireContext())
        searchList.setController(controller)

        postponeEnterTransition()

        searchList.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery.hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.queryChanged(newText.orEmpty())
                return true
            }
        })

        listSettings.setOnClickListener {
            Toast.makeText(requireContext(), "Not yet", Toast.LENGTH_SHORT).show()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        listLoadindIndicator.isVisible = state.searchResult is Loading
        controller.setData(Pair(state.data, state.searchResult is Loading))
    }
}
