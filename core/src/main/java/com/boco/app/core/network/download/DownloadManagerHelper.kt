package com.boco.app.core.network.download

import android.app.DownloadManager
import android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.Toast
import java.io.File


/**
 * Created by void on 2017/11/27.
 */
class DownloadManagerHelper(val context: Context) {
    //下载器
    private var downloadManager: DownloadManager? = null

    //下载的ID
    private var downloadId: Long = 0
    private var listener: DownloadListener? = null
    fun downloadAPK(url: String, name: String, listener: DownloadListener) {
        this.listener = listener
        downloadAPK(url, name)
    }

    //下载apk
    fun downloadAPK(url: String, name: String) {
        Log.i("DownloadManagerHelper", "startUpdate name=$name apkUrl=$url")
        //创建下载任务
        val request = DownloadManager.Request(Uri.parse(url))
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(true)

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setTitle("新版本Apk")
        request.setDescription("Apk Downloading...")
        request.setVisibleInDownloadsUi(true)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,name)
        //设置下载的路径
        request.setDestinationInExternalPublicDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath, name)

        //获取DownloadManager
        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager!!.enqueue(request)

        //注册广播接收者，监听下载状态
        context.registerReceiver(receiver,
                IntentFilter(ACTION_DOWNLOAD_COMPLETE))
    }

    //广播监听下载的各个状态
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            checkStatus()
        }
    }


    //检查下载状态
    private fun checkStatus() {
        val query = DownloadManager.Query()
        //通过下载的id查找
        query.setFilterById(downloadId)
        val c = downloadManager!!.query(query)
        if (c.moveToFirst()) {
            val status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
            when (status) {
            //下载暂停
                DownloadManager.STATUS_PAUSED -> {
                }
            //下载延迟
                DownloadManager.STATUS_PENDING -> {
                }
            //正在下载
                DownloadManager.STATUS_RUNNING -> {
                }
            //下载完成
                DownloadManager.STATUS_SUCCESSFUL -> {
                    val file = getFile()
                    if (file != null) {
                        installAPK(file)
                    }
                }
            //下载失败
                DownloadManager.STATUS_FAILED -> Toast.makeText(context, "下载更新失败", Toast.LENGTH_SHORT).show()
            }
        }
        c.close()
    }

    //下载到本地后执行安装
    private fun installAPK(file: File) {
//        RxPermissions.getInstance(context)
//                .request(android.)//这里填写所需要的权限
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
//                            Log.i("permissions", Manifest.permission.READ_CALENDAR + "：" + 获取成功);
//                        } else {
//                            Log.i("permissions", Manifest.permission.READ_CALENDAR + "：" + 获取失败);
//                        }
//                    }
//                })
//    }
        //获取下载文件的Uri
        val downloadFileUri = downloadManager!!.getUriForDownloadedFile(downloadId).path

        val intent = Intent(Intent.ACTION_VIEW)
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            val apkUri = FileProvider.getUriForFile(context, context.applicationInfo.packageName + ".provider", file)
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }

    private fun getFile(): File? {
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)
        val c = downloadManager!!.query(query)
        if (c.moveToFirst()) {
            //获取文件下载路径
            val filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME))
            //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
            return File(filename)
        }
        return null
    }
}