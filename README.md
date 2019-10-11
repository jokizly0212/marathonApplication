# Marathon Application
###Table of Contents:
..* Application's idea
..* Tech that we used
..* Demonstration


### Let's start

1. ##### Application's idea
..* Running app
..* Marathon events nearby
..* Runners can join the marathon events with the app
..* Runners can view the running path on the map, estimated time, steps, and calories burned
..* The app view runner's stats after the marathon event

2. ##### Tech that we used
..* Retrofit to fetch the data from the API that we created
..* MVVM - Model - View - View Model architecture
..* SharePreferences to store/modify/delete the data locally
..* Coroutines to run the tasks in the background
..* Glide library to load the image (based on the imageURL)
..* RoomDatabase for accessing the data
..* Internal sensors: calculating steps, track your device's physical position, measures the acceleration --> velocity --> calories burned
..* Google Map API for showing the marathon path, estimated time for the whole marathon event

3. ##### Demonstration
App's screenshots

![Demo](https://drive.google.com/file/d/1bqrGlUq2IODIPgx9Q_GhqTGwciB4tsfd/view?usp=sharing)

4. ##### How to use the app
..* You can download the zip file above to download the whole project
..* It works best with the latest version of Android
..* Build the app and it's ready to go

