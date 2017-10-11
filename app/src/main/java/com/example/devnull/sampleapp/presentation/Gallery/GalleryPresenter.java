package com.example.devnull.sampleapp.presentation.Gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class GalleryPresenter extends MvpBasePresenter<GalleryView> {

    private static final String LOG_TAG = GalleryPresenter.class.getSimpleName();

    static final int REQUEST_TAKE_PHOTO = 1;
    private File mAllocationPendingFile;

    @Inject
    GalleryRepo mRepo;

    public GalleryPresenter() {
        DaggerGalleryRepoComponent.builder().build().inject(this);
    }

    public void performTakePhotoButtonClick(Context context) {

    }

    public void performChooseFromGalleryButtonClick(Context context) {

    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private void dispatchTakePictureIntent(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(LOG_TAG, "Exception in creating image file " + ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mAllocationPendingFile = photoFile;
                Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                getView().startActivityByRequest(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        if (resultCode != RESULT_OK) {
            getView().showFailToast();
        }

        if (requestCode == REQUEST_TAKE_PHOTO) {
            Single.just(insertToRepoAndRequestAll())
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(dataList -> postDataToView(dataList, context));
        }
    }

    public void loadData(final Context context) {
        Single.just(mRepo.getAll())
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> postDataToView(data, context));
    }

    private void postDataToView(List<GalleryFile> dataList, Context context) {
        for (GalleryFile file: dataList) {
            file.setThumbnail(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getFile().getAbsolutePath()),
                    (int)context.getResources().getDimension(R.dimen.thumbnail_width),
                    (int)context.getResources().getDimension(R.dimen.thumbnail_height)));
        }
        getView().setData(dataList);
    }

    private List<GalleryFile> insertToRepoAndRequestAll() {
        GalleryFile tmp = new GalleryFile(mRepo.getMaxId() + 1, mAllocationPendingFile);
        mRepo.insert(tmp);
        return mRepo.getAll();
    }


}
