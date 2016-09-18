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

Caution: CandroidServer has fully tested on Windows OS. On other OSs needs customization. All funtion may will not work

***1)*** Compile and run `cAndroidServer` on your PC

***2)*** Disable your PC firewall to open way to incoming commands from Android phone

***3)*** Find your PC IP. Run on terminal  
`ifconfig` - on Unix based systems
`ipconfig` - on Windows

***4)*** Server port set default to `6000`. If there any confilict with port change it newer one

***5)*** Compile and run `cAndroidClient` on your Android device

***6)*** Press settings button on app and enter severver IP address and port number to appropriate fields

***7)*** Open your WiFi on phone

***8)*** Press "TouchPad and KeyBoard" button to open touchpad activity. If WiFi is closed it will ask to connect to network.

***9)*** Try swipe your finger on phone you have to see effect. Mause will move on PC

***10)*** Then you can type character by custom keyboard on phone

***11)*** Try "Need for Spped" and "Slide show controller" too. But first open that application on PC too

***12)*** Thats all

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
