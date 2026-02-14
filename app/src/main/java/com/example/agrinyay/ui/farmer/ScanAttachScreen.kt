package com.example.agrinyay.ui.farmer

import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.BatchViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
fun ScanAttachScreen(
    navController: NavController,
    farmerId: String,
    batchId: String,
    viewModel: BatchViewModel
) {

    val context = LocalContext.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var hasNavigated by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->

                val previewView = PreviewView(ctx)
                val cameraProviderFuture =
                    ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener({

                    val cameraProvider =
                        cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val barcodeScanner = BarcodeScanning.getClient()

                    val imageAnalyzer =
                        ImageAnalysis.Builder().build().also {

                            it.setAnalyzer(cameraExecutor) { imageProxy ->

                                if (hasNavigated) {
                                    imageProxy.close()
                                    return@setAnalyzer
                                }

                                val mediaImage = imageProxy.image
                                if (mediaImage != null) {

                                    val image = InputImage.fromMediaImage(
                                        mediaImage,
                                        imageProxy.imageInfo.rotationDegrees
                                    )

                                    barcodeScanner.process(image)
                                        .addOnSuccessListener { barcodes ->

                                            for (barcode in barcodes) {

                                                val crateId =
                                                    barcode.rawValue ?: ""

                                                if (crateId.isNotEmpty() && !hasNavigated) {

                                                    hasNavigated = true

                                                    navController.navigate(
                                                        "scan_result/$farmerId/$batchId/$crateId"
                                                    )

                                                    break
                                                }
                                            }

                                            imageProxy.close()
                                        }
                                        .addOnFailureListener {
                                            imageProxy.close()
                                        }
                                } else {
                                    imageProxy.close()
                                }
                            }
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        context as androidx.lifecycle.LifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )

                }, ContextCompat.getMainExecutor(ctx))

                previewView
            }
        )

        // Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .background(
                        Color.Transparent,
                        shape = RoundedCornerShape(24.dp)
                    )
            )
        }

        Text(
            text = "Align QR inside frame",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
