package com.angelhack.fragment;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.angelhack.Constants;
import com.angelhack.R;
import com.angelhack.SelectPhotoInterface;
import com.angelhack.Utils;
import com.angelhack.adapters.SelectPhotoAdapter;
import com.angelhack.core.Attachment;

public class InitiateFragment extends Fragment {

	public Button mDOB_btn;
	static String mDate = "";
	View rootView;
	public EditText mBreed_et;
	// public EditText mCharacter_et;
	public EditText mName_et;
	public long dob = -1;
	Uri mImageCaptureUri;
	ImageView mUser_iv;
	AlertDialog alertDialog;
	LinearLayout mLayout;
	LinearLayout mCancel;
	LinearLayout mSave;
	Attachment mAttachment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater
				.inflate(R.layout.initiatefragment, container, false);
		mDOB_btn = (Button) rootView.findViewById(R.id.add_dog_dob);
		mDOB_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getActivity().getSupportFragmentManager(),
						"datePicker");
			}
		});

		// initViews();
		return rootView;

	}

	public void initViews() {
		mDOB_btn = (Button) rootView.findViewById(R.id.add_dog_dob);
		mBreed_et = (EditText) rootView.findViewById(R.id.add_dog_breed);

		mName_et = (EditText) rootView.findViewById(R.id.add_dog_name_et);
		mUser_iv = (ImageView) rootView.findViewById(R.id.add_dog_img);
		mLayout = (LinearLayout) rootView.findViewById(R.id.edit_feature);
		mCancel = (LinearLayout) rootView
				.findViewById(R.id.cancel_user_profile);
		mSave = (LinearLayout) rootView.findViewById(R.id.save_user_profile);
		mDOB_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getActivity().getSupportFragmentManager(),
						"datePicker");
			}
		});
		mUser_iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showCameraDialogBox();
			}
		});

		mLayout.setVisibility(View.VISIBLE);
		initClickListener();

	}

	public void initClickListener() {

		mCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		mSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			final Calendar c = Calendar.getInstance();
			if (dob > 100) {
				c.setTimeInMillis(dob);
			}
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			mDate = day + "/" + month + "/" + year;
			final Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);
			c.set(Calendar.DAY_OF_MONTH, day);

			dob = c.getTimeInMillis();
			mDOB_btn.setText(Utils.getDateSiteVieving(dob));
		}
	}

	public void showCameraDialogBox() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		SelectPhotoAdapter adapter = new SelectPhotoAdapter(getActivity(),
				mSelectPhotoInterface);
		builder.setTitle("Select any one option")
				.setAdapter(adapter, null)
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
							}
						});
		alertDialog = builder.create();
		alertDialog.show();
		((Button) alertDialog.findViewById(android.R.id.button2))
				.setTextSize(16);
		((Button) alertDialog.findViewById(android.R.id.button2))
				.setTypeface(Typeface.DEFAULT_BOLD);
	}

	SelectPhotoInterface mSelectPhotoInterface = new SelectPhotoInterface() {

		@Override
		public void selectGallery() {

			alertDialog.cancel();

			Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);

			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, 0);

		}

		@Override
		public void selectCamera() {

			alertDialog.cancel();

			Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
			photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			mImageCaptureUri = Uri.fromFile(new File(Environment
					.getExternalStorageDirectory(), "dogalize_"
					+ String.valueOf(System.currentTimeMillis()) + ".jpg"));

			photoPickerIntent.putExtra(
					android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
			startActivityForResult(photoPickerIntent, 3);

		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		getActivity();
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				if (data != null) {
					Uri savedUri = data.getData();

					try {
						String[] projection = { MediaStore.Images.Media.DATA };
						@SuppressWarnings("deprecation")
						Cursor cursor = getActivity().managedQuery(savedUri,
								projection, null, null, null);
						int column_index_data = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String capturedImageFilePath = cursor
								.getString(column_index_data);
						File file = new File(capturedImageFilePath);
						Uri muri = Uri.fromFile(file);
						String extension = MimeTypeMap
								.getFileExtensionFromUrl(muri.toString());
						String mime = MimeTypeMap.getSingleton()
								.getMimeTypeFromExtension(extension);
						long file_size = file.length();
						if (checkTotalAttachmentSize(file_size)) {
							mAttachment = new Attachment();
							mAttachment.setUri(muri);
							String title = file.getName();
							mAttachment.setName(title);
							mAttachment.setMimeType(mime);
							mAttachment.setType(extension);
							mAttachment.setFile(file);

							mAttachment.setSize(file_size);
							if (mime.contains("image")) {
								Bitmap bitmap = new Utils().getThumbnail(
										mAttachment, getActivity());
								if (bitmap != null)
									mUser_iv.setImageBitmap(bitmap);
							}
							mAttachment.setDisplaySize(Utils
									.formatFileSize(file_size));
							mAttachment.setAttachmentSize(file_size);

						} else {
							Utils.showAlertDialogWithTitle(
									getString(R.string.more_then_tenmb),
									getString(R.string.oops),

									getActivity());

						}
					} catch (Exception e) {
						showToastMessage("Invalid file");
					}
				}
			} else if (requestCode == 3) {
				try {

					File file = new File(mImageCaptureUri.getPath());
					Uri muri = Uri.fromFile(file);
					String extension = MimeTypeMap.getFileExtensionFromUrl(muri
							.toString());
					String mime = MimeTypeMap.getSingleton()
							.getMimeTypeFromExtension(extension);
					long file_size = file.length();
					if (checkTotalAttachmentSize(file_size)) {

						if (mime != null && mime.length() > 0) {
							mAttachment = new Attachment();
							mAttachment.setUri(muri);
							String title = file.getName();
							mAttachment.setName(title);
							mAttachment.setMimeType(mime);
							mAttachment.setType(extension);
							mAttachment.setFile(file);

							mAttachment.setSize(file_size);
							Bitmap bitmap = new Utils().getThumbnail(
									mAttachment, getActivity());
							if (bitmap != null)
								mUser_iv.setImageBitmap(bitmap);
							mAttachment.setDisplaySize(Utils
									.formatFileSize(file_size));
							mAttachment.setAttachmentSize(file_size);

						} else {

							Utils.showAlertDialogWithTitle(
									getString(R.string.more_then_tenmb),
									getString(R.string.oops),

									getActivity());
						}
					}

				} catch (Exception e) {
					showToastMessage("Invalid file");

				}

			}
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	public boolean checkTotalAttachmentSize(long size) {

		if (size > Constants.MAX_ATTACHMENT_SIZE)
			return false;
		else
			return true;

	}

	private void showToastMessage(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}

}
