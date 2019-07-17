package com.justin.capturetest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.justin.capturetest.R;
import com.justin.capturetest.databinding.ItemBoardBinding;
import com.justin.capturetest.models.Board;

import java.util.List;

public class PinBoardAdapter extends RecyclerView.Adapter<PinBoardAdapter.PinBoardHolder> {


    private List<Board> mBoardInfList;
    private Context mContext;
    private PinClickListener mPinClickListener;

    public PinBoardAdapter(Context context, List<Board> mBoardInfList ) {
        this.mContext = context;
        this.mBoardInfList = mBoardInfList;
        this.mPinClickListener = (PinClickListener)context;
    }


    public interface PinClickListener{
        void onPinClick(Board board);
    }


    @NonNull
    @Override
    public PinBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBoardBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_board, parent, false);
        return new PinBoardHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PinBoardHolder holder, int position) {
               holder.setData(mBoardInfList.get(position),mPinClickListener);

    }

    @Override
    public int getItemCount() {
        return mBoardInfList !=null ? mBoardInfList.size() : 0;
    }

    public class PinBoardHolder extends RecyclerView.ViewHolder {

        private final ItemBoardBinding binding ;

        public PinBoardHolder(@NonNull ItemBoardBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void setData(Board board, PinClickListener mPinClickListener) {
            binding.setBoardInfo(board);
            binding.setCallback(mPinClickListener);
        }
    }
}
