package ysn.codepolitan_jadwalkeretaapi.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ysn.codepolitan_jadwalkeretaapi.R;

/**
 * Created by root on 15/02/17.
 */

public class AdapterKotaAsalTujuan extends RecyclerView.Adapter<AdapterKotaAsalTujuan.ItemKotaAsalTujuanViewHolder> {

    private static final String TAG = "AdapterTAG";
    List<String> listNamaKotaStasiun;
    List<String> listValueNamaKotaStasiun;
    List<String> listNamaKotaBesar;
    OnItemClickListener onItemClickListener;

    public AdapterKotaAsalTujuan(List<String> listNamaKotaStasiun,
                                 List<String> listValueNamaKotaStasiun,
                                 List<String> listNamaKotaBesar,
                                 OnItemClickListener onItemClickListener) {
        this.listNamaKotaStasiun = listNamaKotaStasiun;
        this.listValueNamaKotaStasiun = listValueNamaKotaStasiun;
        this.listNamaKotaBesar = listNamaKotaBesar;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public ItemKotaAsalTujuanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kota_asal_tujuan, null);
        return new ItemKotaAsalTujuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemKotaAsalTujuanViewHolder holder, int position) {
        holder.textViewNamaKotaStasiun.setText(listNamaKotaStasiun.get(position));
        holder.textViewNamaKotaBesar.setText(listNamaKotaBesar.get(position));
        holder.onClick(listValueNamaKotaStasiun.get(position), listNamaKotaStasiun.get(position));
    }

    @Override
    public int getItemCount() {
        return listNamaKotaStasiun.size();
    }

    public void refreshData(Map<String, List<String>> mapListData) {
        listNamaKotaStasiun = mapListData.get("namaKotaStasiun");
        listValueNamaKotaStasiun = mapListData.get("valueNamaKotaStasiun");
        listNamaKotaBesar = mapListData.get("namaKotaBesar");
    }

    public class ItemKotaAsalTujuanViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayoutItem;
        private TextView textViewNamaKotaStasiun;
        private TextView textViewNamaKotaBesar;

        public ItemKotaAsalTujuanViewHolder(View itemView) {
            super(itemView);
            relativeLayoutItem = (RelativeLayout) itemView.findViewById(R.id.relative_layout_item_kota_asal_tujuan);
            textViewNamaKotaStasiun = (TextView) itemView.findViewById(R.id.text_view_nama_kota_stasiun_item_kota_asal_tujuan);
            textViewNamaKotaBesar = (TextView) itemView.findViewById(R.id.text_view_nama_kota_besar_item_kota_asal_tujuan);
        }

        public void onClick(final String value, final String kota) {
            relativeLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(value, kota);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(String valueKota, String kotaName);
    }

}
