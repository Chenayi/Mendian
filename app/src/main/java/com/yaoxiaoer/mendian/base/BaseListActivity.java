package com.yaoxiaoer.mendian.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.C;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Chenwy on 2018/2/1.
 */

public abstract class BaseListActivity<P extends IPresenter, D> extends BaseActivity<P> implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    private ListAdapter listAdapter;
    protected int pageNo;
    private boolean isLoadMoreFail;
    private View emptyView;
    private TextView tvEmpty;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initDatas() {
        initBundleDatas(getIntent().getExtras());
        refreshLayout.setEnabled(enableRefresh());
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);

        rv.setLayoutManager(getLayoutManager());
        listAdapter = new ListAdapter(BaseListActivity.this, new ArrayList<D>());
        initHeaderAndFooterView();
        rv.setAdapter(listAdapter);

        if (enableLoadMore()) {
            listAdapter.setEnableLoadMore(true);
            listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (!isLoadMoreFail) {
                        pageNo += 1;
                    }
                    refreshData();
                }
            }, rv);
        } else {
            listAdapter.bindToRecyclerView(rv);
        }
        if (enableEmptyView()) {
            emptyView = emptyView();
            listAdapter.setEmptyView(emptyView);
            listAdapter.setHeaderFooterEmpty(headerAndEmpty(), footerAndEmpty());
            hideEmptyView();
        }
        listAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onListItemClick(listAdapter.getItem(position), position);
            }
        });

        listAdapter.setOnItemChildClickListener(new BaseAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                onListItemChildClick(view.getId(), listAdapter.getItem(position), position);
            }
        });

        startLoading();
        pageNo = 1;
        refreshData();
    }

    protected abstract void initBundleDatas(Bundle bundle);

    protected abstract void initHeaderAndFooterView();

    protected abstract String getBarTitle();

    protected abstract void refreshData();

    protected abstract int itemLayout();

    protected abstract void myHolder(BaseViewHolder helper, D data);

    protected abstract void onListItemClick(D data, int position);

    protected abstract void onListItemChildClick(int viewId, D data, int position);

    protected abstract String getEmptyMsg();

    protected boolean headerAndEmpty() {
        return false;
    }

    protected boolean footerAndEmpty() {
        return false;
    }

    protected boolean enableRefresh() {
        return true;
    }

    protected boolean enableLoadMore() {
        return true;
    }

    protected void setEmptyOrErrorMsg(String errMsg) {
        if (enableEmptyView() && tvEmpty != null) {
            tvEmpty.setText(errMsg);
        }
    }

    protected void hideEmptyView() {
        if (enableEmptyView() && emptyView != null) {
            emptyView.setVisibility(View.INVISIBLE);
        }
    }

    protected void showEmptyView() {
        if (enableEmptyView() && emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    protected boolean enableEmptyView() {
        return true;
    }

    protected View emptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rv.getParent(), false);
        ScreenAdapterTools.getInstance().loadView((ViewGroup) emptyView);
        tvEmpty = emptyView.findViewById(R.id.tv_empty_tips);
        return emptyView;
    }


    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        refreshData();
    }

    protected View getHeaderOrFooterView(boolean isHeader, int layoutId) {
        View view = getLayoutInflater().inflate(layoutId, (ViewGroup) rv.getParent(), false);
        ScreenAdapterTools.getInstance().loadView((ViewGroup) view);
        if (isHeader) {
            listAdapter.addHeaderView(view);
        } else {
            listAdapter.addFooterView(view);
        }
        return view;
    }

    protected void notifyItemChanged(int position) {
        if (listAdapter != null) {
            listAdapter.notifyItemChanged(position);
        }
    }

    protected void remove(int position) {
        if (listAdapter != null) {
            listAdapter.remove(position);
        }
    }

    protected void clearAll() {
        if (listAdapter != null && listAdapter.getData().size() > 0) {
            listAdapter.getData().clear();
            listAdapter.notifyDataSetChanged();
        }
    }

    protected D getItem(int position) {
        D item = listAdapter.getItem(position);
        return item;
    }

    protected void scrollToFirst() {
        rv.scrollTo(0, 0);
    }

    protected int getDataSize() {
        return listAdapter.getData().size();
    }

    public void finishRefresh(List<D> datas) {
        refreshLayout.setRefreshing(false);
        isLoadMoreFail = false;
        listAdapter.setNewData(datas);
        if (datas.size() <= 0) {
            showEmptyView();
            setEmptyOrErrorMsg(getEmptyMsg());
        }
        if (enableLoadMore()) {
            listAdapter.disableLoadMoreIfNotFullPage();
            if (datas.size() < C.PAGE_SIZE) {
                listAdapter.loadMoreEnd(true);
            } else {
                listAdapter.loadMoreComplete();
            }
        }
    }

    public void finishLoadMore(List<D> datas) {
        refreshLayout.setRefreshing(false);
        isLoadMoreFail = false;
        listAdapter.addData(datas);
        if (datas.size() < C.PAGE_SIZE) {
            listAdapter.loadMoreEnd(true);
        } else {
            listAdapter.loadMoreComplete();
        }
    }

    /**
     * 处理错误
     *
     * @param code
     * @param msg
     */
    public void handleError(int code, String msg) {
        refreshLayout.setRefreshing(false);
        if (pageNo == 1) {
            ToastUtils.showShort(msg);
            if (getDataSize() <= 0 && enableEmptyView()) {
                showEmptyView();
                setEmptyOrErrorMsg(msg);
            }
        } else {
            isLoadMoreFail = true;
            listAdapter.loadMoreFail();
        }
    }


    private class ListAdapter extends BaseAdapter<D, BaseViewHolder> {
        private final WeakReference<BaseListActivity> mActivity;

        public ListAdapter(BaseListActivity activity, List<D> data) {
            super(itemLayout(), data);
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected void convert(BaseViewHolder helper, D data) {
            BaseListActivity activity = mActivity.get();
            activity.myHolder(helper, data);
        }
    }
}
