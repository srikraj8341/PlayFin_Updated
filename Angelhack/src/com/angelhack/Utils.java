package com.angelhack;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;

import com.angelhack.core.Attachment;

public class Utils {

	public Utils() {

	}

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	public static boolean isMailValid(String mail) {
		String mDomainName = "";

		if (mail.length() > 0) {
			boolean matchFound = false;

			// Input the string for validation
			String email = mail.toString();

			if (mDomainName != null && mDomainName.length() > 0) {
				// Test avec le domaine

				// Set the email pattern string
				Pattern p = Pattern.compile(".+@" + mDomainName);
				// Match the given string with the pattern
				Matcher m = p.matcher(email);
				// check whether match is found
				matchFound = m.matches();

				if (matchFound)
					return true;
				else
					return false;
			} else {
				// test sans le domaine

				// Set the email pattern string
				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				// Match the given string with the pattern
				Matcher m = p.matcher(email);
				// check whether match is found
				matchFound = m.matches();
			}

			if (matchFound)
				return true;
			else
				return false;
		} else {
			return false;
		}

	}

	/*
	 * Getting user location
	 */
	public static Location getUserLastKnownLocation(Context context) {

		Location mLocation = null;
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			if (locationManager != null) {
				Location gpsLocation = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				Location networkLocation = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (gpsLocation != null && networkLocation != null) {
					mLocation = getBetterLocation(gpsLocation, networkLocation);
				} else if (gpsLocation != null) {
					mLocation = gpsLocation;
				} else if (networkLocation != null) {
					mLocation = networkLocation;
				}
			}

		} catch (Exception e) {
			mLocation = null;
		}

		return mLocation;
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix. Code taken from
	 * http://developer.android.com/guide/topics/location
	 * /obtaining-user-location.html
	 * 
	 * @param newLocation
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 * @return The better Location object based on recency and accuracy.
	 */
	protected static Location getBetterLocation(Location newLocation,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return newLocation;
		}
		return currentBestLocation;
	}

	/** Checks whether two providers are the same */
	private static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	/*
	 * used to formating the file size
	 * 
	 * @bytes is a variable of file size in bytes
	 */
	public static String formatFileSize(long bytes) {
		float f = 0;
		if (bytes < 1024)
			return bytes + " bytes";
		f = bytes;

		f /= 1024;
		if (f < 1024)
			return Round(f, 1) + " KB";

		f /= 1024;
		if (f < 1024)
			return Round(f, 1) + " MB";

		f /= 1024;
		if (f < 1024)
			return Round(f, 1) + " GB";

		return Round(f, 1) + "";
	}

	public static float Round(float number, int numofdecimals) {
		float p = (float) Math.pow(10, numofdecimals);
		number = number * p;
		float tmp = Math.round(number);
		return (float) tmp / p;
	}

	public static void showAlertDialogWithTitle(String msg, String title,
			Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title)
				.setMessage(msg)
				.setNegativeButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
							}
						});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	public static void showAlertDialogWithTitleFinish(String msg, String title,
			final Context context, final boolean check) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title)
				.setMessage(msg)
				.setNegativeButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
								if (check) {
									ActionBarActivity activity = (ActionBarActivity) context;
									activity.finish();
								}
							}
						});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	public static boolean haveNetworkConnection(Context context) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase(Constants.INTERNET_MODE_WIFI))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase(
					Constants.INTERNET_MODE_MOBILE))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	public static int getGMtOffsetValue() {
		Calendar mCalendar = new GregorianCalendar();
		TimeZone mTimeZone = mCalendar.getTimeZone();
		int mGMTOffset = mTimeZone.getRawOffset();
		return mGMTOffset;
	}

	public static int convertStringToInt(String string) {
		int value = -1;
		try {
			value = Integer.valueOf(string);
		} catch (Exception e) {

		}
		return value;

	}

	public static long convertStringToLong(String string) {
		long value = -1;
		try {
			value = Long.valueOf(string);
		} catch (Exception e) {

		}
		return value;

	}

	public static double convertStringToDouble(String string) {
		double value = -1;
		try {
			value = Double.parseDouble(string);
		} catch (Exception e) {

		}
		return value;

	}

	public Bitmap getThumbnail(Attachment attachment, Context context)
			throws FileNotFoundException, IOException {
		Bitmap bitmap = null;
		try {
			int THUMBNAIL_SIZE = 60;
			InputStream input = context.getContentResolver().openInputStream(
					attachment.getUri());

			BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
			onlyBoundsOptions.inJustDecodeBounds = true;
			onlyBoundsOptions.inDither = true;// optional
			onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
			BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
			input.close();
			if ((onlyBoundsOptions.outWidth == -1)
					|| (onlyBoundsOptions.outHeight == -1))
				return null;

			int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
					: onlyBoundsOptions.outWidth;

			double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE)
					: 1.0;

			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
			bitmapOptions.inDither = true;// optional
			bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
			input = context.getContentResolver().openInputStream(
					attachment.getUri());
			bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
			input.close();
			if (bitmap != null) {

				setThumbnail(attachment, bitmap);
			}

		} catch (Exception e) {

		}
		return bitmap;
	}

	public void setThumbnail(Attachment attachment, Bitmap bitmap) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		attachment.setThumnail(byteArray);

	}

	private int getPowerOfTwoForSampleRatio(double ratio) {
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0)
			return 1;
		else
			return k;
	}

	public static String getDateSiteVieving(long milliSeconds) {
		// Create a DateFormatter object for displaying date in specified
		// format.
		SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy");

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}
}
