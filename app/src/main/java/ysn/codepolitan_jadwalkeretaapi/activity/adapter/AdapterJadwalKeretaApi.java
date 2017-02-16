package ysn.codepolitan_jadwalkeretaapi.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ysn.codepolitan_jadwalkeretaapi.R;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.Datum;

/**
 * Created by root on 16/02/17.
 */

public class AdapterJadwalKeretaApi extends RecyclerView.Adapter<AdapterJadwalKeretaApi.ViewHolder> {

    public static final int ITEM_HEADER = 1;
    public static final int ITEM_CONTENT = 2;
    public static final int ITEM_FOOTER = 3;

    List<Datum> listDatum;
    List<Integer> listViewType;
    String kotaAsal;
    String kotaTujuan;

    public AdapterJadwalKeretaApi(List<Datum> listDatum,
                                  List<Integer> listViewType,
                                  String kotaAsal,
                                  String kotaTujuan) {
        this.listDatum = listDatum;
        this.listViewType = listViewType;
        this.kotaAsal = kotaAsal;
        this.kotaTujuan = kotaTujuan;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal_kereta_api_header, null);
            return new ItemJadwalKeretaApiHeaderViewHolder(view);
        } else if (viewType == ITEM_CONTENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal_kereta_api, null);
            return new ItemJadwalKeretaApiViewHolder(view);
        } else if (viewType == ITEM_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal_kereta_api_footer, null);
            return new ItemJadwalKeretaApiFooterViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = listViewType.get(position);
        if (viewType == ITEM_HEADER || viewType == ITEM_FOOTER) {
            //  nothing to do in here
        } else if (viewType == ITEM_CONTENT) {
            ItemJadwalKeretaApiViewHolder itemJadwalKeretaApiViewHolder = (ItemJadwalKeretaApiViewHolder) holder;
            Datum datum = listDatum.get(position);

            itemJadwalKeretaApiViewHolder.textViewNamaKeretaApi.setText(datum.getKereta().getName());
            itemJadwalKeretaApiViewHolder.textViewKelasSubKelas.setText(datum.getKereta().getClass_() + " - " + datum.getHarga().getSubclass());
            itemJadwalKeretaApiViewHolder.textViewHargaTiket.setText("Rp " + datum.getHarga().getRp());
            itemJadwalKeretaApiViewHolder.textViewJamBerangkat.setText(datum.getBerangkat().getJam());
            itemJadwalKeretaApiViewHolder.textViewTanggalBerangkat.setText(datum.getBerangkat().getTanggal());
            itemJadwalKeretaApiViewHolder.textViewKotaAsal.setText(kotaAsal);
            itemJadwalKeretaApiViewHolder.textViewDurasi.setText("Durasi " + datum.getDurasi());
            itemJadwalKeretaApiViewHolder.textViewJamDatang.setText(datum.getDatang().getJam());
            itemJadwalKeretaApiViewHolder.textViewTanggalDatang.setText(datum.getDatang().getTanggal());
            itemJadwalKeretaApiViewHolder.textViewKotaTujuan.setText(kotaTujuan);
        }
    }

    @Override
    public int getItemCount() {
        return listDatum.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listViewType.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemJadwalKeretaApiHeaderViewHolder extends ViewHolder {

        public ItemJadwalKeretaApiHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemJadwalKeretaApiFooterViewHolder extends ViewHolder {

        public ItemJadwalKeretaApiFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemJadwalKeretaApiViewHolder extends ViewHolder {

        private TextView textViewNamaKeretaApi;
        private TextView textViewKelasSubKelas;
        private TextView textViewHargaTiket;
        private TextView textViewJamBerangkat;
        private TextView textViewTanggalBerangkat;
        private TextView textViewKotaAsal;
        private TextView textViewDurasi;
        private TextView textViewJamDatang;
        private TextView textViewTanggalDatang;
        private TextView textViewKotaTujuan;

        public ItemJadwalKeretaApiViewHolder(View itemView) {
            super(itemView);
            textViewNamaKeretaApi = (TextView) itemView.findViewById(R.id.text_view_nama_kereta_api_item_jadwal_kereta_api);
            textViewKelasSubKelas = (TextView) itemView.findViewById(R.id.text_view_kelas_subkelas_item_jadwal_kereta_api);
            textViewHargaTiket = (TextView) itemView.findViewById(R.id.text_view_harga_tiket_item_jadwal_kereta_api);
            textViewJamBerangkat = (TextView) itemView.findViewById(R.id.text_view_jam_berangkat_item_jadwal_kereta_api);
            textViewTanggalBerangkat = (TextView) itemView.findViewById(R.id.text_view_tanggal_berangkat_item_jadwal_kereta_api);
            textViewKotaAsal = (TextView) itemView.findViewById(R.id.text_view_kota_asal_item_jadwal_kereta_api);
            textViewDurasi = (TextView) itemView.findViewById(R.id.text_view_durasi_item_jadwal_kereta_api);
            textViewJamDatang = (TextView) itemView.findViewById(R.id.text_view_jam_datang_item_jadwal_kereta_api);
            textViewTanggalDatang = (TextView) itemView.findViewById(R.id.text_view_tanggal_datang_item_jadwal_kereta_api);
            textViewKotaTujuan = (TextView) itemView.findViewById(R.id.text_view_kota_tujuan_item_jadwal_kereta_api);
        }
    }

}
