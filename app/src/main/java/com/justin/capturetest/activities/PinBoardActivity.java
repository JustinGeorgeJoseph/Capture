package com.justin.capturetest.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.justin.capturetest.utils.ItemOffsetDecoration;
import com.justin.capturetest.R;
import com.justin.capturetest.adapters.PinBoardAdapter;
import com.justin.capturetest.databinding.ActivityMainBinding;
import com.justin.capturetest.di.utils.ViewModelFactory;
import com.justin.capturetest.models.Board;
import com.justin.capturetest.viewmodels.PinBoardViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


public class PinBoardActivity extends AppCompatActivity implements LifecycleOwner,
        SwipeRefreshLayout.OnRefreshListener, PinBoardAdapter.PinClickListener {

    private static String TAG = "TAG_JUSTIN";

    private ActivityMainBinding binding = null;
    private ArrayList<Board> mBoardInfoList = null;
    private PinBoardAdapter mPinBoardAdapter = null;
    private PinBoardViewModel mViewModel = null;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(PinBoardActivity.this, R.layout.activity_main);
        initView();
        mViewModel = ViewModelProviders.of(this,  viewModelFactory).get(PinBoardViewModel.class);
        getPinBoardData(mViewModel);
    }

    private void initView() {
        mBoardInfoList = new ArrayList<>();
        mPinBoardAdapter = new PinBoardAdapter(this, mBoardInfoList);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        binding.recyclerView.addItemDecoration(new ItemOffsetDecoration(PinBoardActivity.this,R.dimen.item_offset));
        binding.recyclerView.setAdapter(mPinBoardAdapter);
        binding.swipeRefresh.setOnRefreshListener(this);
    }

    private void getPinBoardData(PinBoardViewModel viewModel){
        Log.d(TAG, "getPinBoardData: ");
        viewModel.getPinBoardObservable().observe(this, new Observer<List<Board>>() {
            @Override
            public void onChanged(List<Board> boards) {
                binding.swipeRefresh.setRefreshing(false);
                if (boards == null){
                    Toast.makeText(PinBoardActivity.this,"No data available!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onChanged: ");
                if (mBoardInfoList != null) {
                    mBoardInfoList.clear();
                    mBoardInfoList.addAll(boards);
                    mPinBoardAdapter.notifyDataSetChanged();
                }

                if (mBoardInfoList.size() <= 0){
                    Toast.makeText(PinBoardActivity.this,"No data available!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        if(binding.swipeRefresh.isRefreshing()){
            mBoardInfoList.clear();
            mPinBoardAdapter.notifyDataSetChanged();
            mViewModel.getRefreshPiBoardData();
        }
    }

    @Override
    public void onPinClick(Board board) {
        Toast.makeText(PinBoardActivity.this, "Position :"+board.getUser().getName(),Toast.LENGTH_SHORT).show();
    }
}
