# cAndroid - Control via Android

[*"cAndroid" - Control via Android*](https://github.com/hummatli/cAndroid) is tool for controlling PC with android device.
This application has created by [*Sattar Hummatli*](https://www.linkedin.com/in/hummatli) to provide to the contest [*"Android Age"*](http://androidage.hackathonazerbaijan.org) which hold by [*"Hackathon Azerbaijan"*](http://hackathonazerbaijan.org).  

Application took third place on competition.

Application has build on IDE `Android Studio`.

By the help of this app you can use your phone as  
* Mause for PC  
* Keyboard for PC  
* Controller for "Need for Speed" game and others  
* Controller for "Power Point" application and other slide show apps.

#Images
<img src="https://raw.githubusercontent.com/hummatli/MAHAds/master/imgs/exit_dlg.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/MAHAds/master/imgs/programs_dlg.png" width="200px"/>

#Application structure

Application contains of two part: 
* `Server` - Runs on PC. Accepts commands from android device
* `Client` - Runs on Android device. Send commands to PC.

Server suggested work on Windows, Linux, MacOS, Solaries operating systems.  
Tested on Windows OS.
  
#How to use?

<b>`1)`</b> To import library to you project add following lines to project's `build.gradle` file. The last stable version is `1.0.6`

```
	dependencies {
    		compile 'com.mobapphome.library:mah-ads:1.10.1'
	}
```

<b>`2)`</b> Call  `MAHAdsController.init()` in your project's starting point. For example: MainActivity's `onCreate()` method or in splash activity. Check url to point your services root path.
Code: 
```java
	MAHAdsController.init(activity, "http://highsoft.az/mahads/");
```

<b>`3)`</b> Call `MAHAdsController.callExitDialog()` when your app quits. It opens `MAHAdsDlgExit` dilog. For example:
Code:	
```java
	public void onBackPressed() {
		MAHAdsController.callExitDialog(activity);
	}
```
<b>Note:</b> To implement `MAHAdsDlgExit` Dialog's `onYes()`, `onNo()`, `onExitWithoutExitDlg()` your main activity has to implement `MAHAdsExitListener`. Otherwise it will through `ClassCastExeption`. `"Your activity must implement MAHAdsExitListener"` 
```java
	public class MainActivity extends AppCompatActivity implements MAHAdsExitListener{
	   @Override
    	   public void onYes() {}

    	   @Override
           public void onNo() {}

           @Override
           public void onExitWithoutExitDlg() {}
	}
```

<b>`4)`</b> To open `MAHAdsDlgPrograms` call `MAHAdsController.callProgramsDialog()` In library sample it has added to menu. Check it
Code:	
```java
	MAHAdsController.callExitDialog(activity);
```

<b>`5)`</b> To customize `MAHAds` dialog UI and overide colors set these values on your main projects `color.xml` file
```xml
    <color name="mah_ads_window_background_color">#FFFFFFFF</color>
    <color name="mah_ads_title_bar_color">#FF3F51B5</color>
    <color name="mah_ads_colorAccent">#FFFF4081</color>

    <color name="mah_ads_all_and_btn_text_color">#FF3F51B5</color>
    <color name="mah_ads_question_txt_color">#FF3F51B5</color>
    <color name="mah_ads_yes_no_txt_color">#FF3F51B5</color>

    <color name="mah_ads_btn_other_border_color">#FF303F9F</color>
    <color name="mah_ads_btn_background_color_pressed">#333F51B5</color>

    <color name="mah_ads_text_view_new_background_color">#FFFF0000</color>
    <color name="mah_ads_text_view_new_text_color">#FFFFFFFF</color>
    <color name="mah_ads_no_img_color">#333F51B5</color>			
```

<b>`7)`</b>` Localization:`  Module now supports 4 languages ` (English, Azerbaijan, Russia, Turkey)` .  To set localization to app use your own method or if it is static and don't change in program session you can just simply add 		`LocaleUpdater.updateLocale(this, "your_lang");` in the start of your app. For examlpe  `LocaleUpdater.updateLocale(this, "ru");`

<b>`8)`</b> To customize `MAHAds` UI texts and overide them add these lines to main projects `string.xml` and set them values

```xml
    <string name="mah_ads_close">Close</string>
    <string name="mah_ads_dlg_title">Recommended applications</string>
    <string name="mah_ads_text_google_play">Open in GooglePlay</string>
    <string name="mah_ads_info_version">Version</string>
    <string name="mah_ads_internet_update_error">Error, please check internet connection or link</string>
    <string name="mah_ads_open_program">Open</string>
    <string name="mah_ads_install_program">Install</string>
    <string name="mah_ads_refresh_btn">Retry</string>
    <string name="mah_ads_free_aps">Recommended applications</string>
    <string name="mah_ads_new_text">New</string>

    <string name="mah_ads_dlg_exit_question">Do you want exit?</string>
    <string name="mah_ads_dlg_exit_positive_btn_txt">EXIT</string>
    <string name="mah_ads_dlg_exit_negativ_btn_txt">STAY</string>

    <string name="mah_ads_dlg_exit_btn_more_txt_1">Applications</string>
    <string name="mah_ads_dlg_exit_btn_more_txt_2">Detailed</string>
```
<b>Note:</b> You can even customize dialogs in your application. Copy `layout/mah_ads_dialog_programs.xml`,  `layout/mah_ads_dialog_exit.xml`files and put in your layot dir and customize  them as you want. But keep view ids as they are. They will overide older ones from library. 
 
<b>`8)`</b> As modul takes information from web servcie you need add `INTERNET` permission to main project.
```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

#End
Thats all. If you have any probelm with using this app please let me know. Write to settarxan@gmail.com. I will help.

#Contribution
* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create new Pull Request
* Star it

#Developed By
Sattar Hummatli - settarxan@gmail.com


#License
Copyright 2015  - <a href="https://www.linkedin.com/in/hummatli">Sattar Hummatli</a>   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
