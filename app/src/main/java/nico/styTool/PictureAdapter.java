package nico.styTool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.Glide;

public class PictureAdapter extends BaseAdapter
{
    private List<Helps_a> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    public PictureAdapter(Context context, List<Helps_a> list)
    {this.mContext = context;this.mDatas = list;inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
	{
            convertView = inflater.inflate(R.layout.a_de, null);
            holder = new ViewHolder();
            holder.userimg = (CircleImageView) convertView.findViewById(R.id.adeCircleImageView1);//
            holder.username = (TextView) convertView.findViewById(R.id.lxw_id_item_message_username);//
            holder.creattime = (TextView) convertView.findViewById(R.id.lxw_id_item_message_createtime);//
            convertView.setTag(holder);
        }
	else
	{
            holder = (ViewHolder) convertView.getTag();
        }

	//   holder.userimg.setImageResource(R.mipmap.ic_launcher);

//        NotifyMsg msg=mDatas.get(position);

        Helps_a author=mDatas.get(position);

        String userimgpath=null;
        if (author.getContent() != null)
	{
            userimgpath = author.getContent();
        }
        if (userimgpath != null)
	{
            //ImageLoader.getmInstance().loaderImage(Constant.USERIMG + userimgpath, holder.userimg, true);
           // ImageLoader.getmInstance().loaderImage(Constant.USERIMG + myUser.getAuvter().getUrl(), holder.userimg, true);
	    //    Glide.with(mContext).load(Constant.USERIMG+userimgpath).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.userimg);
        }

        holder.username.setText(author.getContent());
        holder.creattime.setText(mDatas.get(position).getCreatedAt());
        return convertView;
    }

    private class ViewHolder
    {
        CircleImageView userimg;
        TextView username;
        TextView creattime;
    }
}
