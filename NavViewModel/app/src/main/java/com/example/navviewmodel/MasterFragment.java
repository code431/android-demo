package com.example.navviewmodel;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.navviewmodel.databinding.FragmentMasterBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class MasterFragment extends Fragment {


    public MasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel myViewModel;
        myViewModel = new ViewModelProvider(getActivity(),new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        FragmentMasterBinding binding;
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_master,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(getActivity());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_masterFragment_to_detailFragment);
            }
        });

        binding.seekBar.setProgress(myViewModel.getNumber().getValue());

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                myViewModel.getNumber().setValue(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_master, container, false);
    }

}
