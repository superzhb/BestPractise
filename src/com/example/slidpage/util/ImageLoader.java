package com.example.slidpage.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.LruCache;

@SuppressLint("NewApi")
public class ImageLoader {
	private LruCache<Integer, Bitmap> cache;

	private static ImageLoader imageLoader;

	private ImageLoader() {
		int maxcMemory = (int) Runtime.getRuntime().maxMemory();
		cache = new LruCache<Integer, Bitmap>(maxcMemory / 4) {
			@Override
			protected int sizeOf(Integer key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}

	public static ImageLoader getInstance() {
		
		if (imageLoader == null) {
			imageLoader = new ImageLoader();
		}
		return imageLoader;
	}

	public void addBitmap(Integer key, Bitmap bitmap) {
		if (cache.get(key) == null) {
			cache.put(key, bitmap);
		}
	}

	public Bitmap getBitmap(Resources res, int key, int width) {
		if (cache.get(key) != null) {
			return cache.get(key);
		}
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, key, options);
		options.inSampleSize = 1;

		if (width < options.outWidth) {
			options.inSampleSize = options.outWidth / width;
		}

		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(res, key, options);
	}
}
