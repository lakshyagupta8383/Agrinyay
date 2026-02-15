package com.example.agrinyay.ui.farmer

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.BatchViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
fun ScanResultScreen(
    navController: NavController,
    batchId: String,
    crateId: String,
    viewModel: BatchViewModel
) {
    var scannedCrateId by remember { mutableStateOf("") }

    // FIX: Context moved outside remember to avoid crash
    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        if (scannedCrateId.isEmpty()) {
            if (hasCameraPermission) {
                CameraPreviewView { result ->
                    scannedCrateId = result
                }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(modifier = Modifier.size(250.dp).border(2.dp, Color.White, RoundedCornerShape(12.dp)))
                        Spacer(Modifier.height(16.dp))
                        Text("Scan Crate QR", color = Color.White, style = MaterialTheme.typography.titleLarge)
                    }
                }
            } else {
                Text("Camera permission required", Modifier.align(Alignment.Center))
            }
        } else {
            // This was missing!
            CrateFormUI(
                batchId = batchId,
                scannedCrateId = scannedCrateId,
                onCancel = { scannedCrateId = "" },
                onSubmit = { condition ->
                    viewModel.attachCrate(batchId, scannedCrateId, condition)
                    navController.popBackStack()
                }
            )
        }
    }
}

// --- MISSING FUNCTION ADDED BELOW ---
@Composable
fun CrateFormUI(
    batchId: String,
    scannedCrateId: String,
    onCancel: () -> Unit,
    onSubmit: (String) -> Unit
) {
    // Matches Backend Enum ("RAW", "RIPPED", "SEMI-RIPPED")
    var selectedCondition by remember { mutableStateOf("RAW") }
    val conditions = listOf("RAW", "SEMI-RIPPED", "RIPPED")

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crate Detected! 📦", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(16.dp))
        Text("ID: $scannedCrateId", style = MaterialTheme.typography.titleLarge, color = Color.Gray)
        Spacer(Modifier.height(32.dp))
        Text("Select Condition:", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))

        conditions.forEach { condition ->
            Button(
                onClick = { selectedCondition = condition },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCondition == condition) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) { Text(condition) }
        }

        Spacer(Modifier.height(32.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("Re-Scan") }
            Spacer(Modifier.width(16.dp))
            Button(onClick = { onSubmit(selectedCondition) }, modifier = Modifier.weight(1f)) { Text("Add Crate") }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreviewView(onBarcodeFound: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = Executors.newSingleThreadExecutor()

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(executor) { imageProxy ->
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        BarcodeScanning.getClient().process(image)
                            .addOnSuccessListener { barcodes ->
                                for (barcode in barcodes) {
                                    barcode.rawValue?.let { code ->
                                        onBarcodeFound(code)
                                        cameraProvider.unbindAll()
                                    }
                                }
                            }
                            .addOnCompleteListener { imageProxy.close() }
                    } else {
                        imageProxy.close()
                    }
                }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis)
                } catch (e: Exception) { e.printStackTrace() }
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}