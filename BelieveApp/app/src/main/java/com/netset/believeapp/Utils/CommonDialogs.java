package com.netset.believeapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.netset.believeapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.drakeet.materialdialog.MaterialDialog;

import static android.graphics.Color.parseColor;


public class CommonDialogs {
	public static ProgressDialog pDialog;
	public static Context ctx;

	public static void customToast(Context c, String msg) {
		 LayoutInflater inflater = ((Activity)c).getLayoutInflater();
		    View layout = inflater.inflate(R.layout.viewww,
		      (ViewGroup) ((Activity)c).findViewById(R.id.toast_layout_root));

		    TextView text = (TextView) layout.findViewById(R.id.text);
 		    
		    text.setText(msg);

		    Toast toast = new Toast(c);
		   // toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
 		    toast.setDuration(Toast.LENGTH_SHORT);
		    toast.setView(layout);
		    toast.show();
	}
	public static ProgressDialog progressDialogWithMessage(Context c, String msg) {


		pDialog = new ProgressDialog(c);
		pDialog.setMessage(msg);
		pDialog.setIndeterminate(true);
		pDialog.show();
		return pDialog;
	}


	public static Dialog oneButtonDialog(Context c, String title, String msg, String buttonTxt, DialogInterface.OnClickListener clicklistener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(c);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setNeutralButton(buttonTxt, clicklistener);
		return dialog.create();
	}


	public static void ShowErrorDialog(Context context, String msg){

	final MaterialDialog mMaterialDialog = new MaterialDialog(context);
	mMaterialDialog.setTitle("Network Issue");
	mMaterialDialog.setMessage(msg);
	mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mMaterialDialog.dismiss();

		}
	});

	mMaterialDialog.show();

	}
	public static void ShowSuccessDialog(Context context, String msg){

		final MaterialDialog mMaterialDialog = new MaterialDialog(context);
		mMaterialDialog.setTitle("Success");
		mMaterialDialog.setMessage(msg);
		mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMaterialDialog.dismiss();

			}
		});

		mMaterialDialog.show();

	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager =
				(InputMethodManager) activity.getSystemService(
						Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(
				activity.getCurrentFocus().getWindowToken(), 0);
	}
	public static void logout(final Activity mContext, String message){
		
		final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
		mMaterialDialog.setTitle("Alert");
		mMaterialDialog.setMessage(message);
		mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMaterialDialog.dismiss();
				GoogleApiClient mGoogleApiClient = null;

					try {

						LoginManager.getInstance().logOut();

					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						if (mGoogleApiClient != null) if (!mGoogleApiClient.isConnected()) {
							mGoogleApiClient.connect();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				 /*   Global.FirstName ="";
				    Global.LastName ="";
				    Global.FBImage="";
					GeneralValues.set_logintype(mContext,"");
					GeneralValues.set_loginbool(mContext, false);
					GeneralValues.set_user_id(mContext, "");
					GeneralValues.set_Api_Key(mContext, "");
					ShortcutBadger.applyCount(mContext, 0);*/
				/*	mContext.startActivity(new Intent(mContext,Login_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
					mContext.finish();*/




			}
		});

		mMaterialDialog.show();

	}

/*
	public static void Handleclick(){

		long currentTime = SystemClock.elapsedRealtime();
		if (currentTime - Global.lastClickTime > Global.MIN_CLICK_INTERVAL) {
			Global.lastClickTime = currentTime;
			// Do your webservice hit
			 }
		}*/

	public static void openInternetDialog(Context c) {
		ctx = c;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		alertDialogBuilder.setTitle("Internet Alert!");
		alertDialogBuilder.setMessage("You are not connected to Internet..Please Enable Internet!")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								final Intent intent = new Intent(Intent.ACTION_MAIN, null);
								intent.addCategory(Intent.CATEGORY_LAUNCHER);
								final ComponentName cn = new ComponentName(
										"com.android.settings",
										"com.android.settings.wifi.WifiSettings");
								intent.setComponent(cn);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								ctx.startActivity(intent);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public  static void getDisplayImage(Context context, String string, ImageView imgView,String colorcode){

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.displayer(new CircleBitmapDisplayer(parseColor(colorcode), 2))
				.cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.build();

		ImageLoader.getInstance().displayImage(string, imgView, options);


	}

	public  static void getDisplayImage2(Context context, String string, ImageView imgView,String colorcode){

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.displayer(new CircleBitmapDisplayer(parseColor(colorcode), 2))
				.showImageForEmptyUri(R.drawable.music_icon)
				.showImageOnLoading(R.drawable.music_icon)
				.showImageOnFail(R.drawable.music_icon)
				.cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.build();

		ImageLoader.getInstance().displayImage(string, imgView, options);


	}


	public  static void getDisplayImage(Context context, String string, ImageView imgView){

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.displayer(new CircleBitmapDisplayer(parseColor("#ffffff"), 0))
				.cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.build();

		ImageLoader.getInstance().displayImage(string, imgView, options);


	}

	public  static void getSquareImage(Context context, String string, ImageView imgView){

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.app_logo)
				.showImageOnLoading(R.drawable.app_logo)
				.showImageOnFail(R.drawable.app_logo).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer())
				.build();

		ImageLoader.getInstance().displayImage(string, imgView, options);

	}


	public  static void getSquareImage2(Context context, String string, ImageView imgView){

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer())
				.build();

		ImageLoader.getInstance().displayImage(string, imgView, options);

	}

	public  static void getSquareImage3(Context context, String string, ImageView imgView){

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.placeholder)
				.showImageOnLoading(R.drawable.placeholder)
				.showImageOnFail(R.drawable.placeholder).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer())
				.build();

		ImageLoader.getInstance().displayImage(string, imgView, options);

	}

	public static String saveimagetosdcard(Context ctx, Bitmap photo) {

		// Bitmap bitmap = null;
		FileOutputStream output;
		File file;
		file = getOutputMediaFile();
		try {
			if(!file.exists()) {
				file.mkdirs();
			}
			output = new FileOutputStream(file);

			// Compress into png format image from 0% - 100%
			photo.compress(Bitmap.CompressFormat.JPEG, 100, output);
			output.flush();
			output.close();
			/*
			 * Toast.makeText(ctx,
			 * "Image Saved to "+getOutputMediaFile().getAbsolutePath(),
			 * Toast.LENGTH_SHORT) .show()
			 */;

		}

		catch (Exception e) {
			e.printStackTrace();
			//CommonDialogs.customToast(ctx,"Try Again.");
			// TODO Auto-generated catch block
		}
		return file.getAbsolutePath();
	}

	public static Bitmap rotateImage(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}

	public static File getOutputMediaFile() {

		// External sdcard location_black
		File mediaStorageDir = Environment.getExternalStorageDirectory();
		// Create a new folder in SD Card
		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/believe");

		if (!dir.exists()) {
			dir.mkdirs();
//			try {
//				dir.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

		File mediaFile;

		mediaFile = new File(dir.getPath() + File.separator + "IMG_" + timeStamp + ".png");

		return mediaFile;
	}

	public static String getPathFromURI(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
				// ExternalStorageProvider
				if (isExternalStorageDocument(uri)) {
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];

					if ("primary".equalsIgnoreCase(type)) {
						return Environment.getExternalStorageDirectory() + "/" + split[1];
					}
				}
				// DownloadsProvider
				else if (isDownloadsDocument(uri)) {

					final String id = DocumentsContract.getDocumentId(uri);
					final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

					return getDataColumn(context, contentUri, null, null);
				}
				// MediaProvider
				else if (isMediaDocument(uri)) {
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];

					Uri contentUri = null;
					if ("image".equals(type)) {
						contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
					} else if ("video".equals(type)) {
						contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
					} else if ("audio".equals(type)) {
						contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
					}
					final String selection = "_id=?";
					final String[] selectionArgs = new String[] {
							split[1]
					};

					return getDataColumn(context, contentUri, selection, selectionArgs);
				}
			}
			// MediaStore (and general)
			else if ("content".equalsIgnoreCase(uri.getScheme())) {
				return getDataColumn(context, uri, null, null);
			}
			// File
			else if ("file".equalsIgnoreCase(uri.getScheme())) {
				return uri.getPath();
			}
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection,
									   String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
	//	String[] filePathColumn = {MediaStore.Images.Media.DATA};
		final String[] projection = {column};
		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);

			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				//cursor.moveToFirst();
				return cursor.getString(column_index);

			} else {
				return uri.getPath();
			}
			/*
			if (cursor != null && cursor.moveToFirst()) {

				return cursor.getString(column_index);
			}*/
		} finally {
			if (cursor != null)
				cursor.close();
		}
		//return null;
	}



	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}


	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}


		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);

		sb.append(hours);
		sb.append(":");
		sb.append(minutes);
		sb.append(":");
		sb.append(seconds);
		sb.append("");

		return sb.toString();
	}







}

