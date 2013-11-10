package com.angelhack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelhack.R;
import com.angelhack.SelectPhotoInterface;

public class SelectPhotoAdapter extends BaseAdapter {

	private LayoutInflater inflator;
	final int[] images = { R.drawable.image_capture, R.drawable.image_gallery };
	final String[] images_source_txt = { "Camera", "Image Gallery" };
	SelectPhotoInterface mSelectPhotoInterface;

	public SelectPhotoAdapter(Context context,
			SelectPhotoInterface mSelectPhotoInterface) {
		this.inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mSelectPhotoInterface = mSelectPhotoInterface;
	}

	@Override
	public int getCount() {

		return images.length;
	}

	@Override
	public Object getItem(int arg0) {

		return arg0;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		MainListHolder mHolder;

		View v = convertView;
		if (convertView == null) {
			mHolder = new MainListHolder();

			v = inflator.inflate(R.layout.update_photo_custom_dialog_box, null);
			mHolder.image = (ImageView) v
					.findViewById(R.id.update_media_images);
			mHolder.source_name = (TextView) v
					.findViewById(R.id.update_image_source_type);

			v.setTag(mHolder);
		} else {
			mHolder = (MainListHolder) v.getTag();
		}

		mHolder.image.setImageResource(images[position]);
		mHolder.source_name.setText(images_source_txt[position]);

		v.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (position == 1) {
					mSelectPhotoInterface.selectGallery();
				} else {
					mSelectPhotoInterface.selectCamera();

				}

			}
		});
		return v;
	}

	class MainListHolder {
		private ImageView image;
		private TextView source_name;
	}

}
