package com.king.demo;

import java.io.File;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.king.demo.util.FileUtil;

import android.content.Context;

public class MyGlideModule implements GlideModule {

	/**下载保存的路径**/
	public static final String DOWN_LOAD_PATH = "dehong";
	/** 德鸿文件存储根目录 */
	public static final String deHongDiretory = FileUtil.getExternalStorageDirectory()
			+ File.separator + DOWN_LOAD_PATH + File.separator;
	
	int diskSize = 1024 * 1024 * 100;
	int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8; // 取1/8最大内存作为最大缓存
	String diskCachePath = deHongDiretory + "glideCache"; // 自定义缓存 路径
																		// 和
																		// 缓存大小

	@Override
	public void applyOptions(Context context, GlideBuilder builder) {

		ViewTarget.setTagId(R.id.glide_tag_id);
		
		// 自定义磁盘缓存:这种缓存只有自己的app才能访问到
		builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));
		builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCachePath, diskSize));

		// 默认内存和图片池大小
		MemorySizeCalculator calculator = new MemorySizeCalculator(context);
		int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
		int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
		builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
		builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));

		// 自定义内存和图片池大小
		builder.setMemoryCache(new LruResourceCache(memorySize));
		builder.setBitmapPool(new LruBitmapPool(memorySize));

		// 提高图片质量
		builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);

		// // 自定义磁盘缓存：这种缓存存在SD卡上，所有的应用都可以访问到
		// builder.setDiskCache(new DiskLruCacheFactory(diskCachePath,
		// diskCacheSize));
	}

	@Override
	public void registerComponents(Context context, Glide glide) {

	}
}
