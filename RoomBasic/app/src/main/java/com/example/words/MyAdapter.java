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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoder> {
    private List<Word> allWords = new ArrayList<>();
    private boolean useCardView;
    private WordViewModel wordViewModel;

    public MyAdapter(boolean useCardView, WordViewModel wordViewModel) {
        this.useCardView = useCardView;
        this.wordViewModel = wordViewModel;
    }

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (useCardView) {
            itemView = layoutInflater.inflate(R.layout.cell_card_2,parent,false);
        } else {
            itemView = layoutInflater.inflate(R.layout.cell_normal_2,parent,false);
        }
        return new MyViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, int position) {
        final Word word = allWords.get(position);
        holder.textViewNubmer.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(null);  //调用setChecked时会启用监听器，防止直接执行下面代码
        if (word.isChineseInvisible()){
            holder.textViewChinese.setVisibility(View.GONE);   //不显示同时不占据位置
            holder.aSwitchChineseInvisible.setChecked(true);
        } else {
            holder.textViewChinese.setVisibility(View.VISIBLE);   //正常显示
            holder.aSwitchChineseInvisible.setChecked(false);
        }
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
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    static class MyViewHoder extends RecyclerView.ViewHolder{
        TextView textViewNubmer,textViewEnglish,textViewChinese;
        Switch aSwitchChineseInvisible;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            textViewNubmer = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            aSwitchChineseInvisible = itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
