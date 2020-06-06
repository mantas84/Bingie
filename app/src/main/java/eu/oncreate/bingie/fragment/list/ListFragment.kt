package eu.oncreate.bingie.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import eu.oncreate.bingie.R
import eu.oncreate.bingie.fragment.base.BaseFragment
import eu.oncreate.bingie.fragment.details.InitialDetailsState
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber
import javax.inject.Inject

class ListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ListViewModel.Factory

    private val viewModel: ListViewModel by fragmentViewModel()

    private lateinit var controller: ListController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // todo Add to baseFragment??
        controller = ListController(requireContext()) { searchResultItem ->
            navigator
                .navigate(
                    R.id.action_list_to_details,
                    bundleOf(MvRx.KEY_ARG to InitialDetailsState(searchResultItem))
                )
        }
        searchList.layoutManager = LinearLayoutManager(requireContext())
        searchList.setController(controller)
        invalidate()
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
        searchQuery.doOnTextChanged { text, start, count, after ->
            viewModel.queryChanged(text?.toString().orEmpty())
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        Timber.d("state $state")
        controller.setData(state.data)
    }
}
