package com.sofiamarchinskaya.hw1.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sofiamarchinskaya.hw1.types.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNoteInfoBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.viewmodels.NoteInfoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


/**
 * Фрагмент для отображения деталей о заметке
 */
class NoteInfoFragment : Fragment() {

    private lateinit var binding: FragmentNoteInfoBinding
    private val viewModel: NoteInfoViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteInfoBinding.inflate(inflater, container, false)
        arguments?.apply {
            viewModel.note.value =
                Note(getInt(Constants.ID), getString(Constants.TITLE), getString(Constants.TEXT))
        } ?: run {
            activity?.invalidateOptionsMenu()
            viewModel.note.value = Note(
                Constants.INVALID_ID,
                arguments?.getString(Constants.TITLE),
                arguments?.getString(Constants.TEXT)
            )
            viewModel.isNewNote = true
        }

        initEvents()
        viewModel.note.observe(viewLifecycleOwner) {
            binding.title.setText(viewModel.note.value?.title)
            binding.text.setText(viewModel.note.value?.body)
        }
        binding.text.addTextChangedListener {
            viewModel.setNoteText(it.toString())
        }
        binding.title.addTextChangedListener {
            viewModel.setNoteTitle(it.toString())
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (viewModel.isNewNote)
            inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                viewModel.checkNote()
                return true
            }
            R.id.load_json -> {
                viewModel.getJsonNote()
                return true
            }
            R.id.location -> {
                viewModel.onLocationItemClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isEmpty() ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED
                ) {
                    makeToast("You need to grant permission to access location")
                } else {
                    getCurrentLocation()
                }
            }
        }
    }

    private fun onSaveDisabled() {
        Toast.makeText(requireContext(), getString(R.string.empty_note), Toast.LENGTH_LONG).show()
    }

    private fun onSuccessfullySaved() {
        Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_LONG).show()
    }

    private fun createSaveDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val checkBoxView = layoutInflater.inflate(R.layout.check_box, null)
        val checkBox = checkBoxView.findViewById<CheckBox>(R.id.checkbox)
        val dialogFragment = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.dialog_title))
            setView(checkBoxView)
            setNegativeButton(
                getString(R.string.dialog_negative),
                null
            )
            setPositiveButton(getString(R.string.dialog_positive)) { _, _ ->
                lifecycleScope.launch {
                    viewModel.onSaveNote(checkBox.isChecked)
                }
            }
            create()
        }
        activity?.supportFragmentManager?.let { dialogFragment.show() }
    }

    private fun setLoadedNote(note: Note) {
        binding.title.setText(note.title)
        binding.text.setText(note.body)
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun hideProgressBar() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.title.visibility = View.VISIBLE
        binding.text.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.title.visibility = View.INVISIBLE
        binding.text.visibility = View.INVISIBLE
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun initEvents() {
        viewModel.onSaveSuccessEvent.observe(viewLifecycleOwner) {
            it ?: return@observe
            onSuccessfullySaved()
            activity?.sendBroadcast(Intent().apply {
                action = Constants.NOTE_SENT
                putExtra(Constants.TITLE, it.title)
                putExtra(Constants.TEXT, it.body)
            })
        }
        viewModel.onSaveAllowedEvent.observe(viewLifecycleOwner) {
            createSaveDialog()
        }
        viewModel.onSaveFailureEvent.observe(viewLifecycleOwner) {
            onSaveDisabled()
        }
        viewModel.onLoadFailureEvent.observe(viewLifecycleOwner) {
            makeToast(resources.getString(R.string.failed))
        }
        viewModel.onLoadSuccessEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                setLoadedNote(it)
            }
            makeToast(resources.getString(R.string.successfully_download))
        }
        viewModel.onShowProgressBarEvent.observe(viewLifecycleOwner) {
            showProgressBar()
        }
        viewModel.onHideProgressBarEvent.observe(viewLifecycleOwner) {
            hideProgressBar()
        }
        viewModel.onLoadLocationClickEvent.observe(viewLifecycleOwner) {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (checkPermissions()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    binding.text.setText(formatLocation(location.latitude, location.longitude))
                }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(), "Failed on getting current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun formatLocation(latitude: Double, longitude: Double): String {
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses: Address? =
                geocoder.getFromLocation(latitude, longitude, MAX_RESULTS).firstOrNull()
            return if (addresses != null) {
                "${addresses.thoroughfare ?: ""}, ${addresses.featureName ?: ""}," +
                        " ${addresses.locality ?: ""}, ${addresses.subAdminArea ?: ""}, ${addresses.adminArea ?: ""}, ${addresses.countryName ?: ""}"
            } else {
                resources.getString(R.string.not_defined)
            }
        } catch (e: Exception) {
            makeToast(
                resources.getString(R.string.fail_to_connect)
            )
            return resources.getString(R.string.not_defined)
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 1000
        private const val PERMISSION_ID = 42
        private const val MAX_RESULTS = 1

        fun newInstance(note: Note): NoteInfoFragment =
            NoteInfoFragment().apply {
                arguments = bundleOf(
                    Constants.TITLE to note.title,
                    Constants.TEXT to note.body,
                    Constants.ID to note.id
                )
            }
    }
}