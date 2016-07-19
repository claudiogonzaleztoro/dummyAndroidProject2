package cl.petsos.petsos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIS1175m on 7/5/16.
 */
public class MyListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    //private List<String> mDataList = new ArrayList<String>();
    private List<PetResponse> mDataList = new ArrayList<PetResponse>();

    public MyListViewAdapter(Context cxt){
        mInflater = LayoutInflater.from(cxt);
    }

    public void setData(List<PetResponse> dataList){
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String data = mDataList.get(position).name;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.listitem,null);
            System.out.println("item created for "+position);
        }

        ((TextView)convertView.findViewById(R.id.textView2)).setText(data);

        return convertView;

    }
}
