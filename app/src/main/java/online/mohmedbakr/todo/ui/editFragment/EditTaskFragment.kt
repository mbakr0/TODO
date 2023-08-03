package online.mohmedbakr.todo.ui.editFragment

import android.animation.Animator
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import online.mohmedbakr.todo.R
import online.mohmedbakr.todo.alarm.Alarm
import online.mohmedbakr.todo.database.Task
import online.mohmedbakr.todo.databinding.FragmentSecondBinding
import online.mohmedbakr.todo.ui.MainActivity
import online.mohmedbakr.todo.util.getDate
import online.mohmedbakr.todo.util.getTime
import java.util.Calendar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private var _binding: FragmentSecondBinding? = null

    private lateinit var calendar: Calendar


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reminderSwitch.setOnClickListener {
            showDate()
        }


        calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE) + 1


        binding.taskDate.setOnClickListener {
            binding.taskDate.text = null
            val datePicker = DatePickerDialog(requireContext(),this,year,month,day)
            datePicker.datePicker.minDate = System.currentTimeMillis()
            datePicker.show()
        }
        binding.taskTime.setOnClickListener {
            binding.taskTime.text = null
            val timePicker = TimePickerDialog(requireContext(),this,hour,minute,false)
            timePicker.show()

        }

        binding.addTaskButton.setOnClickListener {
            when {
                binding.taskTitle.length() == 0 -> binding.taskTitle.error = getString(R.string.error_text)
                binding.taskTitle.text.toString().trim { it <= ' ' }.isEmpty() -> binding.taskTitle.error = getString(
                    R.string.error_spaces
                )
                else -> {
                    val taskDao = (activity as MainActivity).taskDao
                    val title = binding.taskTitle.text.toString()
                    var date:Long = 0

                    if (binding.reminderContainer.isVisible && binding.taskDate.length() != 0 && binding.taskTime.length() != 0)
                    {
                        date = calendar.timeInMillis
                        Alarm.createAlarm(requireContext(),Task(title = title, date = date))
                    }
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            taskDao.insert(Task(title = title, date = date))
                        }
                    }

                    findNavController().navigateUp()
                }
            }
            hideKeyboard()
        }

    }


    private fun showDate(){
            if (binding.reminderSwitch.isChecked) {
                hideKeyboard()
                binding.reminderContainer.animate().alpha(1.0f).setDuration(500).setListener(
                    object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                            binding.reminderContainer.visibility = View.VISIBLE
                        }
                        override fun onAnimationEnd(animation: Animator) {}
                        override fun onAnimationCancel(animation: Animator) {}
                        override fun onAnimationRepeat(animation: Animator) {}
                    }
                )
            } else {
                binding.reminderContainer.animate().alpha(0.0f).setDuration(500).setListener(
                    object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {}

                        override fun onAnimationEnd(animation: Animator) {
                            binding.reminderContainer.visibility = View.INVISIBLE
                        }
                        override fun onAnimationCancel(animation: Animator) {}
                        override fun onAnimationRepeat(animation: Animator) {}
                    }
                )
                binding.taskDate.text = null
                binding.taskTime.text = null
            }

    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        binding.taskDate.setText(getDate(calendar.timeInMillis))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)


        binding.taskTime.setText(getTime(calendar.timeInMillis))


    }
}