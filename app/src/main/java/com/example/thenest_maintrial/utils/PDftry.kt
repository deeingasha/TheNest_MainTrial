package com.example.thenest_maintrial.utils

//import com.example.sahibindencomproductlisting.R
//import com.example.sahibindencomproductlisting.ui.adapter.ProductAdapter
//import com.example.sahibindencomproductlisting.ui.viewModel.ProductViewModel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.util.LruCache
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

suspend fun createPdf(
    context: Context,
    cv: CardView,
    view: RecyclerView,
    viewModel: ViewModel,
    rootView: View,
    pdfFileName: String
) = withContext(Dispatchers.IO) {

    val margin = 56

    val recycler_view_bm = getScreenshotFromRecyclerView(view)
    val appBar_bm = getBitmapFromView(cv)

    try {
        val pdfFile = createPDFFile(pdfFileName)
        val fOut = FileOutputStream(pdfFile)

        val document = PdfDocument()
        val pageInfo = recycler_view_bm?.let {
            PdfDocument.PageInfo.Builder(
                recycler_view_bm.width + 2 * margin,
                recycler_view_bm.height + appBar_bm.height + 2 * margin, 1
            ).create()

        }
//        val pageInfo = PdfDocument.PageInfo.Builder(595,842, 1).create()
        val page = document.startPage(pageInfo)
        val c = page.canvas

        //add the margin to the x and y when drawing the bitmap
        c.drawBitmap(appBar_bm, margin.toFloat(), margin.toFloat(), null)

        if (recycler_view_bm != null) {
            c.drawBitmap(
                recycler_view_bm,
                margin.toFloat(),
                appBar_bm.height + margin.toFloat(),
                null
            )
        }

        document.finishPage(page)
        document.writeTo(fOut)
        document.close()


        showDownloadNotification(context, pdfFile)

        withContext(Dispatchers.Main) {
            val snackbar = Snackbar.make(
                rootView,
                "PDF generated successfully.",
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }

    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun getBitmapFromView(view: CardView): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun getScreenshotFromRecyclerView(view: RecyclerView): Bitmap? {
    val adapter = view.adapter
    var bigBitmap: Bitmap? = null
    if (adapter != null) {
        val size = adapter.itemCount
        var height = 0
        val paint = Paint()
        var iHeight = 0
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8
        val bitmapCache = LruCache<String, Bitmap>(cacheSize)
        for (i in 0 until size) {
            val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
            adapter.onBindViewHolder(holder, i)
            holder.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(view.width, adapter.getItemViewType(i)),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            holder.itemView.layout(
                0,
                0,
                holder.itemView.measuredWidth,
                holder.itemView.measuredHeight
            )
            holder.itemView.isDrawingCacheEnabled = true
            holder.itemView.buildDrawingCache()
            val drawingCache = holder.itemView.drawingCache
            if (drawingCache != null) {

                bitmapCache.put(i.toString(), drawingCache)
            }

            height += holder.itemView.measuredHeight
        }

        bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)
        val bigCanvas = Canvas(bigBitmap)
        bigCanvas.drawColor(Color.WHITE)

        for (i in 0 until size) {
            val bitmap = bitmapCache.get(i.toString())
            if (bitmap != null) {
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }

    }
    return bigBitmap
}

fun showDownloadNotification(context: Context, pdfFile: File) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel("pdfChannel", "PDF Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    val pdfUri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        pdfFile
    )

    val openPdfIntent = Intent(Intent.ACTION_VIEW).apply {
        data = pdfUri
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    val pendingIntent = PendingIntent.getActivity(
        context, 0, openPdfIntent,
        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, "pdfChannel")
        .setContentTitle("PDF Download")
        .setContentText("Download complete")
        .setSmallIcon(R.drawable.ic_pdf) // replace with your own icon
        .setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_pdf
            )
        ) // replace with your own icon
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(1, notification)
}

fun createPDFFile(pdfFileName: String): File {
    var pdfFile = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        "${pdfFileName}.pdf"
    )
    if (pdfFile.exists()) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        pdfFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "${pdfFileName}_${timestamp}.pdf"
        )
    }

    pdfFile.createNewFile()
    return pdfFile
}
//            .setAction("Open",  View . OnClickListener () {
//                @Override
//                public void onClick(View view) {
//                    openPDFRecord(pdfFile)
//                }
//            })

//fun openPDFRecord(file: File) {
//    val intent = Intent(Intent.ACTION_VIEW)
//    intent.setDataAndType(Uri.fromFile(file), "application/pdf")
//    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//    startActivity(intent)
//}


