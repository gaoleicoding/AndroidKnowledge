package com.example.knowledge.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.lifecycleScope
import com.example.knowledge.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class FileSelectActivity : AppCompatActivity() {
    var PICK_DOCUMENT_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_select)
        pickDocument()
    }

    private fun pickDocument() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }

        startActivityForResult(intent, PICK_DOCUMENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // 处理选定的文档 URI
                lifecycleScope.launch(Dispatchers.IO) {
                    val documentFile = DocumentFile.fromSingleUri(applicationContext, uri)
                    // 判断是否可以读取
                    val read = documentFile?.canRead()
                    if (read == true) {
                        // 再往下走
                    }
                    val name = documentFile?.name
                    // 这个path是app内部存储目录，可以自定义，一定得是内部目录
                    val path = applicationContext.externalCacheDir?.absolutePath
                    val pathFile = File(path)
                    // 创建目录
                    if (!pathFile.exists()) pathFile.mkdirs()
                    val file = File(path, name)
                    // 读取文件，写入新文件
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        FileOutputStream(file).use { outputStream ->
                            val buffer = ByteArray(4 * 1024) // 4 KB buffer size
                            var read: Int
                            while (inputStream.read(buffer).also { read = it } != -1) {
                                outputStream.write(buffer, 0, read)
                            }
                        }
                    }
                    // 最终文件目录
                    val absolutePath = file.absolutePath
                    Log.e("FileSelectActivity", "absolutePath:" + absolutePath)


                }

            }
        }
    }

}