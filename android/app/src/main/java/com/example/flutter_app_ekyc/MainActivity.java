package com.example.flutter_app_ekyc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.vnptit.idg.sdk.activity.VnptIdentityActivity;
import com.vnptit.idg.sdk.utils.SDKEnum;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

import static com.vnptit.idg.sdk.utils.KeyIntentConstants.ACCESS_TOKEN;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.CALL_ADD_FACE;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.CAMERA_FOR_PORTRAIT;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.CHALLENGE_CODE;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.CHECK_LIVENESS_CARD;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.CHECK_MASKED_FACE;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.DOCUMENT_TYPE;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.LIVENESS_ADVANCED;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.SELECT_DOCUMENT;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.SHOW_DIALOG_SUPPORT;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.SHOW_RESULT;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.SHOW_SWITCH;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.TOKEN_ID;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.TOKEN_KEY;
import static com.vnptit.idg.sdk.utils.KeyIntentConstants.VERSION_SDK;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.COMPARE_RESULT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.FRONT_IMAGE;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.HASH_FRONT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.HASH_PORTRAIT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.HASH_REAR;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.INFO_RESULT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.LIVENESS_CARD_FRONT_RESULT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.LIVENESS_CARD_REAR_RESULT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.LIVENESS_RESULT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.MASKED_FACE_RESULT;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.PORTRAIT_IMAGE;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.REAR_IMAGE;
import static com.vnptit.idg.sdk.utils.KeyResultConstants.REGISTER_RESULT;

public class MainActivity extends FlutterActivity {

    private String CHANNEL = "com.vnpt.ekyc/sdk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(getFlutterEngine());
        new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                        if (call.method.equals("startEKYC")) {
                            openEKYC(call, result);
                        } else {
                            result.notImplemented();
                        }
                    }
                }
        );
    }

    private void openEKYC(MethodCall call, MethodChannel.Result result) {
        Activity activity = this;
        if (activity == null) {
            result.error(
                    "ACTIVITY_NOT_AVAILABLE", "Browser cannot be opened " +
                            "without foreground activity", null
            );
            return;
        }

        Intent intent = new Intent(getActivity(), VnptIdentityActivity.class);
        if (intent != null) {
            intent.putExtra(ACCESS_TOKEN, "");
            intent.putExtra(TOKEN_ID, "");
            intent.putExtra(TOKEN_KEY, "");
            intent.putExtra(DOCUMENT_TYPE, SDKEnum. DocumentTypeEnum.IDENTITY_CARD.getValue());
            intent.putExtra(SELECT_DOCUMENT, true);
            intent.putExtra(VERSION_SDK, SDKEnum.VersionSDKEnum.ADVANCED.getValue());
            intent.putExtra(SHOW_RESULT, true);
            intent.putExtra(SHOW_DIALOG_SUPPORT, true);
            intent.putExtra(CAMERA_FOR_PORTRAIT, SDKEnum.CameraTypeEnum.FRONT.getValue());
            intent.putExtra(SHOW_SWITCH, false);
            intent.putExtra(CALL_ADD_FACE, false);
            intent.putExtra(LIVENESS_ADVANCED, true);
            intent.putExtra(CHECK_MASKED_FACE,true);
            intent.putExtra(CHECK_LIVENESS_CARD,true);
            intent.putExtra(CHALLENGE_CODE,"");
            startActivityForResult(intent, 1);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String strDataInfo = data.getStringExtra(INFO_RESULT);
                String strDataCompare = data.getStringExtra(COMPARE_RESULT);
                String strDataLiveness = data.getStringExtra(LIVENESS_RESULT);
                String strDataRegister = data.getStringExtra(REGISTER_RESULT);
                String imageFront = data.getStringExtra(FRONT_IMAGE);
                String imageRear = data.getStringExtra(REAR_IMAGE);
                String imagePortrait = data.getStringExtra(PORTRAIT_IMAGE);
                String strLivenessCardFront = data.getStringExtra(LIVENESS_CARD_FRONT_RESULT);
                String strLivenessCardRear = data.getStringExtra(LIVENESS_CARD_REAR_RESULT);
                String strLivenessMaskFace = data.getStringExtra(MASKED_FACE_RESULT);
                String hashFront = data.getStringExtra(HASH_FRONT);
                String hashRear = data.getStringExtra(HASH_REAR);
                String hashPortrait = data.getStringExtra(HASH_PORTRAIT);
            }
        }
    }
}
