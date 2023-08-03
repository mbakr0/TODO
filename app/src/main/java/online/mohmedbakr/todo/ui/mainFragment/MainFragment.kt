package online.mohmedbakr.todo.ui.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import online.mohmedbakr.todo.R
import online.mohmedbakr.todo.database.TaskDao
import online.mohmedbakr.todo.databinding.FragmentFirstBinding
import online.mohmedbakr.todo.ui.MainActivity
import online.mohmedbakr.todo.util.setFadeVisible

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var taskDao :TaskDao
    private val viewModel: MainFragmentViewModel by viewModels{
        MainFragmentViewModelFactory(taskDao)
    }

    private lateinit var adapter:RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskDao = (activity as MainActivity).taskDao
        adapter = RecyclerViewAdapter(viewModel)

        val myAnim = AnimationUtils.loadAnimation(context, R.anim.fab_anim)
        val interpolator = BounceInterpolator()
        myAnim.interpolator = interpolator
        binding.fab.startAnimation(myAnim)

        binding.tasksList.adapter = adapter
        val touchHelperCallback = RecyclerViewAdapter.ListItemTouchHelper(adapter)
        val touchHelper = ItemTouchHelper(touchHelperCallback)
        touchHelper.attachToRecyclerView(binding.tasksList)


        binding.noDataTextView.setFadeVisible(false)
        viewModel.list.observe(viewLifecycleOwner){
            binding.noDataTextView.setFadeVisible(it.isEmpty())
            adapter.submitList(it)
        }



        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}