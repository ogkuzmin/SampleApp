package com.example.devnull.sampleapp.presentation.Gallery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.data.GalleryRepo;
import com.example.devnull.sampleapp.di.DaggerGalleryRepoComponent;
import com.example.devnull.sampleapp.domain.GalleryFile;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class GalleryPresenter extends MvpBasePresenter<GalleryView> {

    private static final String LOG_TAG = GalleryPresenter.class.getSimpleName();

    private static final String APP_NAME = "SampleApp";

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;

    private final int mGridElementSize;
    private final Scheduler.Worker mIoWorker;

    private volatile File mAllocationPendingFile;

    @Inject
    GalleryRepo mRepo;

    public GalleryPresenter(Context context) {
        DaggerGalleryRepoComponent.builder().build().inject(this);
        mGridElementSize = calculateGridElementSize(context);
        mIoWorker = Schedulers.io().createWorker();
    }

    private static int calculateGridElementSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpSpacing = context.getResources().getDimension(R.dimen.grid_between_item_spacing);
        int intSpacing = Math.round(dpSpacing * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        int displayWidth = displayMetrics.widthPixels;
        int sizeOfGridElement = (displayWidth - 3*intSpacing)/2;
        return sizeOfGridElement;
    }

    @UiThread
    public void performTakePhotoButtonClick(final Context context) {
        Log.d(LOG_TAG, "::performTakePhotoButtonClick");
        mIoWorker.schedule(() -> dispatchTakePictureIntent(context));
    }

    public void performChooseFromGalleryButtonClick(Context context) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        getView().startActivityByRequest(photoPickerIntent, REQUEST_PICK_PHOTO);

    }

    @UiThread
    public void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        Log.d(LOG_TAG, "::onActivityResult");

        if (resultCode != RESULT_OK) {
            getView().showFailToast();
        }

        if (requestCode == REQUEST_TAKE_PHOTO) {
            Log.d(LOG_TAG, "::onActivityResult took photo with path " + mAllocationPendingFile.getAbsolutePath());
            mIoWorker.schedule(() -> {
                insertToRepo(context, mAllocationPendingFile.getAbsolutePath());
                mAllocationPendingFile = null;
            });
        } else if (requestCode == REQUEST_PICK_PHOTO && data != null) {
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = context.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            Log.d(LOG_TAG, "::onActivityResult received imagePath " + imagePath);
            mIoWorker.schedule(() -> insertToRepo(context, imagePath));
        }
    }

    @UiThread
    public void loadData(final Context context) {
        Log.d(LOG_TAG, "::loadData");
        mIoWorker.schedule(() -> asyncLoadDataAndPostToView(context));
    }

    @UiThread
    public void deleteGalleryFileById(long id) {
        mIoWorker.schedule(() -> mRepo.deleteById(id));
    }

    @WorkerThread
    private void asyncLoadDataAndPostToView(Context context) {
        Single.just(getAllFromRepoAndCreateThumbnails(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> postDataToView(data));
    }

    @UiThread
    private void postDataToView(List<GalleryFile> dataList) {
        Log.d(LOG_TAG, "::postDataToView list by size " + dataList.size());
        getView().setData(dataList);
    }

    @WorkerThread
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

    @WorkerThread
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

    @WorkerThread
    private List<GalleryFile> getAllFromRepoAndCreateThumbnails(Context context) {
        Log.d(LOG_TAG, "::getAllFromRepoAndCreateThumbnails " + Thread.currentThread());
        List<GalleryFile> dataList = mRepo.getAll();
        Collections.sort(dataList);
        for (GalleryFile file: dataList) {
            file.setThumbnail(createThumbnailFromFile(file.getFile().getAbsolutePath()));
        }
        return dataList;
    }

    private Bitmap createThumbnailFromFile(String filePath) {
        int targetW = mGridElementSize;
        int targetH = mGridElementSize;

        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filePath), targetW, targetH);
    }

    @WorkerThread
    private boolean insertToRepo(Context context, String filePath) {
        GalleryFile tmp = new GalleryFile(mRepo.getMaxId() + 1, filePath);
        addGaleryFileToMediaProviderDb(context, filePath);
        return mRepo.insert(tmp);
    }

    private void addGaleryFileToMediaProviderDb(Context context, String alocationFilePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(alocationFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public void performShowImage(Context context, String path) {
        Uri photoURI = Uri.parse("content://" + path);
        Intent intent = new Intent(Intent.ACTION_VIEW, photoURI);
        intent.setType("image/*");
        context.startActivity(intent);
    }
}
