/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package br.com.hotforms;

import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONException;

import android.content.pm.*;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FacebookHash extends CordovaPlugin {
    private static final String TAG = "FacebookHash";

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        Log.v(TAG, "FacebookHash: initialization");
        super.initialize(cordova, webView);
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Executing action: " + action);
        final Activity activity = this.cordova.getActivity();
        final Window window = activity.getWindow();
        
        if ("getHash".equals(action)) {
        	try {
        		String packageName = activity.getClass().getPackage().getName();
        		PackageManager packageManager = activity.getPackageManager();
            	PackageInfo info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                	MessageDigest md = MessageDigest.getInstance("SHA");
                	md.update(signature.toByteArray());
                	String hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);

                	String result = String.format("{ FacebookHash : \"%s\", PackageName : \"%s\"}", hash.trim(), packageName);
                	callbackContext.success(result);
            	}
        	} 
        	catch (NameNotFoundException e) {
				callbackContext.error(e.getMessage());
        	} 
        	catch (NoSuchAlgorithmException e) {
        		callbackContext.error(e.getMessage());
        	}
			return true;
		}

        return false;
    }
}
