package com.example.devnull.sampleapp.presentation.Gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.data.GalleryRepo;
import com.example.devnull.sampleapp.di.DaggerGalleryRepoComponent;
import com.example.devnull.sampleapp.domain.GalleryFile;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class GalleryPresenter extends MvpBasePresenter<GalleryView> {

    private static final String LOG_TAG = GalleryPresenter.class.getSimpleName();

    private static final String APP_NAME = "SampleApp";

    static final int REQUEST_TAKE_PHOTO = 1;
    private File mAllocationPendingFile;

    @Inject
    GalleryRepo mRepo;

    public GalleryPresenter() {
        DaggerGalleryRepoComponent.builder().build().inject(this);
    }

    public void performTakePhotoButtonClick(Context context) {
        Log.d(LOG_TAG, "::performTakePhotoButtonClick");
        dispatchTakePictureIntent(context);
    }

    public void performChooseFromGalleryButtonClick(Context context) {

    }



    private File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d(LOG_TAG, "Trying to create tempFile in " + storageDir.getAbsolutePath() +
                ", storageDir exist? " + storageDir.exists());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private void dispatchTakePictureIntent(Context context) {
        Log.d(LOG_TAG, "::dispatchTakePictureIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(context);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(LOG_TAG, "Exception in creating image file " + ex);
                getView().showFailToast();
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mAllocationPendingFile = photoFile;
                Uri photoURI = FileProvider.getUriForFile(context,
                        context.getResources().getString(R.string.file_provider_authority),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                getView().startActivityByRequest(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        Log.d(LOG_TAG, "::onActivityResult");

        if (resultCode != RESULT_OK) {
            getView().showFailToast();
        }

        if (requestCode == REQUEST_TAKE_PHOTO) {
            Single.just(insertToRepo())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

    public void loadData(final Context context) {
        Log.d(LOG_TAG, "::loadData");
        Single.just(getAllFromRepoAndCreateThumbnails(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> postDataToView(data));
    }

    private void postDataToView(List<GalleryFile> dataList) {
        Log.d(LOG_TAG, "::postDataToView list by size " + dataList.size());
        getView().setData(dataList);
    }

    private List<GalleryFile> getAllFromRepoAndCreateThumbnails(Context context) {
        List<GalleryFile> dataList = mRepo.getAll();
        for (GalleryFile file: dataList) {
            int targetW = (int) context.getResources().getDimension(R.dimen.thumbnail_width);
            int targetH = (int) context.getResources().getDimension(R.dimen.thumbnail_height);
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getFile().getAbsolutePath(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            bmOptions.inScaled = true;

            file.setThumbnail(BitmapFactory.decodeFile(file.getFile().getAbsolutePath(), bmOptions));
        }
        return dataList;
    }

    private boolean insertToRepo() {
        GalleryFile tmp = new GalleryFile(mRepo.getMaxId() + 1, mAllocationPendingFile);
        mAllocationPendingFile = null;
        return mRepo.insert(tmp);
    }


}
