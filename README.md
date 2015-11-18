## SwipeYours ##

SwipeYours allows users to input their own Visa magstripe data to perform NFC payments on payment terminals supporting the Visa MSD protocol (most NFC terminals in North America).  While fully functional for payments, this app is targeted for developers trying to understand HCE (Host Card Emulation).  The main activity provides a time-stamped log of the raw APDU data sent back-and-forth over the NFC interface to complete transactions.

### Android Device Requirements: ###

* Android version 4.4 (KitKat) or later.  HCE (Host Card Emulation) was not available prior to KitKat.
* NFC (Near Field communications).
  
The Google Nexus 4, 5 and 6 are all phones that meet the above requirements.

### Magstripe Data Requirement: ###

SwipeYours parses the card data needed to complete NFC transactions via Visa MSD from the track 2 portion of the magstripe data on a Visa credit cards.

USB magnetic stripe readers are available cheaply (around $20 delivered in the US) from both Amazon and Ebay.  The inexpense readers act as a USB keyboard input and require no special drivers on most desktop computers.

If saving your Magstripe data to Google Drive is not a security concern, the magstripe data can be quickly copied to your phone via an app like Google Keep.

### Google Play Link ###

https://play.google.com/store/apps/details?id=to.noc.android.swipeyours  

### Article on SwipeYours ###

http://blog.simplytapp.com/2014/01/host-card-emulation-series-swipeyours.html

### Developing: ###

SwipeYours was created in Android Studio and getting it to work in Eclipse would require moving a lot of files around (i.e. I do not recommend using Eclipse).  To load the project:

*  Import Project
*  Select the build.gradle file in this directory
*  Check (or leave checked) "Use default gradle wrapper"
