package com.example.words;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordsFragment extends Fragment {
    private WordViewModel wordViewModel;
    private MyAdapter myAdapter1, myAdapter2;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private LiveData<List<Word>> filteredWords;
    private static final String VIEW_TYPE_SHP = "view_type_shp";
    private static final String IS_USING_CARD_VIEW = "is_using_card_view";
    private List<Word> allWords;
    private boolean undoAction;
    private DividerItemDecoration dividerItemDecoration;

    public WordsFragment() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearData:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("清空数据");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordViewModel.deleteAllWords();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.switchViewType:
                SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
                boolean view_Type = shp.getBoolean(IS_USING_CARD_VIEW, false);
                SharedPreferences.Editor editor = shp.edit();
                if (view_Type) {
                    recyclerView.setAdapter(myAdapter1);
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    editor.putBoolean(IS_USING_CARD_VIEW, false);
                } else {
                    recyclerView.setAdapter(myAdapter2);
                    recyclerView.removeItemDecoration(dividerItemDecoration);
                    editor.putBoolean(IS_USING_CARD_VIEW, true);
                }
                editor.apply();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String pattern = s.trim();
                filteredWords.removeObservers(requireActivity());     //移除原有的观察Observer
                filteredWords = wordViewModel.getWordsWithPattern(pattern);
                filteredWords.observe(requireActivity(), new Observer<List<Word>>() {       //getViewLifecycleOwner防止多个observe监听
                    @Override
                    public void onChanged(List<Word> words) {
                        int temp = myAdapter1.getItemCount();
                        allWords = words;
                        if (temp != words.size()) {
                            myAdapter1.submitList(words);
                            myAdapter2.submitList(words);
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordViewModel = ViewModelProviders.of(requireActivity()).get(WordViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter1 = new MyAdapter(false, wordViewModel);
        myAdapter2 = new MyAdapter(true, wordViewModel);
        recyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                super.onAnimationFinished(viewHolder);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                    for (int i = firstPosition; i < lastPosition; i++) {
                        MyAdapter.MyViewHolder holder = (MyAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (holder != null) {
                            holder.textViewNumber.setText(String.valueOf(i + 1));
                        }
                    }
                }
            }
        });

        SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
        final boolean view_Type = shp.getBoolean(IS_USING_CARD_VIEW, false);
        dividerItemDecoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        if (view_Type) {
            recyclerView.setAdapter(myAdapter2);
        } else {
            recyclerView.setAdapter(myAdapter1);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }

        //recyclerView.setAdapter(myAdapter1);
        filteredWords = wordViewModel.getAllWordsLive();
        filteredWords.observe(requireActivity(), new Observer<List<Word>>() {     //getViewLifecycleOwner防止多个observe监听
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapter1.getItemCount();
                allWords = words;
                if (temp != words.size()) {
                    if (temp < words.size() && !undoAction) {
                        recyclerView.smoothScrollBy(0, -200);
                    }
                    undoAction = false;
                    myAdapter1.submitList(words);
                    myAdapter2.submitList(words);
                    //myAdapter1.notifyItemInserted(0);
                    //myAdapter1.notifyDataSetChanged();
                    //myAdapter2.notifyDataSetChanged();
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Word wordFrom = allWords.get(viewHolder.getAdapterPosition());
                Word wordTo = allWords.get(target.getAdapterPosition());
                int idTemp = wordFrom.getId();
                wordFrom.setId(wordFrom.getId());
                wordTo.setId(idTemp);
                wordViewModel.updateWords(wordFrom, wordTo);
                myAdapter1.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                myAdapter2.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Word wordToDelete = allWords.get(viewHolder.getAdapterPosition());
                wordViewModel.deleteWords(wordToDelete);
                Snackbar.make(requireActivity().findViewById(R.id.wordsFragmentView), "删除了一个词汇", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                undoAction = true;
                                wordViewModel.insertWords(wordToDelete);
                            }
                        })
                        .show();

            }
        }).attachToRecyclerView(recyclerView);

        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });
    }
}
