package com.example.devnull.sampleapp.data;

import com.example.devnull.sampleapp.domain.GalleryFile;

import java.util.List;

/**
 * Interface to repo which saves path to image files selected to be viewed in
 * {@link com.example.devnull.sampleapp.presentation.gallery.GalleryFragment}
 */
public interface GalleryRepo {

    boolean insert(GalleryFile file);
    List<GalleryFile> getAll();
    boolean delete(GalleryFile file);
    int getMaxId();
    boolean deleteById(long id);
}
