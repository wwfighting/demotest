package com.ww.administrator.demotest.catefragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.okhttp.Request;
import com.ww.administrator.demotest.BaseFragment;
import com.ww.administrator.demotest.R;
import com.ww.administrator.demotest.adapter.OrderFragmentAdapter;
import com.ww.administrator.demotest.cityselect.MyApp;
import com.ww.administrator.demotest.model.OrderResultInfo;
import com.ww.administrator.demotest.pay.PayActivity;
import com.ww.administrator.demotest.util.Constants;
import com.ww.administrator.demotest.util.HttpUtil;

/**
 * Created by Administrator on 2016/7/28.
 */
public class OrderFragment extends BaseFragment {

    String uid = "-1";
    SwipeRefreshLayout msrlOrder;
    RecyclerView mrvOrder;
    TextView mtvOrderNo;
    ProgressWheel mpbOrder;

    OrderFragmentAdapter mAdapter;
    Gson mGson = new Gson();
    LinearLayoutManager manager;

    @Override
    protected void getArgs() {
        uid = ((MyApp) getActivity().getApplicationContext()).getUser().getId();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_layout;
    }

    @Override
    protected void initViews(View view) {
        msrlOrder = (SwipeRefreshLayout) view.findViewById(R.id.srl_order);
        mrvOrder = (RecyclerView) view.findViewById(R.id.rv_order);
        mtvOrderNo = (TextView) view.findViewById(R.id.tv_order_no);
        mpbOrder = (ProgressWheel) view.findViewById(R.id.pb_common);
        mtvOrderNo.setVisibility(View.GONE);
        mpbOrder.setVisibility(View.VISIBLE);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mrvOrder.setLayoutManager(manager);
        msrlOrder.setColorSchemeResources(R.color.day_colorPrimary, R.color.day_colorPrimaryDark,
                R.color.day_colorAccent);
    }

    @Override
    protected void doBusiness() {
        loadDatas();
        refreshDatas();
    }

    /**
     * 刷新
     */
    public void refreshDatas(){
        msrlOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
                msrlOrder.setRefreshing(false);
            }
        });
    }

    private void loadDatas() {
        HttpUtil.postAsyn(Constants.BASE_URL + "get_userorder_list.php", new HttpUtil.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mpbOrder.setVisibility(View.GONE);
                mrvOrder.setVisibility(View.GONE);
                mtvOrderNo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(String response) {
                mpbOrder.setVisibility(View.GONE);
                OrderResultInfo info = mGson.fromJson(response, OrderResultInfo.class);
                if (info.getCode().equals("200")){
                    mtvOrderNo.setVisibility(View.GONE);
                    mrvOrder.setVisibility(View.VISIBLE);
                    mAdapter = new OrderFragmentAdapter(getActivity(),info);
                    mrvOrder.setAdapter(mAdapter);
                    mAdapter.setOnPayClickListener(new OrderFragmentAdapter.OnPayClickListener() {
                        @Override
                        public void getItem(OrderResultInfo.Databean info) {
                            Intent intent = new Intent(getActivity(), PayActivity.class);
                            intent.putExtra("title", info.getGoodsname());
                            intent.putExtra("ordNum", Integer.parseInt(info.getSuperbillid()));
                            intent.putExtra("payMoney", info.getSchedprice());
                            startActivityForResult(intent, 100);
                        }
                    });
                }else {
                    mrvOrder.setVisibility(View.GONE);
                    mtvOrderNo.setVisibility(View.VISIBLE);
                    mtvOrderNo.setText(info.getInfo());
                }

            }
        }, new HttpUtil.Param[]{
                new HttpUtil.Param("uid", uid),
                new HttpUtil.Param("action", "all")
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200){
            loadDatas();
            refreshDatas();
        }
    }
}
