package com.example.words;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.words.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ListAdapter<Word,MyAdapter.MyViewHolder> {
    private boolean useCardView;
    private com.example.words.WordViewModel wordViewModel;

    public MyAdapter(boolean useCardView, com.example.words.WordViewModel wordViewModel) {
        super(new DiffUtil.ItemCallback<Word>() {
            @Override
            public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return (oldItem.getWord().equals(newItem.getWord())
                        && oldItem.getChineseMeaning().equals(newItem.getChineseMeaning())
                        && oldItem.isChineseInvisible() == newItem.isChineseInvisible());
            }
        });
        this.useCardView = useCardView;
        this.wordViewModel = wordViewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (useCardView) {
            itemView = layoutInflater.inflate(R.layout.cell_card_2, parent, false);
        } else {
            itemView = layoutInflater.inflate(R.layout.cell_normal_2, parent, false);
        }
        final MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=" + holder.textViewEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Word word = (Word) holder.itemView.getTag(R.id.word_for_view_holder);
                if (b) {
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setChineseInvisible(true);
                    wordViewModel.updateWords(word);
                } else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setChineseInvisible(false);
                    wordViewModel.updateWords(word);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final com.example.words.Word word = getItem(position);
        holder.itemView.setTag(R.id.word_for_view_holder, word);
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        if (word.isChineseInvisible()) {
            holder.textViewChinese.setVisibility(View.GONE);   //不显示同时不占据位置
            holder.aSwitchChineseInvisible.setChecked(true);
        } else {
            holder.textViewChinese.setVisibility(View.VISIBLE);   //正常显示
            holder.aSwitchChineseInvisible.setChecked(false);
        }
        //提高性能，onBindViewHolder经常被调用每次都要new监听
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=" + holder.textViewEnglish.getText());
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(uri);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
//        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    holder.textViewChinese.setVisibility(View.GONE);
//                    word.setChineseInvisible(true);
//                    wordViewModel.updateWords(word);
//                } else {
//                    holder.textViewChinese.setVisibility(View.VISIBLE);
//                    word.setChineseInvisible(false);
//                    wordViewModel.updateWords(word);
//                }
//            }
//        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.textViewNumber.setText(String.valueOf(holder.getAdapterPosition() + 1));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewEnglish, textViewChinese;
        Switch aSwitchChineseInvisible;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            aSwitchChineseInvisible = itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
