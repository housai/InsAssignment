package com.burhanrashid52.imageeditor.Picture;
abstract class PickPictureThread extends Thread implements Runnable {
    public abstract void pickPictureThreadRun();
    @Override
    public void run() {
        pickPictureThreadRun();
    }
}