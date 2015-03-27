Android-Facebook-Hash
============================
When you are testing you App with Facebook SDK, you need inform you Device Hash to facebook. This plugin help you to get this hash.

#### Required
Plugin for Cordova >= 3.0

#### Installation

For Cordova:

    cordova plugin add https://github.com/iFernandoSousa/android-facebook-hash.git

#### Using the plugin
To get your Facebook hasth, you just need install and call this Javascript method:

    FacebookHash.getHash(function(d) { 
          console.log(d);
        }, 
        function(error) { 
          console.log(error); 
    });
